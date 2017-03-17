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

import java.io.{File, FileInputStream}
import java.nio.file.Paths
import java.util.zip.{ZipEntry, ZipInputStream}

import com.google.gerrit.server.config.SitePaths
import org.scalatest.{AppendedClues, FlatSpec, Matchers, TryValues}

class GerritSupportPluginTest extends FlatSpec
  with Matchers with RegularExpressions with AppendedClues with TryValues {
  val testSiteEnvName = "GERRIT_TEST_SITE"
  val testSite = System.getenv(testSiteEnvName)
  testSite should not be (null) withClue ("You need to export GERRIT_TEST_SITE=")
  val sitePaths = new SitePaths(Paths.get(testSite))

  "Executor Strings" should "collect version" in {
    Executor.output(SupportCommand(gerritVersion = true), sitePaths) should
      include("gerrit version")
  }
  it should "collect cpuinfo" in {
    Executor.output(SupportCommand(cpuInfo = true), sitePaths) should
      include("vendor_id")
  }
  it should "collect meminfo" in {
    Executor.output(SupportCommand(memInfo = true), sitePaths) should
      include("MemTotal")
  }
  it should "collect disk space" in {
    Executor.output(SupportCommand(diskSpace = true), sitePaths) should
      include("Filesystem")
  }
  it should "provide plugins list" in {
    val output = Executor.output(SupportCommand(pluginsList = true), sitePaths)
    output should include("total ")
  }
  it should "provide libs list" in {
    val output = Executor.output(SupportCommand(libsList = true), sitePaths)
    output should include("total ")
    output should include("/lib")
  }
  "zipper" should "collect all info in a zip" in {
    val tryZip = Executor.run(SupportCommand(true, true, true, true, true, true),
      sitePaths)

    val zipped = tryZip.success.value withClue ("A zip should have been " +
      "produced")


    new Unzipped(zipped.innerFile).numFiles should be(6)

  }
  it should "collect configuration as subfolder" in {
    val tryZip = Executor.run(SupportCommand(gerritConfiguration = true),
      sitePaths)
  }

}

class Unzipped(file: File) {
  val zis: ZipInputStream = new ZipInputStream(new FileInputStream(file));
  val numFiles = helper(0)

  def helper(counter: Int): Int =
    if (zis.getNextEntry == null) counter else helper(counter + 1)
}
