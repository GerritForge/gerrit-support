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

import java.io.FileOutputStream
import java.nio.file.Path
import java.util.zip.{ZipEntry, ZipOutputStream}

import com.google.common.base.Charsets
import com.google.gerrit.extensions.annotations.PluginData
import com.google.inject.Inject



class Zipped @Inject() (@PluginData val directory: Path)
  extends Timestamp with RandomUUID {

  val fname = s"$getTimeStamp-collect-$getUUID.zip"

  val zip = new ZipOutputStream(
    new FileOutputStream(directory.resolve(fname).toFile))

  def write(file: CommandResult): Unit = {

    zip.putNextEntry(new ZipEntry(file.title))
    zip.write(file.content.getBytes(Charsets.UTF_8))
  }
  
  def close(): Unit = zip.close
}
