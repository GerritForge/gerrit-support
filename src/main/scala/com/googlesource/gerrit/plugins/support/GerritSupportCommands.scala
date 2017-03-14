/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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