name := """ncs-music"""
organization := "com.fearlessmonkey"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies += guice

herokuAppName in Compile := "ncs-music"

herokuConfigVars in Compile := Map(
    "JDBC_DATABASE_URL" -> "jdbc:postgresql://ec2-23-21-158-253.compute-1.amazonaws.com:5432/dej9dqv3lobaei?user=cayiripqpvllwf&password=385b8db8d76358dc327fa5eaa143c2df421f3adb0dd4eb8fd8ad4e2629c8da01&sslmode=require"
)

libraryDependencies ++= Seq(
  javaJdbc,
  evolutions,
  jdbc,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "com.cloudinary" % "cloudinary-http44" % "1.13.0"
)

PlayKeys.externalizeResources := false
