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

import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Path, Paths}
import java.util.zip.{ZipEntry, ZipOutputStream}

class Zipped(dataDirectory: Path) extends Util {

  val fname = s"$getTimeStamp-collect-$getUUID.zip"
  val directory : Path = dataDirectory.resolve("support")
  if(!Files.exists(directory))
    Files.createDirectories(directory)
  val zip = new ZipOutputStream(
  new FileOutputStream(directory.resolve(fname).toFile
  ))
  zip.putNextEntry(new ZipEntry("gerrit-info.txt"))

  def write(s: String) : Unit = zip.write(s.getBytes())
  def close() : Unit = zip.close
}
