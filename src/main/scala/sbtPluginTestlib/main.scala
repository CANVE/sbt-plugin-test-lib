package org.canve.sbtPluginTestLib

import org.canve.util.CanveDataIO
import java.io.{File}
import scala.sys.process._

case class Result(project: String, result: Boolean)

case class AnsiFilterer(outfile: String) extends ProcessLogger {
  
  def buffer[T](f: ⇒ T): T = f
  
  def out(s: ⇒ String): Unit = {
    print(".")  
  }
  
  def err(s: ⇒ String): Unit = {
    
  }
}

/*
 * Summary Output formatter
 */
object Summary {
  def apply(results: Array[Result]) = {
    println()
    
    val output = results map { result => 
      Console.BOLD + "[" + 
      Console.YELLOW + result.project + 
      " => " +
      Console.BOLD + (result.result match {
        case true =>  "Ok"
        case false => Console.RED + "Failed" + Console.YELLOW
      }) + Console.WHITE + "]" +
      Console.RESET
    }
    
    println(output.mkString("\n"))
    
    println()
  }
}

object Runner extends App {
  
  val testProjectsRoot = "test-projects"
  
  val results = CanveDataIO.getSubDirectories(testProjectsRoot) map { projectDirObj =>
    val projectName = projectDirObj.getName
    val projectPath = testProjectsRoot + File.separator + projectName 
    print(Console.YELLOW + Console.BOLD + s"Running the sbt plugin for $projectPath..." + Console.RESET) 
    val result = injectAndTest(projectDirObj)
    println(result match {
      case true => "finished okay"
      case false => "failed"
    })
    Result(projectName, result)
  } 
  
  Summary(results)
  
  private def injectAndTest(projectDirObj: File): Boolean = {
    // add the plugin to the project's sbt setup
    scala.tools.nsc.io.File(projectDirObj.toString + File.separator + "project/canve.sbt")
      .writeAll("""addSbtPlugin("canve" % "sbt-plugin" % "0.0.1")""" + "\n")      
     
    // run sbt for the project and get the exit code
    Process(Seq("sbt", "-Dsbt.log.noformat=true", "canve"), projectDirObj) ! (AnsiFilterer(projectDirObj.toString)) == 0
  }
}

