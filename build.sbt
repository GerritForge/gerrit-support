val pluginName = "gerrit-support"

name := pluginName

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

val guiceV = "3.0"
val gerritV = "2.11"
val scalatraV = "2.5.+"
val scalatestV = "3.0.1"
val gsonV = "2.7" // same version provided by Gerrit
val jodaV = "2.9.4" // same version provided by Gerrit
val scalaGuiceV = "4.1.0"


libraryDependencies ++= Seq(
  // provided by gerrit
  "com.google.inject"     %   "guice"             % guiceV      % Provided,
  "com.google.gerrit"     %   "gerrit-plugin-api" % gerritV     % Provided,
  "com.google.code.gson"  %   "gson"              % gsonV       % Provided,
  "joda-time"             %   "joda-time"         % jodaV       % Provided,

  // added to assembly
  "org.scalatra"          %%  "scalatra"          % scalatraV,


  // test dependencies
  "org.scalatra"          %%  "scalatra-scalatest"% scalatraV   % Test,
  "org.scalatest"         %%  "scalatest"         % scalatestV  % Test,
  "net.codingwell"        %%  "scala-guice"       % scalaGuiceV % Test
  )

assemblyJarName in assembly := s"$pluginName.jar"

packageOptions in (Compile, packageBin) +=  {
  Package.ManifestAttributes(
    "Gerrit-ApiType" -> "plugin",
    "Gerrit-PluginName" -> pluginName)
}
