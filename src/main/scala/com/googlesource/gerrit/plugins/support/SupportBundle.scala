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

import java.io.{File, FileNotFoundException, FileOutputStream}
import java.nio.charset.Charset
import java.nio.file.Path
import java.util.UUID
import java.util.zip.{ZipEntry, ZipOutputStream}

import com.google.gerrit.extensions.annotations.PluginData
import com.google.gson.Gson
import com.google.inject.Inject
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.util.{Failure, Success, Try}

class SupportBundleFile @Inject()(@PluginData val pluginData: Path) {

  import SupportBundle._

  def apply(bundleFilename: String): Try[File] = {
    val bundleMatched = bundleFilename match {
      case BundleName(filename) => Success(filename)
      case _ => Failure(new IllegalArgumentException(s"Invalid bundle name '$bundleFilename'"))
    }

    bundleMatched.map(pluginData.resolve).map(_.toFile).flatMap {
      case f if f.exists => Success(f)
      case f => Failure(new FileNotFoundException(s"Bundle file $f does not exists"))
    }
  }
}

class SupportBundleBuilder @Inject()(@PluginData val directory: Path, gson: Gson) {

  import SupportBundle._

  lazy val timestamp = DateTimeFormat.forPattern("yyyyMMdd-HHmmss").print(new DateTime)

  lazy val filename = BundleName(timestamp, UUID.randomUUID)

  lazy val file = directory.resolve(filename).toFile

  lazy val zip = new ZipOutputStream(new FileOutputStream(file))

  val UTF8 = Charset.forName("UTF-8")

  def write(file: CommandResult): SupportBundleBuilder = {
    zip.putNextEntry(new ZipEntry(file.entryName))
    zip.write(gson.toJson(file.content).getBytes(UTF8))
    this
  }

  def build() = {
    zip.close
    file
  }
}

object SupportBundle {

  val BundleName = "([0-9\\-]+-collect-[a-z0-9\\-]+.zip)".r

  def BundleName(timestamp: String, uuid: UUID) = s"$timestamp-collect-$uuid.zip"
}
