package org.canve.sbtPluginTestLib
/*
 * Mapping between project names and the file path
 * where their output will be redirected into
 */
object RedirectionMapper {
  def apply(project: Project) = {
    new java.io.File(project.name + ".out")
  }
}
