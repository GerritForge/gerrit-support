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
