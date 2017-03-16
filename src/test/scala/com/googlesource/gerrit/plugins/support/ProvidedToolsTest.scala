package com.googlesource.gerrit.plugins.support

import com.google.gson.Gson
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.{FlatSpec, Matchers}

case class Test(name: String, age: Int)

class ProvidedToolsTest extends FlatSpec with Matchers
  with Util with RegularExpressions {

  "gson" should "work for case classes" in {

    val test = Test("test",-1)
    val gson = new Gson()
    val toJson = gson.toJson(test,classOf[Test])
    toJson should be("""{"name":"test","age":-1}""")
    val test1 : Test = gson.fromJson(toJson,classOf[Test])
    test1 should be(test)
  }

  "joda" should "generate proper datetime formatting" in {
    val date : DateTime = new DateTime(2000,2,28,16,30)
    val formatted = DateTimeFormat.forPattern("yyyyMMdd-HHmmss").print(date)
    formatted should be ("20000228-163000")
    getTimeStamp should fullyMatch regex timeStampMatcher

  }
  "uuid" should " be generated" in {

    getUUID should fullyMatch regex uuidMatcher
  }

}
