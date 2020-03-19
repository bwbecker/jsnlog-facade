lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "JSNLog Facade"

normalizedName := "jsnlog-facade"

version := "0.2.1"

organization := "ca.bwbecker"

scalaVersion := "2.12.10"

crossScalaVersions := Seq(scalaVersion.value)

resolvers ++= Seq(
    "CS-OAT@cs.uwaterloo.ca" at "https://cs.uwaterloo.ca/~cs-oat/maven/"
    )


scalacOptions ++= Seq("-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps")

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  "ca.bwbecker" %%% "jsFacadeOptionBuilder" % "0.9.4"
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



val keyFile: File = Path.userHome / ".ssh" / "oat_rsa"

val publishMavenStyle = true

publishTo in ThisBuild := Some(Resolver.ssh(
  "OAT Libraries", 
  "linux.cs.uwaterloo.ca",
  "/u1/cs-oat/public_html/maven"
).as("cs-oat", keyFile).withPublishPermissions("0644"))


publishArtifact in Test := false


pomIncludeRepository := { _ => false }
