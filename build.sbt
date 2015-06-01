import AssemblyKeys._
assemblySettings

name := "s-event"

version := "0.1"

scalaVersion := "2.11.6"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.11"

addCompilerPlugin(
  "org.scala-lang.plugins" % "scala-continuations-plugin_2.11.6" % "1.0.2")

libraryDependencies +=
  "org.scala-lang.plugins" %% "scala-continuations-library" % "1.0.2"

scalacOptions += "-P:continuations:enable"