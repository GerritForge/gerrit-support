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
import java.util.zip.{ZipEntry, ZipFile}

import com.google.gson.{Gson, JsonPrimitive}
import org.scalatest.{FlatSpec, Matchers}
import FileMatchers._
import scala.collection.JavaConverters._

class GerritSupportTest extends FlatSpec with Matchers {
  val tmpPath = File.createTempFile(classOf[GerritSupportTest].getName, "").getParentFile

  def tmpFile = {
    val file = File.createTempFile("file.txt", System.currentTimeMillis.toString, tmpPath)
    file.deleteOnExit
    file
  }

  "version command" should "return a non-empty version string" in {
    val version = new GerritVersionCommand().execute.content

    version.getAsString should not be null
  }

  "Bundle builder" should "create an output zip file" in {
    val zipFile = new SupportBundleBuilder(tmpPath.toPath, new Gson).build

    zipFile should (be a 'file
      and endWithExtension("zip"))
  }

  it should "create a one entry in the output zip file" in {
    val file = new SupportBundleBuilder(tmpPath.toPath, new Gson)
      .write(CommandResult("foo", new JsonPrimitive("bar")))
      .build

    val zipEntries = new ZipFile(file).entries.asScala
    zipEntries should have size (1)
  }

  it should "add the Json primitive into the zip entry" in {
    val file = new SupportBundleBuilder(tmpPath.toPath, new Gson)
      .write(CommandResult("entry-name", new JsonPrimitive("foo")))
      .build

    val zipEntries = new ZipFile(file).entries.asScala.toSeq
    zipEntries.map(_.getName) should contain("entry-name")
  }

}

