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
import org.scalatra.{Created, InternalServerError, Ok, ScalatraServlet}
import org.slf4j.LoggerFactory

@Singleton
@Export("/*")
class GerritSupportPlugin @Inject()(val sitePaths: SitePaths)
  extends ScalatraServlet with Util {
  private val log = LoggerFactory.getLogger(classOf[GerritSupportPlugin])

  post("/collect") {
    val command = SupportCommand.fromJson(request.body)
    val zipped = new Zipped(sitePaths.data_dir)
    try {
      if (command.gerritVersion) {
        val result = execute(s"java -jar ${sitePaths.bin_dir}/gerrit.war  " +
          "version")
        zipped.write(result)
      }
      zipped.close
      val headers = Map(
        "Location" -> zipped.fname)

      // returns empty string with 201
      Created("OK\n",headers)
    } catch {
      case e: Exception =>
        zipped.close
        log.error(s"SupportPlugin can't write collect file '${zipped.fname}' in " +
          s"folder '${zipped.directory}'", e)
        InternalServerError(reason=e.getMessage)
    }

  }
}
