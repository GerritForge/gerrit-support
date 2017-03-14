package com.googlesource.gerrit.plugins.support

import com.google.gerrit.common.Version
import com.google.gson.{JsonElement, JsonPrimitive}
import com.google.inject._

case class CommandResult(entryName: String, content: JsonElement)

trait GerritSupportCommand {
  def execute: CommandResult
}

@Singleton
class GerritSupportCommandFactory @Inject()(val injector: Injector) {
  def apply(name: String): GerritSupportCommand =
    injector.getInstance(
      Class.forName(s"com.googlesource.gerrit.plugins.support.${name.capitalize}Command")
        .asInstanceOf[Class[_ <: GerritSupportCommand]])
}

class GerritVersionCommand extends GerritSupportCommand {
  def execute = CommandResult("version.json", new JsonPrimitive(Version.getVersion))
}