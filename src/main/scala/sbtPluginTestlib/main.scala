package org.canve.sbtPluginTestLib

import org.canve.util.CanveDataIO
import java.io.{File}

object app extends App {
  
  val testProjectsRoot = "test-projects"
  
  CanveDataIO.getSubDirectories(testProjectsRoot) map { projectDirObj =>
    val projectName = projectDirObj.getName
    val result = injectAndTest(projectDirObj)
    (projectName, result)
  }
  
  def injectAndTest(projectDirObj: File) {
    scala.tools.nsc.io.File(projectDirObj.toString + File.separator + "project/canve.sbt")
      .writeAll("""addSbtPlugin("canve" % "sbt-plugin" % "0.0.1")""" + "\n")              
  }
  
  
  
}

