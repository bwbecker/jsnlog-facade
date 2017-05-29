package facades.stacktrace

import ca.bwbecker.facades.builder._
import facades.stacktrace.StackTrace.StackFrame

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
  * A facade for the StackTrace.JS library.  See https://www.stacktracejs.com/#!/docs/stacktrace-js
  *
  * Note that the library has two functions, instrument and deinstrument, that have not
  * been implemented here.
  */
@js.native
@JSGlobal
object StackTrace extends js.Object {

  /**
    * Generate a backtrace from invocation point, then parse and enhance it.
    */
  def get(options: impl.StackTraceOptions = StackTraceOptions
         ): js.Promise[js.Array[StackFrame]] = js.native

  /**
    * Generate a backtrace from invocation point, then parse it. This method does not use
    * source maps or guess anonymous functions.
    */
  def getSync(options: impl.StackTraceOptions = StackTraceOptions
             ): js.Array[StackFrame] = js.native

  /**
    * Given an Error object, use error-stack-parser to parse it and enhance
    * location information with stacktrace-gps.
    */
  def fromError(error: Throwable,
                options: impl.StackTraceOptions = StackTraceOptions
               ): js.Promise[js.Array[StackFrame]] = js.native


  /**
    * Use stack-generator to generate a backtrace by walking the arguments.callee.caller chain.
    */
  def generateArtificially(options: impl.StackTraceOptions = StackTraceOptions
                          ): js.Promise[js.Array[StackFrame]] = js.native


  @js.native
  trait StackFrame extends js.Object {
    def fileName: String = js.native

    def lineNumber: Int = js.native

    def columnNumber: Int = js.native
  }


}


object StackTraceOptions extends impl.StackTraceOptionsBuilder(noOpts)

object impl {


  @js.native
  trait StackTraceOptions extends js.Object


  class StackTraceOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[StackTraceOptions, StackTraceOptionsBuilder] {

    def copy(newDict: OptMap): StackTraceOptionsBuilder = {
      new StackTraceOptionsBuilder(newDict)
    }

    /**
      * Only include stack entries matching for which filter returns true.
      *
      * The stackframe is, unfortunately, in terms of the original Javascript rather
      * than after translation to the Scala equivalent.
      */
    def filter(f: js.Function1[StackFrame, Boolean]) = jsOpt("filter", f)

    /**
      * Pre-populate source cache to avoid network requests.
      *
      * Note:  This is totally untested.  It's here just to note that the underlying
      * Javascript library has this potential.
      */
    def sourceCache(mapping: js.Dictionary[String]) = jsOpt("sourceCache", mapping)

    /**
      * Set to true to prevent all network requests.  Defaults to false.
      */
    def offline(value: Boolean) = jsOpt("offline", value)
  }

}
