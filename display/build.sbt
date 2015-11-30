import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

workbenchSettings

organization := "scalera"

version := "1.0"

scalaVersion := "2.11.2"

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

name := "moonrover-display"

libraryDependencies ++= Seq(
  organization.value %% "moonrover-core" % version.value,
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "com.lihaoyi" %%% "scalatags" % "0.5.3",
  "org.scoverage" %% "scalac-scoverage-runtime" % "1.0.4")

bootSnippet := "scalera.moonrover.display.Boot().main(document.getElementById('canvas'));"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)