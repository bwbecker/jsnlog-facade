package ca.bwbecker.facades.jsnlog

import ca.bwbecker.facades.builder._
import org.scalajs.dom.XMLHttpRequest

import scala.scalajs.js


/**
  * These are types that are needed internally, can't be private, but shouldn't be as
  * visible as JL or AjaxAppender, etc.
  */
package impl {


  @js.native
  trait GeneralOptions extends js.Object


  class GeneralOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[GeneralOptions, GeneralOptionsBuilder] {

    def copy(newDict: OptMap): GeneralOptionsBuilder = {
      new GeneralOptionsBuilder(newDict)
    }

    /**
      * If false, all loggers are disabled.
      **/
    def enabled(v: Boolean) = jsOpt("enabled", v)

    /**
      * Limits total number of messages sent to the server.
      *
      * You use maxMessages to limit the number of messages sent to the server. When you set maxMessages via a call to
      * setOptions, a counter is set to maxMessages. Each time messages are sent to the server, that counter is
      * decremented by the number of messages sent. When the counter gets to zero or below, no more messages will be
      * sent.
      *
      *
      * However, this is affected by batching and buffering.
      *
      * Take a situation where maxMessages is set to 5 and 2 messages have already been sent - so the message counter
      * is now 3. If 8 messages had been stored in a buffer and those messages are now sent, they will be all sent.
      * That means the server will receive a total of 2 + 8 = 10 messages. After this, no more messages will be sent,
      * because the number of messages sent (10) exceeds maxMessages (5).
      *
      * This means that maxMessages is not a precise limit on the number of messages sent to the server. On the other
      * hand, buffered messages are sent together in a single request to the server, minimizing bandwidth. And buffered
      * messages are often useful in solving exceptions, so there is value in receiving them.
      */
    def maxMessages(v: Int) = jsOpt("maxMessages", v)

    /**
      * Default url used by ajax appenders when no url is set.
      *
      * Provides the default url if no url is set on the AjaxAppender setOptions Method. Also sets the url for the
      * default appender.
      *
      * If defaultAjaxUrl is not set, the url /jsnlog.logger is used.
      */
    def defaultAjaxUrl(v: String) = jsOpt("defaultAjaxUrl", v)

    /**
      * The IP address of the browser. Used with the ipRegex option of loggers and appenders.
      */
    def clientIP(v: String) = jsOpt("clientIP", v)

    /**
      * Sent with all log messages to the server, so make it easier to identify all log messages for a given request
      * (details).
      */
    def requestId(v: String) = jsOpt("requestId", v)

    /**
      * Sets a beforeSend method for all Ajax Appenders.
      *
      * The beforeSend field lets you set a function that is called right before an AJAX request with log messages is
      * sent to the server. It receives these parameters:
      *
      * xhr	XMLHttpRequest object used to send the request. Allows you to for example add your own request headers.
      * json	Message to be sent. Allows you to modify this message before it is sent. See below.
      */
    def defaultBeforeSend(v: js.Function2[XMLHttpRequest, String, Unit]) = jsOpt("defaultBeforeSend", v)

    /**
      * Method used to turn objects into strings.
      *
      * When you log an object, it needs to be turned into a string. This field lets you set
      * the method used to do this.
      *
      * If you do not set this field, by default the standard method JSON.stringify is used.
      *
      * Your method has to take the object as a parameter and return the string:
      *
      * serialize(object: any): string
      *
      * A major reason to use your own method is to deal with objects with cyclic references.
      * That is, objects that refer to themselves (details).
      */
    def serialize(v: js.Function1[js.Object, String]) = jsOpt("serialize", v)

  }


  /**
    * Options that are used in all of the setOptions methods.
    */
  @js.native
  trait CommonOptions extends js.Object

  object CommonOptions extends CommonOptionsBuilder(noOpts)

  class CommonOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[CommonOptions, CommonOptionsBuilder]
            with CommonOptionsSetters[CommonOptions, CommonOptionsBuilder] {
    def copy(newDict: OptMap): CommonOptionsBuilder = {
      new CommonOptionsBuilder(newDict)
    }
  }

  trait CommonOptionsSetters[T <: js.Object, B <: JSOptionBuilder[T, _]] extends JSOptionSetter[T, B] {

    /**
      * Numeric severity. Only log messages with a severity equal or higher than this can be sent to the server.
      */
    def level(v: Int) = jsOpt("level", v)

    /**
      * If not empty, log messages only get processed if this regular expression matches the user agent string of the
      * browser.
      */
    def userAgentRegex(v: js.RegExp) = jsOpt("userAgentRegex", v)

    /**
      * If not empty, log messages only get processed if this regular expression matches the IP address(es) of the
      * sender of the request (details below). If you use this, be sure to set the IP address via the setOptions method
      * of the JL object.
      */
    def ipRegex(v: js.RegExp) = jsOpt("ipRegex", v)

    /**
      * If not empty, log messages are suppressed if they match this regular expression. If an object is being logged,
      * it is converted to a JSON string, which is then matched.
      */
    def disallow(v: js.RegExp) = jsOpt("disallow", v)

  }


  /**
    * Options that AjaxAppender and ConsoleAppender have in common.
    */
  @js.native
  trait AppenderOptions extends js.Object

  object AppenderOptions extends AppenderOptionsBuilder(noOpts)

  class AppenderOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[AppenderOptions, AppenderOptionsBuilder]
            with AppenderOptionsSetters[AppenderOptions, AppenderOptionsBuilder] {
    def copy(newDict: OptMap): AppenderOptionsBuilder = {
      new AppenderOptionsBuilder(newDict)
    }

  }

  trait AppenderOptionsSetters[T <: js.Object, B <: JSOptionBuilder[T, _]] extends CommonOptionsSetters[T, B] {


    /**
      * If the severity of the log message is equal or greater than this, but smaller than level, the log message will
      * not be sent to the server, but stored in an internal buffer.
      * If bufferSize is 0 or less, the log message is simply ignored.
      */
    def storeInBufferLevel(v: Int) = jsOpt("storeInBufferLevel", v)

    /**
      * If the severity of a log message is equal or greater than this, not only the log message but also all log
      * messages stored in the internal buffer will be sent to the server.
      * This allows you to store low priority trace messages in the internal buffer, and only send them when a high
      * priority fatal message is sent.
      */
    def sendWithBufferLevel(v: Int) = jsOpt("sendWithBufferLevel", v)


    /**
      * Sets the size of the buffer used with sendWithBufferLevel and storeInBufferLevel.
      */
    def bufferSize(v: Int) = jsOpt("bufferSize", v)


    /**
      * Allows you to improve performance by sending multiple log messages in one go, rather than one by one.
      */
    def batchSize(v: Int) = jsOpt("batchSize", v)
  }


  /**
    * Options for an Ajax Appender.
    */
  @js.native
  trait AjaxAppenderOptions extends js.Object

  class AjaxAppenderOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[AjaxAppenderOptions, AjaxAppenderOptionsBuilder]
            with AjaxAppenderOptionsSetters[AjaxAppenderOptions, AjaxAppenderOptionsBuilder] {
    def copy(newDict: OptMap): AjaxAppenderOptionsBuilder = {
      new AjaxAppenderOptionsBuilder(newDict)
    }
  }

  trait AjaxAppenderOptionsSetters[T <: js.Object, B <: JSOptionBuilder[T, _]] extends AppenderOptionsSetters[T, B] {


    /**
      * All log messages will be sent to this URL.
      */
    def url(v: String) = jsOpt("url", v)

    /**
      * The beforeSend field lets you set a function that is called right before an AJAX request with log messages is
      * sent to the server.  See http://js.jsnlog.com/Documentation/JSNLogJs/AjaxAppender/SetOptions for more details.
      *
      * Function parameters:
      * xhr:	XMLHttpRequest object used to send the request. Allows you to for example add your own request headers.
      * json:	Message to be sent. Allows you to modify this message before it is sent. See below.
      */
    def beforeSend(v: js.Function2[XMLHttpRequest, Any, Unit]) = jsOpt("beforeSend", v)

  }


  /**
    * Options for a Console Appender.
    */
  @js.native
  trait ConsoleAppenderOptions extends js.Object

  class ConsoleAppenderOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[ConsoleAppenderOptions, ConsoleAppenderOptionsBuilder]
            with ConsoleAppenderOptionsSetters[ConsoleAppenderOptions, ConsoleAppenderOptionsBuilder] {
    def copy(newDict: OptMap): ConsoleAppenderOptionsBuilder = {
      new ConsoleAppenderOptionsBuilder(newDict)
    }
  }

  trait ConsoleAppenderOptionsSetters[T <: js.Object, B <: JSOptionBuilder[T, _]] extends AppenderOptionsSetters[T, B] {

  }


  /**
    * Options for Logger.
    */
  @js.native
  trait LoggerOptions extends js.Object

  class LoggerOptionsBuilder(val dict: OptMap)
    extends JSOptionBuilder[LoggerOptions, LoggerOptionsBuilder]
            with LoggerOptionsSetters[LoggerOptions, LoggerOptionsBuilder] {
    def copy(newDict: OptMap): LoggerOptionsBuilder = {
      new LoggerOptionsBuilder(newDict)
    }
  }

  trait LoggerOptionsSetters[T <: js.Object, B <: JSOptionBuilder[T, _]] extends CommonOptionsSetters[T, B] {

    /**
      * One or more appenders for the logger to send its log messages to. See the examples.
      */
    def appenders(v: Seq[Appender]) = jsOpt("appenders", js.Array[Appender](v: _*))

    /**
      * One or more regular expressions. When a message matches a regular expression, then any subsequent messages
      * matching that same regular expression will be suppressed. See the remarks and examples.
      */
    def onceOnly(v: js.Array[String]) = jsOpt("onceOnly", v)

  }

}