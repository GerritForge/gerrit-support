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

import java.io.{BufferedInputStream, FileInputStream}
import java.nio.file.Path
import java.util.zip.{ZipEntry, ZipOutputStream}

import com.google.gerrit.server.config.SitePaths

import scala.util.Try

object Executor extends Util {
  def versionS(sitePaths: SitePaths) =
    s"java -jar ${sitePaths.bin_dir}/gerrit.war version"
  def diskSpaceS(sitePaths: SitePaths) =
    s"df -T ${sitePaths.site_path}"
  def memInfoS = "cat /proc/meminfo"
  def cpuInfoS = "cat /proc/cpuinfo"
  def pluginsListS(sitePaths: SitePaths) =
    s"ls -l ${sitePaths.plugins_dir}"
  def libsListS(sitePaths: SitePaths) =
    s"ls -l ${sitePaths.lib_dir}"

  def output(command: SupportCommand, sitePaths:SitePaths): String = {
    addString(command.gerritVersion,versionS(sitePaths)) +
      addString(command.memInfo, memInfoS) +
      addString(command.cpuInfo, cpuInfoS) +
      addString(command.diskSpace, diskSpaceS(sitePaths)) +
      addString(command.pluginsList, pluginsListS(sitePaths)) +
      addString(command.libsList, libsListS(sitePaths))
  }

  def run(command: SupportCommand, sitePaths: SitePaths): Try[Zipped]
  = {
    Try {
      val zipped = new Zipped(sitePaths.data_dir)
      def addZip(flag: Boolean, name: String, expr: String) = {
        if(flag) {
          zipped.nextEntry(name)
          zipped.write(execute(expr))
          zipped.zip.closeEntry()
        }
      }
      addZip(command.gerritVersion, "version.txt", versionS(sitePaths))
      addZip(command.memInfo, "meminfo.txt", memInfoS)
      addZip(command.cpuInfo, "cpuinfo.txt", cpuInfoS)
      addZip(command.diskSpace, "diskspace.txt", diskSpaceS(sitePaths))
      addZip(command.pluginsList, "pluginslist.txt", pluginsListS(sitePaths))
      addZip(command.libsList, "libslist.txt", libsListS(sitePaths))
      addFolder(command.gerritConfiguration,zipped,"",sitePaths.etc_dir)
      zipped.zip.flush
      zipped.zip.close
      zipped
    }
  }

  private def addString(flag: Boolean, expr: String): String =
    if (flag) execute(expr) else ""

  private def addFolder(flag: Boolean, zipped: Zipped, dirname: String,
                        src: Path) = {
      //zipped.addFolderToZip(dirname,src)
    }
}
