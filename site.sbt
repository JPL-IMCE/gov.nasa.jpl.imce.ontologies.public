// See: https://github.com/sbt/sbt-site

enablePlugins(PreprocessPlugin)

enablePlugins(SiteScaladocPlugin)

import com.typesafe.sbt.SbtGhPages._

preprocessVars in Preprocess := Map(
  "REPO" -> "gov.nasa.jpl.imce",
  "ORG" -> "JPL-IMCE",
  "SUBJECT" -> "jpl-imce",
  "ORG_NAME" -> organizationName.value,
  "DESC" -> description.value,
  "PKG" -> moduleName.value,
  "CONTRIBUTORS" -> {
    val commit = Process("git rev-parse HEAD").lines.head
    val p1 = Process(s"git shortlog -sne --no-merges $commit")
    val p2 = Process(
      Seq("sed",
        "-e",
        """s|^\s*\([0-9][0-9]*\)\s*\(\w.*\w\)\s*<\([a-zA-Z].*\)>.*$|<tr><td align='right'>\1</td><td>\2</td><td>\3</td></tr>|"""))
    val whoswho = p1 #| p2
    whoswho.lines.mkString("\n")
  },
  "VERSION" -> {
    git.gitCurrentTags.value match {
      case Seq(tag) =>
        s"""<a href="https://github.com/JPL-IMCE/${moduleName.value}/tree/$tag">$tag</a>"""
      case _ =>
        val v = version.value
        git.gitHeadCommit.value.fold[String](v) { sha =>
          if (git.gitUncommittedChanges.value)
            v
          else
            s"""<a href="https://github.com/JPL-IMCE/${moduleName.value}/tree/$sha">$v</a>"""
        }
    }
  }
)

target in preprocess := (target in makeSite).value

ghpages.settings

makeSite <<= makeSite.dependsOn(dumpLicenseReport)

siteMappings <<= siteMappings.dependsOn(dumpLicenseReport)

siteMappings += (licenseReportDir.value / "LicenseReportOfAggregatedSBTPluginsAndLibraries.html") -> "LicenseReportOfAggregatedSBTPluginsAndLibraries.html"

previewFixedPort := Some(4004)

previewLaunchBrowser := false