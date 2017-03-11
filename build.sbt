name := "gerrit-support"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "3.0" % Provided,
  "com.google.gerrit" % "gerrit-plugin-api" % "2.13.6" % Provided
  )

packageOptions in (Compile, packageBin) +=  {
  Package.ManifestAttributes(
    "Gerrit-ApiType" -> "plugin",
    "Gerrit-PluginName" -> "gerrit-support")
}
