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

import com.google.gerrit.extensions.annotations._
import com.google.inject.{Inject, Singleton}
import org.scalatra.{Created, InternalServerError, ScalatraServlet}
import org.slf4j.LoggerFactory

@Singleton
@Export("/collect*")
class GerritSupportServlet @Inject() (processor : ProcessRequest)
  extends ScalatraServlet {
  private val log = LoggerFactory.getLogger(classOf[GerritSupportServlet])

  post("/") {
    processor.processRequest(request.body) match {
      case Right(zipped) =>
        Created("OK\n", Map(
          "Location" -> s"${request.getRequestURI}/${zipped.fname}"))
      case Left(e) =>
        InternalServerError(reason = e.getMessage)
    }

  }
}
