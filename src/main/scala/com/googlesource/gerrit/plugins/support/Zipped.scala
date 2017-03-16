package com.googlesource.gerrit.plugins.support

import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Path, Paths}
import java.util.zip.{ZipEntry, ZipOutputStream}

class Zipped(dataDirectory: Path) extends Util {

  val fname = s"$getTimeStamp-collect-$getUUID.zip"
  val directory : Path = dataDirectory.resolve("support")
  if(!Files.exists(directory))
    Files.createDirectories(directory)
  val zip = new ZipOutputStream(
  new FileOutputStream(directory.resolve(fname).toFile
  ))
  zip.putNextEntry(new ZipEntry("gerrit-info.txt"))

  def write(s: String) : Unit = zip.write(s.getBytes())
  def close() : Unit = zip.close
}
