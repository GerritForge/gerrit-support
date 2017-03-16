package com.googlesource.gerrit.plugins.support

import com.google.gson.Gson
import scala.beans.BeanProperty

case class SupportCommand
(
  var gerritVersion: Boolean
)

object SupportCommand {
  def fromJson(s: String) : SupportCommand = {
    new Gson().fromJson(s, classOf[SupportCommand])
  }
}
