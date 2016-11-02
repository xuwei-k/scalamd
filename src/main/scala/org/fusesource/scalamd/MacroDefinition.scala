package org.fusesource.scalamd

import java.util.regex.{ Matcher, Pattern }

// # Processing Stuff

case class MacroDefinition(
    pattern: String,
    flags: String,
    replacement: (Matcher) => String,
    literally: Boolean
) {

  val regex: Pattern = {
    var f = 0
    if (flags != null) flags.toList.foreach {
      case 'i' => f = f | Pattern.CASE_INSENSITIVE
      case 'd' => f = f | Pattern.UNIX_LINES
      case 'm' => f = f | Pattern.MULTILINE
      case 's' => f = f | Pattern.DOTALL
      case 'u' => f = f | Pattern.UNICODE_CASE
      case 'x' => f = f | Pattern.COMMENTS
      case _ =>
    }
    Pattern.compile(pattern, f)
  }

  override def toString = regex.toString

}
