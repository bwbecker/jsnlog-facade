package ca.bwbecker.facades


import ca.bwbecker.facades.stacktrace.StackTrace

import scala.util.{Failure, Success}

/**
  * A Scala.JS facade for the JSNLog Javascript library.  See [[http://js.jsnlog.com/]].
  *
  * @author bwbecker
  */
package object jsnlog {

  /** Lowest integer value for TRACE messages.  TRACE < DEBUG */
  val TRACE = 0
  /** Lowest integer value for DEBUG messages.  TRACE < DEBUG < INFO */
  val DEBUG = 1001
  /** Lowest integer value for INFO messages.   DEBUG < INFO < WARN */
  val INFO = 2001
  /** Lowest integer value for WARN messages.   INFO < WARN < ERROR */
  val WARN = 3001
  /** Lowest integer value for ERROR messages.  WARN < ERROR < FATAL */
  val ERROR = 4001
  /** Lowest integer value for FATAL messages.  ERROR < FATAL */
  val FATAL = 5001


  /**
    * An initial empty map of option values, which you use to begin building up
    * the options object.
    */
  val defaultOpts = ca.bwbecker.facades.builder.noOpts


  /**
    * Use this execution context to log uncaught errors in a Future back to the server.
    * Otherwise, delegate to the usual JSExecutionContext.queue context.  They are logged with the
    * JL("futureLogger") logger.
    */
  implicit val loggingExecutionContext = new scala.concurrent.ExecutionContextExecutor {

    def execChain(t: Throwable): String = {
      if (t == null) ""
      else t.getMessage + execChain(t.getCause)
    }

    override def execute(command: Runnable) = scala.scalajs.concurrent.JSExecutionContext.queue.execute(command)

    def reportFailure(t: Throwable): Unit = {

      JL("futureLogger").error(this.execChain(t.getCause), t)
      //scala.scalajs.concurrent.JSExecutionContext.queue.reportFailure(e)
    }
  }


  // Stackframes with these file names are generally not helpful.  Filter them out.
  private val unhelpfulStackFrames = ".*(" +
    "java/lang/Throwables|" +
    "scalajs/runtime/StackTrace|" +
    "concurrent/impl/Promise|" +
    "scalajs/concurrent/QueueExecutionContext|" +
    "scalajs/runtime/AnonFunctions" +
    ").*"


  /**
    * Enrich Loggers with a method that takes an exception and logs it with a nice
    * scala-oriented stack trace (provided the sourcemap files are available).
    */
  implicit class RichLogger(val logger: Logger) extends AnyVal {

    /**
      * Log a message with a severity of ERROR and an associated Throwable with its stacktrace.
      *
      * @param msg
      * @param t
      */
    def error(msg: String, t: Throwable): Unit = {

      def msgChain(t: Throwable): String = {
        if (t == null) {
          ""
        } else {
          t.getMessage + "\n\t" + msgChain(t.getCause)
        }
      }

      StackTrace.fromError(t).toFuture.onComplete {
        case Success(st) ⇒
          logger.error(msg + ": " + t.getMessage + "\n  at " +
            st.filter(sf ⇒ !sf.fileName.matches(unhelpfulStackFrames)
            ).mkString("\n  at "))

        case Failure(e) ⇒
          logger.error(s"Error obtaining a stack trace: ${msgChain(e)}\n\t${t.getMessage}")
      }
    }
  }


}

