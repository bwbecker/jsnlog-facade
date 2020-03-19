
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

// lazy val server = (project in file("server")).settings(commonSettings).settings(
//   // scalaJSProjects := Seq(client),
//   // pipelineStages in Assets := Seq(scalaJSPipeline),
//   // pipelineStages := Seq(digest, gzip),
//   // // triggers scalaJSPipeline when using compile or continuous compilation
//   // compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
//   libraryDependencies ++= Seq(
//     // "com.vmunier" %% "scalajs-scripts" % "1.1.2",
//     guice,
//     specs2 % Test
//   ),
//   // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
//   // EclipseKeys.preTasks := Seq(compile in Compile)
// ).enablePlugins(PlayScala).
//   dependsOn(sharedJvm)



lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    //pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      "com.vmunier" %% "scalajs-scripts" % "1.1.4",
      guice,
      specs2 % Test
    ),
    // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
    //EclipseKeys.preTasks := Seq(compile in Compile)
  )
  .enablePlugins(PlayScala)
  .dependsOn(sharedJvm)



// lazy val client = (project in file("client")).settings(commonSettings).settings(
//   // scalaJSUseMainModuleInitializer := true,
//   libraryDependencies ++= Seq(
//     "org.scala-js" %%% "scalajs-dom" % "1.0.0",
//     "ca.bwbecker" %%% "jsnlog-facade" % "0.2.1"
//   )
// ).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
//   dependsOn(sharedJs)


lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.0.0",
      "ca.bwbecker" %%% "jsnlog-facade" % "0.2.1"
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(sharedJs)


lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.10",
  organization := "ca.bwbecker",
  resolvers ++= Seq(
    "CS-OAT@cs.uwaterloo.ca" at "https://cs.uwaterloo.ca/~cs-oat/maven/"
    )
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}


