name := """sbt-dash"""
organization := "works.mesh"
version := "1.0"

licenses += ("Apache License, Version 2.0", url("https://opensource.org/licenses/Apache-2.0"))

bintrayPackageLabels := Seq("sbt","plugin","dash")
bintrayVcsUrl := Some("git@github.com:jastice/sbt-dash.git")

sbtPlugin := true

libraryDependencies ++= Seq(
)


initialCommands in console := """import works.mesh.sbt.dash._"""

// set up 'scripted; sbt plugin for testing sbt plugins
scriptedLaunchOpts ++=
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
