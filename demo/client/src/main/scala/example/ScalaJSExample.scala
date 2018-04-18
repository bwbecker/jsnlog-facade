package example

import ca.bwbecker.facades.jsnlog._
import org.scalajs.dom.Event
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom

import shared.SharedMessages

import scala.scalajs.js
import scala.util.{Failure, Success}


/**
  * Demo the logging library.
  */
object ScalaJSExample {

  def main(args: Array[String]): Unit = {
    this.initJL

    // These get put to the console but not the server due to differing levels
    JL().debug("Debug1: Ready to add message to page")
    JL().debug("Debug2: Ready to add message to page")
    JL().debug("Debug3: Ready to add message to page")
    // Info normally doesn't go to the server
    JL().info("Info msg 1")
    // Three info messages are buffered and sent if an error is sent.
    // This doesn't seem to be working...
    JL().info("Info msg 2")
    JL().info("Info msg 3")
    JL().info("Info msg 4")
    JL().error("Named logger; Error")
    JL().warn("Warning msg 1")
    JL().warn("Warning msg 2")


    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks


    // Catch an error executing in an execution context
    Ajax.get("/doesNotExist").onComplete {
      case Success(request) ⇒
        throw new Exception("SHOULD NOT HAPPEN")

      // Omit this case and a match error is caught; compiler warning
      case Failure(e) ⇒
        // Throw the error and nothing in the logs says where it occurred
        //throw e

        throw new Exception("Failed Ajax call", e)
    }


    // Catch an error executing in an execution context
    Ajax.get("/").onComplete {
      case Success(request) ⇒
        throw new Exception("Oops while processing Success")

      case Failure(e) ⇒
        throw new Exception("SHOULD NOT HAPPEN")
    }

    // Times out
    Ajax.get("/takes2sec", timeout = 200).onComplete {
      case Success(request) ⇒
        throw new Exception("SHOULD NOT HAPPEN")

      case Failure(e) ⇒
        // Don't see a way to distinguish timeouts from other kinds of failures
        throw e
    }


    try {
      new Test().test1()
    } catch {
      case e: Throwable ⇒
        JL("exceptions").error("Using enriched error", e)
    }

    throw new Exception("Uncaught oops...")

  }


  private def initJL: Unit = {

    val errorFunction: js.Function5[String, String, Int, Int, Throwable, Boolean] =
      (message: String, source: String, line: Int, column: Int, throwable: Throwable) => {
        JL("onerrorLogger").error("Uncaught exception: ", throwable)
        true
      }

    // Install our own handler for uncaught exceptions.

    js.Dynamic.global.window.onerror = errorFunction

    //      def onError(errorMsg: Event, url: String, lineNumber: Int, column: Int, errorObj: Throwable): Boolean = {
    //        JL("onerrorLogger").error("Uncaught exception: ", errorObj)
    //        true
    //      }

    // Install our own handler for uncaught exceptions.
    //      dom.window.onerror = (onError _).asInstanceOf[Function4[Event, String, Int, Int, _]]

    //Use a custom end-point for logging to the server; default is /jsnlog.logger
    JL.setOptions(GeneralOptions.defaultAjaxUrl("/logError"))

    //Log both to the server and the console.  Default is just the server.
    JL().setOptions(LoggerOptions.appenders(Seq(
      JL.createAjaxAppender()
        .setOptions(AjaxAppenderOptions
          .bufferSize(3)
          .batchSize(3)  //no way to send immediately even if haven't reached batch size?
          .sendWithBufferLevel(ERROR)
          .storeInBufferLevel(INFO)
          .level(WARN)
        ),
      JL.createConsoleAppender()
        .setOptions(ConsoleAppenderOptions
          .level(DEBUG)
        )
    )))

  }
}
