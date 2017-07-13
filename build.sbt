name := """ncs-music"""
organization := "com.fearlessmonkey"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies += guice

herokuAppName in Compile := "tranquil-mountain-13167"

herokuConfigVars in Compile := Map(
  "JDBC_DATABASE_URL" -> "jdbc:postgresql://ec2-23-21-158-253.compute-1.amazonaws.com:5432/dej9dqv3lobaei"
)

//Dependency for jdbc
libraryDependencies += javaJdbc

libraryDependencies ++= Seq(
  javaJdbc,
  evolutions,
  jdbc,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
)

PlayKeys.externalizeResources := false
