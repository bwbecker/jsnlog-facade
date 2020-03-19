# jsnlog-facade
A Scala.js facade for the JSNLog library

## About

JSNLog is a Javascript library for logging messages, either to the browser
console or back to the web server via an Ajax call or both.  See
the [JSNLog homepage](http://js.jsnlog.com/) for details.  This is a
Scala.JS facade for that library.

Besides the facade to JSNLog, this library also includes:
* A facade for [StackTraceJS](https://www.stacktracejs.com/#!/docs/stacktrace-js).  That
    Javascript library gives nice stacktraces in a variety of situations, with
    references to the Scala source files when source maps are available.
* Enrichments to the JSNLog library to easily log exceptions with the Scala
    stacktraces to either the browser console, the server, or both.
* An execution context that will catch unhandled errors running in Futures
    and log them.

## Demo Project
In the demo folder is a Play project that uses jsnlog-facade.  Look for the following details:
* Configuration at the top of `client/src/.../example/ScalaJSExample.scala`.
* Examples of logging information to the browser's console and server in that same file.
* Examples of exceptions being caught and logged using the window's `onerror` function,
    a `try/catch` statement, and by a custom execution context (imported at the top of
    the file).
* The controller that receives the logged messages is in `server/app/controllers/LogCtrl.scala`.
  The logger used here requires configuration from `server/conf/logback.xml`.
* Including the Javascript libraries in `views/main.scala.html`.



## Installing the library

To use jsnlog-facade, add this line to your libraryDependencies:
```
"ca.bwbecker" %%% "jsnlog-facade" % "0.2.1"
```
with a resolver of
```
resolvers ++= Seq(
    "CS-OAT@cs.uwaterloo.ca" at "https://cs.uwaterloo.ca/~cs-oat/maven/"
    )
```

You'll also need to have the actual jsnlog and Stacktrace Javascript libraries loaded
by your web application, of course.

