name := """sbt-dash"""
organization := "works.mesh"
version := "1.0"

scalaVersion := "2.10.6"

licenses += ("Apache License, Version 2.0", url("https://opensource.org/licenses/Apache-2.0"))

bintrayPackageLabels := Seq("sbt","plugin","dash")
bintrayVcsUrl := Some("git@github.com:jastice/sbt-dash.git")

sbtPlugin := true

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)


initialCommands in console := """import works.mesh.sbt.dash._"""

// set up 'scripted; sbt plugin for testing sbt plugins
ScriptedPlugin.scriptedSettings
scriptedLaunchOpts ++=
  Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
