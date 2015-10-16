package org.canve.sbtPluginTestLib

/*
 * Summary Output formatter
 */
object Summary {
  def apply(results: Array[Result]) = {
    println(Console.YELLOW + Console.BOLD + "\n\n Summary \n---------\n")
    
    val output = results map { result => 
      Console.BOLD + "[" + 
      Console.YELLOW + result.project.name + 
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
