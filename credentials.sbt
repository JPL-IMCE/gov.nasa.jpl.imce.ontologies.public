import CredentialsSettings._

// publish to bintray
credentials += Credentials(
  "Bintray API Realm", "api.bintray.com",
  getCredentialProperty("BINTRAY_USER"), getCredentialProperty("BINTRAY_APIKEY"))

pgpSigningKey := lookupCredentialUnsignedLongProperty("PGP_SIGNING_KEY")

pgpPassphrase := lookupCredentialCharArrayProperty("PGP_PASSPHRASE")
