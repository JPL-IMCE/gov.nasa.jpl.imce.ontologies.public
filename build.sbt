import sbt.Keys._
import sbt._

import gov.nasa.jpl.imce.sbt._

import scala.xml.{Node => XNode}
import scala.xml.transform._

useGpg := true

lazy val artifactZipFile = taskKey[File]("Location of the zip artifact file")

lazy val root = Project("gov-nasa-jpl-imce-ontologies", file("."))
  .enablePlugins(IMCEGitPlugin)
  .enablePlugins(IMCEReleasePlugin)
  .settings(IMCEReleasePlugin.packageReleaseProcessSettings: _*)
  .settings(
    IMCEKeys.licenseYearOrRange := "2009-2016",
    IMCEKeys.organizationInfo := IMCEPlugin.Organizations.omf,
    IMCEKeys.targetJDK := IMCEKeys.jdk18.value,

    projectID := {
      val previous = projectID.value
      previous.extra(
        "build.date.utc" -> buildUTCDate.value,
        "artifact.kind" -> "omf.ontologies")
    },

    // disable using the Scala version in output paths and artifacts
    crossPaths := false,

    extractArchives := {},

    artifactZipFile := {
      import com.typesafe.sbt.packager.universal._
      val artifactsDir=baseDirectory.value / "gov.nasa.jpl.imce.ontologies" / "ontologies" / "artifacts"
      val targetDir = baseDirectory.value / "target"
      val ontologiesDir= targetDir / "ontologies"

      val bPath = artifactsDir / "bundles"
      val bFiles = (PathFinder(bPath).*** --- bPath) pair Path.rebase(bPath, ontologiesDir)
      IO.copy(bFiles, overwrite=true, preserveLastModified=true)
      
      val dPath = artifactsDir / "digests"
      val dFiles = (PathFinder(dPath).*** --- dPath) pair Path.rebase(dPath, ontologiesDir)
      IO.copy(dFiles, overwrite=true, preserveLastModified=true)

      val ePath = artifactsDir / "entailments"
      val eFiles = (PathFinder(ePath).*** --- ePath) pair Path.rebase(ePath, ontologiesDir)
      IO.copy(eFiles, overwrite=true, preserveLastModified=true)

      val oPath = artifactsDir / "ontologies"
      val oFiles = (PathFinder(oPath).*** --- oPath) pair Path.rebase(oPath, ontologiesDir)
      IO.copy(oFiles, overwrite=true, preserveLastModified=true)

      val fileMappings = ontologiesDir.*** pair relativeTo(targetDir)
      val zipFile: File = baseDirectory.value / "target" / s"imce-omf_ontologies-${version.value}.zip"

      ZipHelper.zipNative(fileMappings, zipFile)

      zipFile
    },

    addArtifact(Artifact("imce-omf_ontologies", "zip", "zip", Some("resource"), Seq(), None, Map()), artifactZipFile),

    makePom <<= makePom dependsOn artifactZipFile,

    // Disable (I suspect this causes a 409 Conflict with Artifactory)
    /*
    pomPostProcess <<= (pomPostProcess, baseDirectory) {
      (previousPostProcess, base) => { (node: XNode) =>
        val processedNode: XNode = previousPostProcess(node)
        val mdUpdateDir = UpdateProperties(base)
        val resultNode: XNode = new RuleTransformer(mdUpdateDir)(processedNode)
        resultNode
      }
    },
    */

    sourceGenerators in Compile := Seq(),

    managedSources in Compile := Seq(),

    // disable publishing the main jar produced by `package`
    publishArtifact in(Compile, packageBin) := false,

    // disable publishing the main API jar
    publishArtifact in(Compile, packageDoc) := false,

    // disable publishing the main sources jar
    publishArtifact in(Compile, packageSrc) := false,

    // disable publishing the jar produced by `test:package`
    publishArtifact in(Test, packageBin) := false,

    // disable publishing the test API jar
    publishArtifact in(Test, packageDoc) := false,

    // disable publishing the test sources jar
    publishArtifact in(Test, packageSrc) := false
  )


def UpdateProperties(base: File): RewriteRule = {

  val targetDir = base / "target"
  val oDir= targetDir / "ontologies"
  val fileMappings = (oDir.*** pair relativeTo(targetDir)).sortBy(_._2)
  val oFiles = fileMappings flatMap {
    case (file, path) if ! file.isDirectory =>
      Some(MD5File(name=path, md5=MD5.hashFile(file)))
    case _ =>
      None
  }
  val all = MD5SubDirectory(
    name = "ontologies",
    files = oFiles)

  new RewriteRule {

    import spray.json._
    import MD5JsonProtocol._

    override def transform(n: XNode): Seq[XNode]
    = n match {
      case <properties>{props@_*}</properties> =>
        <properties>{props}<md5>{all.toJson}</md5></properties>
      case _ =>
        n
    }
  }
}