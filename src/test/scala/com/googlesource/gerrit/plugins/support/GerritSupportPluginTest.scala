package com.googlesource.gerrit.plugins.support

import org.scalatest.FunSuiteLike
import org.scalatra.test.scalatest.ScalatraSuite

class GerritSupportPluginTest extends ScalatraSuite
  with FunSuiteLike with RegularExpressions {
  // add the servlet so we can start testing
  addServlet(classOf[GerritSupportPlugin], "/*")

  case class Entity(name: String, value: Int)

  test("collect version") {
    // to be studied how to implement this injecting something equivalent as
    // Gerrit
    1 should equal(1)
    //    val jsonCommand = new Gson().toJson(
    //      SupportCommand(gerritVersion = true), classOf[SupportCommand])
    //    post("/collect", jsonCommand) {
    //      status should equal(200)
    //      //body should fullyMatch regex fileNameMatcher
    //    }
  }
}
