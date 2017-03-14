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

import com.google.gerrit.extensions.annotations._
import com.google.gerrit.server.config.SitePaths
import com.google.inject._
import org.scalatra.{ Created, InternalServerError, Ok, ScalatraServlet }
import org.slf4j.LoggerFactory
import com.google.gson.Gson
import com.google.gson.JsonObject

import scala.collection.JavaConverters._

@Singleton
@Export("/collect*")
class GerritSupportServlet @Inject() (
  val sitePaths: SitePaths,
  injector: Injector,
  gson: Gson,
  commandFactory: GerritSupportCommandFactory)
    extends ScalatraServlet {
  private val log = LoggerFactory.getLogger(classOf[GerritSupportServlet])

  post("/") {
    val requestJson = gson.fromJson(request.body, classOf[JsonObject])
    val zipped: Zipped = injector.getInstance(classOf[Zipped])
    try {
      requestJson
        .entrySet().asScala
        .filter(_.getValue.getAsBoolean)
        .map(_.getKey)
        .map(commandFactory.apply)
        .map(_.execute)
        .foreach(zipped.write)

      Created("OK\n", Map(
        "Location" -> s"${request.getRequestURI}/${zipped.fname}"))
    } catch {
      case e: Exception =>
        log.error(s"Can't write collect file '${zipped.fname}' in " +
          s"folder '${zipped.directory}'", e)
        InternalServerError(reason = e.getMessage)
    } finally {
      zipped.close
    }

  }
}
