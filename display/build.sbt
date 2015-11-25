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

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"

libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.5.3"

bootSnippet := "scalera.moonrover.display.Boot().main(document.getElementById('canvas'));"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)