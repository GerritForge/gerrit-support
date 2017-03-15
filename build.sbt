val pluginName = "gerrit-support"

name := pluginName

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.1"

val guiceV = "3.0"
val gerritV = "2.13.6"
val scalatraV = "2.5.+"

libraryDependencies ++= Seq(
  "com.google.inject" %   "guice" % guiceV    % Provided,
  "com.google.gerrit" %   "gerrit-plugin-api" % gerritV % Provided,
  "org.scalatra"      %%  "scalatra"          % scalatraV
  )

assemblyJarName in assembly := s"$pluginName.jar"

packageOptions in (Compile, packageBin) +=  {
  Package.ManifestAttributes(
    "Gerrit-ApiType" -> "plugin",
    "Gerrit-PluginName" -> pluginName)
}
