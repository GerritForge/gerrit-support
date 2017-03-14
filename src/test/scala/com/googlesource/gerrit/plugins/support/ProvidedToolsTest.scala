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
