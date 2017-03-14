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

import java.io.{ File, FileNotFoundException }

import com.google.gerrit.extensions.annotations._
import com.google.inject.{ Inject, Singleton }
import eu.medsea.mimeutil.detector.ExtensionMimeDetector
import org.scalatra._
import org.scalatra.util.Mimes

import scala.collection.JavaConversions._
import scala.util.{ Failure, Success, Try }

@Singleton
@Export("/collect*")
class GerritSupportServlet @Inject() (processor: RequestProcessor, bundleFactory: SupportBundleFile, mimeDetector: ExtensionMimeDetector)
    extends ScalatraServlet with Mimes {

  post("/") {
    processor.processRequest(request.body) match {
      case Success(zipped) =>
        Created("OK", Map(
          "Location" -> s"${request.getRequestURI}/${zipped.filename}"))
      case Failure(e) =>
        InternalServerError(reason = e.getLocalizedMessage)
    }
  }

  get("/:filename") {
    val bundleFilename = params.getOrElse("filename", halt(BadRequest("Missing or invalid bundle name")))

    bundleFactory(bundleFilename) match {
      case Success(bundleFile: File) =>
        Ok(bundleFile, Map("Content-Type" -> mimeType(bundleFilename)))

      case Failure(e: FileNotFoundException) => NotFound("Bundle not found")

      case Failure(e: IllegalArgumentException) => BadRequest("Invalid bundle name")

    }
  }

  private def mimeType(filename: String) = mimeDetector.getMimeTypes(filename)
    .map(_.toString)
    .headOption.getOrElse("application/octect-stream")
}
