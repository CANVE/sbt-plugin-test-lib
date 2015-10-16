package org.canve.sbtPluginTestLib

/*
 * Summary Output formatter
 */
object Summary {
  def apply(results: Array[Result]) = {
    println(Console.YELLOW + Console.BOLD + "\n\n  Summary  \n-----------")
    
    val output = results map { result => 
      Console.BOLD +  
      (result.result match {
        case true =>  Console.GREEN + "Worked Ok for project " + result.project.name
        case false => Console.RED   + "Failed for project "    + result.project.name
      }) + Console.RESET + elapsed(result.elapsed) +
      Console.RESET
    }
    
    println(output.mkString("\n"))
    
    println()
  }
  
  private def elapsed(time: Long) = f" (took $time%,.0f milliseconds)" 
}
