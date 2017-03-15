package com.googlesource.gerrit.plugins.gerritsupport

import com.google.gerrit.extensions.annotations._
import com.google.inject._
import org.scalatra.ScalatraServlet

@Singleton
@Export("/*")
class GerritSupportPlugin extends ScalatraServlet {
  get("/") {
    "[GerritSupportPlugin working with scalatra ] \n"+Scala.list.mkString("-") + "\n" ;
  }

  post("/post") {
    "[GerritSupportPlugin called with post] :'" + request.body + "'\n"
  }
}
// this has been inserted to be sure we can access scala runtime from Gerrit
object Scala {
  val list = List("1","2","3");
}
