package works.mesh.sbt.dash

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object DashPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  object DashKeys {
    val dashGeneratorUri = settingKey[URI]("URI for downloading the Dash sdocset generator")
    val dashGeneratorDownload = taskKey[File]("Download sdocset generator")
    val dashDocset = taskKey[File]("Create a Dash docset for the project")
  }

  import DashKeys._

  val autoImport = Seq(dashDocset)

  override lazy val projectSettings = Seq(

    dashGeneratorUri := uri("https://kapeli.com/feeds/zzz/sdocset.tgz"),

    dashGeneratorDownload := {
      val dashGeneratorDir = target.value / "sdocset"
      val dashGeneratorExecutable = dashGeneratorDir / "sdocset"

      if (dashGeneratorExecutable.exists()) dashGeneratorExecutable
      else {
        val dashGeneratorArchive = dashGeneratorDir / "sdocset.tgz"
        IO.download(dashGeneratorUri.value.toURL, dashGeneratorArchive)
        val extract = s"tar -xf ${dashGeneratorArchive.getAbsolutePath} -C ${target.value}"
        require(extract.! == 0)
        require(dashGeneratorExecutable.exists())

        dashGeneratorExecutable
      }
    },

    dashDocset := {
      val docsJar = (packageDoc in Compile).value
      val docsetName = name.value
      val docset = docsJar.getParentFile / (docsetName + ".docset")

      val cross = CrossVersion(scalaVersion.value, scalaBinaryVersion.value)
      val versioned = cross(projectID.value)
      val packname = s"${versioned.organization}:${versioned.name}"

      val sdocset = dashGeneratorDownload.value
      val docCommand = s"${sdocset.getAbsolutePath} $docsetName $packname ${docsJar.getAbsolutePath} ${version.value}"

      require(docCommand.! == 0, "failed to generate docset")
      docset
    }
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()
}