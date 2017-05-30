lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "JSNLog Facade"

normalizedName := "jsnlog-facade"

version := "0.1.0"

organization := "ca.bwbecker"

scalaVersion := "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

scalacOptions ++= Seq("-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps")

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "ca.bwbecker" %%% "jsFacadeOptionBuilder" % "0.9.0"
)


homepage := Some(url("https://github.com/bwbecker/jsnlog-facade"))

licenses += ("MIT License", url("http://www.opensource.org/licenses/mit-license.php"))


scmInfo := Some(ScmInfo(
    url("https://github.com/bwbecker/jsnlog-facade"),
    "scm:git:git@github.com:bwbecker/jsnlog-facade.git",
    Some("scm:git:git@github.com:bwbecker/jsnlog-facade.git")))

developers := List(
  Developer(
    id = "bwbecker",
    name = "Byron Weber Becker",
    email = "bwbecker@uwaterloo.ca",
    url = url("https://github.com/bwbecker")
  )
)


publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false


pomIncludeRepository := { _ => false }
