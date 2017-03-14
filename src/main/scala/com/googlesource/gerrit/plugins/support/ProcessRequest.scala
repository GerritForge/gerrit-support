// Copyright (C) 2017 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.support

import com.google.gson.{Gson, JsonObject}
import com.google.inject.{Inject, Injector, Singleton}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

@Singleton
class ProcessRequest @Inject()(
                                injector: Injector,
                                gson: Gson,
                                commandFactory: GerritSupportCommandFactory) {

  private val log = LoggerFactory.getLogger(classOf[GerritSupportServlet])

  def processRequest(body: String): Either[Exception, Zipped] = {
    val requestJson = gson.fromJson(body, classOf[JsonObject])
    val zipped: Zipped = injector.getInstance(classOf[Zipped])

    try {
      requestJson
        .entrySet().asScala
        .filter(_.getValue.getAsBoolean)
        .map(_.getKey)
        .map(commandFactory.apply)
        .map(_.execute)
        .foreach(zipped.write)

      Right(zipped)
    } catch {
      case e: Exception =>
        log.error(s"Can't write collect file '${zipped.fname}' in " +
          s"folder '${zipped.directory}'", e)
        Left(e)
    } finally {
      zipped.close
    }
  }
}
