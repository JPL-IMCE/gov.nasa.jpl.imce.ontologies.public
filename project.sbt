
sbtPlugin := false

name := "gov.nasa.jpl.imce.ontologies.public"

description := "JPL's Ontological Modeling Framework vocabularies for Integrated Model-Centric Engineering (IMCE)"

moduleName := name.value

organization := "gov.nasa.jpl.imce"

organizationName := "JPL-IMCE"

homepage := Some(url(s"https://github.jpl.nasa.gov/imce/${moduleName.value}"))

organizationHomepage := Some(url("https://github.jpl.nasa.gov/imce"))

git.remoteRepo := "git@github.jpl.nasa.gov/imce/gov.nasa.jpl.imce.ontologies.public.git"

// publish to bintray.com via: `sbt publish`
publishTo := Some(
  "JPL-IMCE" at
    s"https://api.bintray.com/content/jpl-imce/${organization.value}/${moduleName.value}/${version.value}")

scmInfo := Some(ScmInfo(
  browseUrl = url(s"https://github.jpl.nasa.gov/imce/gov.nasa.jpl.imce.ontologies.public"),
  connection = "scm:"+git.remoteRepo.value))

developers := List(
  Developer(
    id="sjenkins",
    name="Steven S. Jenkins",
    email="steven.s.jenkins@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/sjenkins/default.aspx")),
  Developer(
    id="rouquett",
    name="Nicolas F. Rouquette",
    email="nicolas.f.rouquette@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/rouquett/default.aspx")),
  Developer(
    id="castet",
    name="Jean-Francois Castet",
    email="jean-francois.castet@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/castet/default.aspx")),
  Developer(
    id="dwagner",
    name="David A. Wagner",
    email="david.a.wagner@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/dwagner/default.aspx")),
  Developer(
    id="sherzig",
    name="Sebastian J. Herzig",
    email="sebastian.j.herzig@jpl.nasa.gov",
    url=url("https://gateway.jpl.nasa.gov/personal/sherzig/default.aspx")))

