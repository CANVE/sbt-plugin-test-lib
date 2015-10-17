package org.canve.sbtPluginTestLib

import org.canve.util.CanveDataIO
import java.io.{File}
import scala.sys.process._

/*
 * Runs canve for each project included under the designated directory, 
 * by adding the canve sbt plugin to the project's sbt definition.
 * 
 * Arguments to the app may constrict execution, as commented inline.   
 */
object Runner extends App {
  
  val testProjectsRoot = "test-projects"
  
  val results = CanveDataIO.getSubDirectories(testProjectsRoot) map { projectDirObj =>
    val project = Project(projectDirObj, projectDirObj.getName)
    
    /*
     * if there's no main args provided execute all tests,
     * otherwise be selective according to a first main arg's value
     */
    if ((args.isEmpty) || (args.nonEmpty && project.name.startsWith(args.head))) 
    {    
      
      val projectPath = testProjectsRoot + File.separator + project.name 
      print("\n" + Console.YELLOW + Console.BOLD + s"Running the sbt plugin for $projectPath..." + Console.RESET) 
      
      val timedExecutionResult = injectAndTest(project)
      println(timedExecutionResult.result match {
        case Okay    => "finished okay"
        case Failure => "failed"
      })
      
      Result(project, timedExecutionResult.result, timedExecutionResult.elapsed)
      
    } else {
      
      Result(project, Skipped, 0)
      
    }    
  } 
  
  Summary(results)
  
  private def injectAndTest(project: Project) = {
    // add the plugin to the project's sbt setup
    scala.tools.nsc.io.File(project.dirObj.toString + File.separator + "project/canve.sbt")
      .writeAll("""addSbtPlugin("canve" % "sbt-plugin" % "0.0.1")""" + "\n")      
     
    val outStream = new FilteringOutputWriter(RedirectionMapper(project), (new java.util.Date).toString) 
      
    // run sbt for the project and check for success exit code
    val result = TimedExecution {
      Process(Seq("sbt", "-Dsbt.log.noformat=true", "canve"), project.dirObj) ! outStream == 0 match {
        case true  => Okay
        case false => Failure
      }
    }; outStream.close
    
    result
  }
}
