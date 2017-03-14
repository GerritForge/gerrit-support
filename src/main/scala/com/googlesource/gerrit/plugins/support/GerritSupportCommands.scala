package com.googlesource.gerrit.plugins.support

import com.google.inject._
import com.google.gerrit.common.Version

trait GerritSupportCommand {
  def execute: String
}

@Singleton
class GerritSupportCommandFactory @Inject() (val injector: Injector) {
  def apply(name: String): GerritSupportCommand =
    injector.getInstance(
      Class.forName(s"com.googlesource.gerrit.plugins.support.${name.capitalize}Command")
        .asInstanceOf[Class[_ <: GerritSupportCommand]])
}

class GerritVersionCommand extends GerritSupportCommand {
  def execute = Version.getVersion
}