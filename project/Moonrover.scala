import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scoverage.coveralls.Imports.CoverallsKeys._

object Moonrover extends Build {

  lazy val moonrover = Project(
    id = "moonrover",
    base = file(""),
    aggregate = Seq(core, display),
    settings = common)

  lazy val core = Project(
    id = "moonrover-core",
    base = file("core"),
    settings = common ++ Seq(
      name := "moonrover-core",
      libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    )
  ).enablePlugins(ScalaJSPlugin)

  lazy val display = Project(
    id = "moonrover-display",
    base = file("display"),
    dependencies = Seq(core),
    settings = common ++ Seq(
      name := "moonrover-display"
    ))

  //  Settings

  def common = Seq(
    organization := "scalera",
    version := "1.0",
    scalaVersion := "2.11.2",
    ivyScala := ivyScala.value map {
      _.copy(overrideScalaVersion = true)
    },
    coverallsToken := Some("oAvKbNTN9kxad9bI693UQ7JFATrgi9Urn")
  )
}