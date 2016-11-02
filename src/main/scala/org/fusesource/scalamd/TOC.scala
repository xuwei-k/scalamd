package org.fusesource.scalamd

class TOC(
    val start_level: Int,
    val end_level: Int,
    val html: String
) {

  case class Heading(
      level: Int,
      id: String,
      body: String
  ) {

    def toHtml: String = {
      if (id == null) "<span>" + body + "</span>"
      else "<a href=\"#" + id + "\">" + body + "</a>"
    }

    override def toString = toHtml
  }

  val headings: Seq[Heading] = {
    TOC.rHeadings.findAllIn(html).matchData.toList
      .flatMap { m =>
        if (m.group(4) != null) {
          val h = new Heading(m.group(1).toInt, m.group(4), m.group(5))
          if (start_level <= h.level && h.level <= end_level) {
            Some(h)
          } else {
            None
          }
        } else {
          None
        }
      }
  }

  val toHtml: String = if (headings.size == 0) "" else {
    val sb = new StringBuilder
    def startList(l: Int) = sb.append("  " * (1 + l - start_level) + """<li><ul style="list-style:none;">""" + "\n")
    def endList(l: Int) = sb.append("  " * (l - start_level) + "</ul></li>\n")
    def add(l: Int, h: Heading) = sb.append("  " * (1 + l - start_level) + "<li>" + h.toString + "</li>\n")
    def formList(level: Int, index: Int): Unit = if (index < 0 || index >= headings.size) {
      if (level > start_level) {
        endList(level)
        formList(level - 1, index)
      }
    } else {
      val h = headings(index)
      if (level < h.level) {
        startList(level)
        formList(level + 1, index)
      } else if (level > h.level) {
        endList(level)
        formList(level - 1, index)
      } else {
        add(level, h)
        formList(level, index + 1)
      }
    }
    formList(start_level, 0)
    """<div class="toc"><ul style="list-style:none;">""" + "\n" + sb.toString + "</ul></div>"
  }

}

object TOC {

  val rHeadings = """<h(\d)(.*?\s+id\s*=\s*("|')(.*?)\3)?.*?>(.*?)</h\1>""".r

}
