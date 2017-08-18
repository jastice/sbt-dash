package works.mesh.sbt.dash

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import scala.sys.process._

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
      val targetValue = target.value
      val dashGeneratorDir = targetValue / "sdocset"
      val dashGeneratorExecutable = dashGeneratorDir / "sdocset"
      val generatorUrl = dashGeneratorUri.value.toURL

      if (dashGeneratorExecutable.exists()) dashGeneratorExecutable
      else {
        val dashGeneratorArchive = dashGeneratorDir / "sdocset.tgz"
        download(generatorUrl, dashGeneratorArchive)
        val extract = s"tar -xf ${dashGeneratorArchive.getAbsolutePath} -C $targetValue"
        require(extract.! == 0)
        require(dashGeneratorExecutable.exists())

        dashGeneratorExecutable
      }
    },

    dashDocset := {
      val docsJar = (packageDoc in Compile).value
      val docsetName = name.value.toLowerCase.replace("-","")
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

  private def download(url: URL, to: File) =
    sbt.io.Using.urlInputStream(url) { inputStream =>
     sbt.io.IO.transfer(inputStream, to)
    }
}