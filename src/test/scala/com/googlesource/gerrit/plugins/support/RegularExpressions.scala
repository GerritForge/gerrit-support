package com.googlesource.gerrit.plugins.support

trait RegularExpressions {
  val uuidMatcher = """[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}"""
  val timeStampMatcher = """\d{8}-\d{6}"""
  val fileNameMatcher = s"$timeStampMatcher-collect-$uuidMatcher.zip"
}
