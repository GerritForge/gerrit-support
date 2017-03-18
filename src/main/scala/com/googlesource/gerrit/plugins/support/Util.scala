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

import java.util.UUID

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import sys.process._

trait Util {
  def getTimeStamp : String = {
    DateTimeFormat.forPattern("yyyyMMdd-HHmmss").print(new DateTime())
  }
  def getUUID : String = {
    UUID.randomUUID().toString
  }
  def execute(s: String) : String = {
    s !!
  }

}
