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

import java.io.File

import com.google.gerrit.common.PluginData
import com.google.inject.{Guice, Provides}
import net.codingwell.scalaguice.ScalaPrivateModule
import org.scalatest.{FunSuite, Matchers}

class GerritSupportTest extends FunSuite with Matchers {

  test("collect version") {

    val result = new GerritVersionCommand().execute
    result.content should be ("(dev)")
  }
  test("processor") {
    val injector = Guice.createInjector(new MyModule())
    val zipped = injector.getInstance(classOf[Zipped])
    zipped.directory

  }
}
class MyModule extends ScalaPrivateModule {
  def configure() {
    expose[MyPluginData].annotatedWith[com.google.gerrit.extensions.annotations.PluginData]
    //bind[PluginData].toInstance(new MyPluginData)

  }
}

@Provides()
class MyPluginData extends PluginData("myplugin","version",new File("")) {

}
