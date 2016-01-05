import org.scoverage.coveralls.Imports.CoverallsKeys._

organization := "scalera"

name := "moonrover-core"

version := "1.0"

scalaVersion := "2.11.2"

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

coverallsToken := Some("oAvKbNTN9kxad9bI693UQ7JFATrgi9Urn")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
