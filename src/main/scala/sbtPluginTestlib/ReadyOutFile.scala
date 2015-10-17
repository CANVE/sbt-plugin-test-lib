package org.canve.sbtPluginTestLib

object ReadyOutFile {
  import java.io.File
  def apply(path: String, fileName: String): File = {
    scala.tools.nsc.io.Path(path).createDirectory(failIfExists = false)
    new File(path + File.separator + fileName)
  }
}

