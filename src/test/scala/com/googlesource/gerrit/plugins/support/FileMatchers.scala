package com.googlesource.gerrit.plugins.support

import org.scalatest._
import matchers._

trait FileMatchers {

  class FileEndsWithExtensionMatcher(expectedExtension: String) extends Matcher[java.io.File] {

    def apply(left: java.io.File) = {
      val name = left.getName
      MatchResult(
        name.endsWith(expectedExtension),
        s"""File $name did not end with extension "$expectedExtension"""",
        s"""File $name ended with extension "$expectedExtension""""
      )
    }
  }

  def endWithExtension(expectedExtension: String) = new FileEndsWithExtensionMatcher(expectedExtension)
}

// Make them easy to import with:
// import CustomMatchers._
object FileMatchers extends FileMatchers
