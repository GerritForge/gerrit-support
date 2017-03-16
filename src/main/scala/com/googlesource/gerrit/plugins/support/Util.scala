package com.googlesource.gerrit.plugins.support

import java.util.UUID

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import sys.process._

trait Util {
  def getTimeStamp : String = {
    DateTimeFormat.forPattern("yyyyMMdd-HHmmss").print(new DateTime())
  }
  def getUUID : String = {
    UUID.randomUUID().toString
  }
  def execute(s: String) : String = {
    s !!
  }

}
