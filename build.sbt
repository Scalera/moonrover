name := "moonrover"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

import org.scoverage.coveralls.Imports.CoverallsKeys._

coverallsToken := Some("oAvKbNTN9kxad9bI693UQ7JFATrgi9Urn")