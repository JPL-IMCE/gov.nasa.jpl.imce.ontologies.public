
import java.io.File
import sbt.Keys._
import sbt._

licenses in GlobalScope += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")

updateOptions := updateOptions.value.withCachedResolution(true)

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

resolvers := {
  val previous = resolvers.value
  if (git.gitUncommittedChanges.value)
    Seq[Resolver](Resolver.mavenLocal) ++ previous
  else
    previous
}

lazy val imce_ontologies_public =
  Project("gov-nasa-jpl-imce-ontologies-public", file("."))
    .enablePlugins(AetherPlugin)
    .enablePlugins(GitVersioning)
    .enablePlugins(UniversalPlugin)
    .settings(
      projectID := {
        val previous = projectID.value
        previous.extra(
          "artifact.kind" -> "ontologies")
      },

      // disable automatic dependency on the Scala library
      autoScalaLibrary := false,

      // disable using the Scala version in output paths and artifacts
      crossPaths := false,

      publishMavenStyle := true,

      // do not include all repositories in the POM
      pomAllRepositories := false,

      // make sure no repositories show up in the POM file
      pomIncludeRepository := { _ => false },

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
      publishArtifact in(Test, packageSrc) := false,

      sourceGenerators in Compile := Seq(),

      managedSources in Compile := Seq(),

      com.typesafe.sbt.packager.Keys.topLevelDirectory in Universal := None,

      // name the '*-resource.zip' in the same way as other artifacts
      com.typesafe.sbt.packager.Keys.packageName in Universal :=
        normalizedName.value + "-" + version.value + "-resource",

      // contents of the '*-resource.zip' to be produced by 'universal:packageBin'
      mappings in Universal in packageBin ++= {

        val ontologyFiles =
          (baseDirectory.value / "ontologies" ***).pair(relativeTo(baseDirectory.value)).sortBy(_._2)

        ontologyFiles

      },

      artifacts += {
        val n = (name in Universal).value
        Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map())
      },
      packagedArtifacts += {
        val p = (packageBin in Universal).value
        val n = (name in Universal).value
        Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) -> p
      }
    )
