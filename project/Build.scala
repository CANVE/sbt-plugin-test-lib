import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    name := "sbt-plugin-test-lib",
    organization := "canve",
    version := "0.0.1",
    scalaVersion := "2.11.7",
    
    libraryDependencies ++= Seq(
      "canve" %% "compiler-plugin" % "0.0.1",
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided", // otherwise cannot use scala.tools.nsc.io.File
      "com.lihaoyi" %% "utest" % "0.3.1" % "test"
    ),
    
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
}

object MyBuild extends Build {
  import BuildSettings._

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings
  ) 
}
