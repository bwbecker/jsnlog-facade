# JSNLog-facade Demo

## Goals

* Be able to log messages to the server, the browser console, or both.
* Capture exceptions and log to the server
    * Those caught with try-catch
    * Exceptions thrown in a future
    * Exceptions that make it to the window's handler
* When debugging, exceptions shown on the console should have
    clickable links to the Scala code via source maps (if available)
* Exceptions logged to the server should have Scala method names,
    source file names and line numbers.

## Approach
* jsonlog and StackTrace are essential tools.  Just need to figure
    out the facades.

## Overview

This demo of JSNLog is based heavily on the
[play-with-scalajs-example](https://github.com/vmunier/play-with-scalajs-example)

Places to look for JSNLog-specific stuff:
* `build.sbt`
* `client/scala/example/ScalaJSExample.scala`: Initialize jsnlog and send some log
    messages to the server and the console.
* `server/app/controllers/LogCtrl.scala`: A controller to receive the log
    messages and write them to Play's log.
* `server/public/javascripts/jsnlog.min.js`: The Javascript library.
* `server/conf/routes`: The route to the LogCtrl controller.
* `server/app/views/main.scala.html`: Adjust the template to include the Javascrip library.



The application contains three directories:
* `server` Play application (server side)
* `client` Scala.js application (client side)
* `shared` Scala code that you want to share between the server and the client

## Run the application
```shell
$ sbt
> run
$ open http://localhost:9000
```
