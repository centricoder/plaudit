name := "plaudit"
organization := "biz.netcentric"
version := "0.1"
scalaVersion := "2.12.5"

lazy val akka_version = "2.5.12"

dependencyOverrides ++= Dependencies.dependencyOverrides

libraryDependencies ++= Dependencies.serviceDependencies

testOptions in Test += Tests.Argument("-l", "org.scalatest.tags.Slow")
