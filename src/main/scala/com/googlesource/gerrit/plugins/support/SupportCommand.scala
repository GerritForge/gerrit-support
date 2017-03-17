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

import com.google.gson.Gson

case class SupportCommand
(
  gerritVersion: Boolean = false,
  cpuInfo: Boolean = false,
  memInfo: Boolean = false,
  diskSpace: Boolean = false,
  pluginsList: Boolean = false,
  libsList: Boolean = false,
  gerritConfiguration: Boolean = false
)

object SupportCommand {
  def fromJson(s: String) : SupportCommand = {
    new Gson().fromJson(s, classOf[SupportCommand])
  }
}
