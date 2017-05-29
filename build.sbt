lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "JSNLog Facade"

normalizedName := "jsnlog-facade"

version := "0.1.0-SNAPSHOT"

organization := "ca.bwbecker"

scalaVersion := "2.12.0"

crossScalaVersions := Seq("2.11.8", "2.12.0")

scalacOptions ++= Seq("-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps")

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "ca.bwbecker" %%% "jsFacadeOptionBuilder" % "0.9-SNAPSHOT"
)

//jsDependencies += "org.webjars" % "jstree" % "3.2.1" / "jstree.js" minified "jstree.min.js"

//jsDependencies in Test += RuntimeDOM

homepage := Some(url("http://www.querki.net/"))

licenses += ("MIT License", url("http://www.opensource.org/licenses/mit-license.php"))

/*
scmInfo := Some(ScmInfo(
    url("https://github.com/jducoeur/jstree-facade"),
    "scm:git:git@github.com:jducoeur/jstree-facade.git",
    Some("scm:git:git@github.com:jducoeur/jstree-facade.git")))
*/

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <developers>
    <developer>
      <id>bwbecker</id>
      <name>Byron Weber Becker</name>
      <url>https://github.com/bwbecker</url>
    </developer>
  </developers>
  )

pomIncludeRepository := { _ => false }
