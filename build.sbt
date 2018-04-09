name := "rock-paper-scissors"
organization := "me.macchiatow"
version := "1.0.1"

scalaVersion := "2.12.4"
retrieveManaged := true
parallelExecution in Test := false

lazy val root = project.in(file("."))
  .settings(libraryDependencies ++= Seq(
    "com.github.scopt" %% "scopt" % "3.7.0",
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  ))

resolvers := Seq(
  Resolver.mavenLocal,
  Resolver.mavenCentral,
  Resolver.sonatypeRepo("releases"))