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
