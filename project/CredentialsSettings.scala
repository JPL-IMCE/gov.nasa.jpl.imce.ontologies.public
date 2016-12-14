/**
  * A utility to retrieve secure credentials.
  *
  * For Travis-CI builds:
  *
  * Use encrypted variables, either via:
  * - https://docs.travis-ci.com/user/environment-variables/#Defining-Variables-in-Repository-Settings
  * - https://docs.travis-ci.com/user/environment-variables/#Defining-encrypted-variables-in-.travis.yml
  * Either technique supports encrypting an environment variable that will be decrypted only from trusted repositories
  * (see: https://docs.travis-ci.com/user/pull-requests#Pull-Requests-and-Security-Restrictions)
  *
  * For local builds:
  * - add a file, local.credentials.sbt, making sure that .gitignore includes a pattern: 'local.*'
  * - In local.credentials.sbt, set variables & values using the following pattern:
  *
  * {{{
  * val _ = {
  *   sys.props += Tuple2("<variable name>", "<value>")
  *   Seq()
  * }
  * }}}
  *
  * To retrieve the value of variables (environment when running on Travis or properties when running locally),
  * use the following pattern:
  *
  * {{{
  * credentials += Credentials(
  *  "Bintray API Realm", "api.bintray.com",
  *  getCredentialProperty("BINTRAY_USER"), getCredentialProperty("BINTRAY_APIKEY"))
  *
  * pgpSigningKey := lookupCredentialUnsignedLongProperty("PGP_SIGNING_KEY")
  *
  * pgpPassphrase := lookupCredentialCharArrayProperty("PGP_PASSPHRASE")
  * }}}
  *
  * @define variable The name of a system property or of an environment variable
  */
object CredentialsSettings {

  /**
    * Get the value of either a system property or, if unset, an environment variable or, if unset, the empty string.
    *
    * @param prop $variable
    * @return
    */
  def getCredentialProperty(prop: String)
  : String
  = sys.props.getOrElse(prop, sys.env.getOrElse(prop, ""))

  val Hex = "0x([0-9a-fA-F]+)L?".r

  /**
    * If set, converts the value of a system property or of an environment variable to an unsigned long.
    * An unsigned long can be in the form:
    * - 0x<hexadecimal>L
    * - <decimal>
    *
    * @param prop $variable
    * @return
    */
  def lookupCredentialUnsignedLongProperty(prop: String)
  : Option[Long]
  = getCredentialProperty(prop) match {
    case "" =>
      None
    case Hex(s) =>
      Some(java.lang.Long.parseUnsignedLong(s, 16))
    case s =>
      Some(java.lang.Long.parseUnsignedLong(s, 10))
  }

  /**
    * If set, converts the value of a system property or of an environment variable to an array of characters
    * @param prop $variable
    * @return
    */
  def lookupCredentialCharArrayProperty(prop: String)
  : Option[Array[Char]]
  = Some(getCredentialProperty(prop).toArray[Char])

}