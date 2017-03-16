package com.googlesource.gerrit.plugins.gerritsupport

import org.scalatest.FunSuiteLike
import org.scalatra.test.scalatest.ScalatraSuite

class GerritSupportPluginTest extends ScalatraSuite with FunSuiteLike {
  // add the servlet so we can start testing
  addServlet(classOf[GerritSupportPlugin], "/*")

  test("simple get") {
    get("/") {
      status should equal (200)
      body should include ("scalatra")
      body should include ("1-2-3")
    }
  }

  test("post json") {

    post("/post","hello post" ) {
      status should equal (200)
      body should include ("hello post")
    }
  }
}
