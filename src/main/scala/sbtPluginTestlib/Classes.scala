package org.canve.sbtPluginTestLib

case class Project(dirObj: java.io.File, name: String)
case class Result(project: Project, result: Boolean, elapsed: Long)
