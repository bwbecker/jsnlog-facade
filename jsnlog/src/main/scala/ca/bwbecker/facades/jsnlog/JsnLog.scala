package ca.bwbecker.facades.jsnlog

import scala.scalajs.js
import js.annotation.JSGlobal


/**
  * Facade for the jsnlog logger.  See http://js.jsnlog.com/
  *
  * @author bwbecker
  */
@JSGlobal
@js.native
object JL extends js.Object {

  /** Creates and retrieves javascript loggers. */
  def apply(loggerName: String = ""): Logger = js.native

  /** Creates an Ajax appender. */
  def createAjaxAppender(name: String = ""): AjaxAppender = js.native

  /** Create a console appender. */
  def createConsoleAppender(name: String = ""): ConsoleAppender = js.native

  /** Gets numeric value of the ALL level. */
  def getAllLevel(): Int = js.native

  /** Gets numeric value of the DEBUG level. */
  def getDebugLevel(): Int = js.native

  /** Gets numeric value of the ERROR level. */
  def getErrorLevel(): Int = js.native

  /** Gets numeric value of the FATAL level. */
  def getFatalLevel(): Int = js.native

  /** Gets numeric value of the INFO level. */
  def getInfoLevel(): Int = js.native

  /** Gets numeric value of the OFF level. */
  def getOffLevel(): Int = js.native

  /** Gets numeric value of the TRACE level. */
  def getTraceLevel(): Int = js.native

  /** Gets numeric value of the WARN level. */
  def getWarnLevel(): Int = js.native

  /** Sets library-wide options. */
  def setOptions(options: impl.GeneralOptions): Unit = js.native
}


/**
  * Represents a JavaScript logger.
  * See documentation at [[http://js.jsnlog.com/Documentation/JSNLogJs/Logger]].
  */
@JSGlobal
@js.native
class Logger extends js.Object {
  /** Creates a log item with severity DEBUG */
  def debug(logMsg: String): Logger = js.native

  def debug(logObj: js.Object): Logger = js.native

  def debug(logFn: js.Function0[js.Object]): Logger = js.native

  /** Creates a log item with severity ERROR */
  def error(logMsg: String): Logger = js.native

  def error(logObj: js.Object): Logger = js.native

  def error(logFn: js.Function0[js.Object]): Logger = js.native

  /** Creates a log item with severity FATAL */
  def fatal(logMsg: String): Logger = js.native

  def fatal(logObj: js.Object): Logger = js.native

  def fatal(logFn: js.Function0[js.Object]): Logger = js.native

  /** Creates a log item with severity FATAL containing a message and an exception */
  def fatalException(logMsg: String, e: Throwable): Logger = js.native

  def fatalException(logMsg: String, e: js.JavaScriptException): Logger = js.native

  def fatalException(logObj: js.Object, e: js.JavaScriptException): Logger = js.native

  def fatalException(logFn: js.Function0[js.Object], e: js.JavaScriptException): Logger = js.native

  /** Creates a log item with severity INFO */
  def info(logMsg: String): Logger = js.native

  def info(logObj: js.Object): Logger = js.native

  def info(logFn: js.Function0[js.Object]): Logger = js.native

  /** Creates a log item with custom severity level */
  def log(level: Int, logMsg: String): Logger = js.native

  def log(level: Int, logObj: js.Object): Logger = js.native

  def log(level: Int, logFn: js.Function0[js.Object]): Logger = js.native

  /** Creates a log item with severity TRACE */
  def trace(logMsg: String): Logger = js.native

  def trace(logObj: js.Object): Logger = js.native

  def trace(logFn: js.Function0[js.Object]): Logger = js.native

  /** Creates a log item with severity WARN */
  def warn(logMsg: String): Logger = js.native

  def warn(logObj: js.Object): Logger = js.native

  def warn(logFn: js.Function0[js.Object]): Logger = js.native


  /**
    * Set the options for the logger.  See [[LoggerOptions]].
    */
  def setOptions(options: impl.LoggerOptions): Logger = js.native
}


@js.native
sealed trait Appender extends js.Any


/**
  * Append log messages to the javascript console.
  */
@JSGlobal
@js.native
class ConsoleAppender extends js.Object with Appender {
  def setOptions(options: impl.ConsoleAppenderOptions): ConsoleAppender = js.native
}


/**
  * Append log messages to the server log via an Ajax call.
  */
@JSGlobal
@js.native
class AjaxAppender extends js.Object with Appender {
  def setOptions(options: impl.AjaxAppenderOptions): AjaxAppender = js.native
}


/**
  * Set options on the library as a whole, typically during initialization.
  */
object GeneralOptions extends impl.GeneralOptionsBuilder(defaultOpts)


/**
  * Set options on a Logger.
  */
object LoggerOptions extends impl.LoggerOptionsBuilder(defaultOpts)

/**
  * Set options on an AjaxAppender.
  */
object AjaxAppenderOptions extends impl.AjaxAppenderOptionsBuilder(defaultOpts)

/**
  * Set options on a ConsoleAppender.
  */
object ConsoleAppenderOptions extends impl.ConsoleAppenderOptionsBuilder(defaultOpts)

