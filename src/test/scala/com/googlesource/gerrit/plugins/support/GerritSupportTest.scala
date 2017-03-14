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

import java.io.File
import java.nio.file.Paths
import java.util.zip.ZipEntry

import org.scalatest.{FlatSpec, Matchers}

class GerritSupportTest extends FlatSpec with Matchers {

  "version command" should "be working at core level" in {
    val result = new GerritVersionCommand().execute
    result.content should not be empty
  }

  "zipped class" should "handle simple file" in {
    val fname = "file.txt"
    val zipped = new Zipped(Paths.get("/tmp"))
    zipped.write(CommandResult(fname, "simple content"))
    zipped.close
    val outputfile = s"/tmp/${zipped.fname}"
    val file = new File(outputfile)
    checkZippedFile(file, List(fname))
  }

  def checkZippedFile(file: File, expectedFileNames: List[String]) = {
    file.exists should be(true)
    file.isFile should be(true)
    val readzip = new java.util.zip.ZipFile(file)
    import collection.JavaConverters._
    val entries = readzip.entries.asScala
    entries.zipWithIndex foreach {
      case (entry: ZipEntry, index: Int) =>
        entry.getName should be(expectedFileNames(index))
    }
  }
}

