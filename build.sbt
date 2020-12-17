ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

// ThisBuild / Test / fork := true

ThisBuild / parallelExecution := true

lazy val root = (project in file("."))
  .aggregate(common, weaver_051, weaver_061)
  .settings(
    name := "Weaver Classloader Issue"
  )

lazy val common = project
  .in(file("common"))
  .settings(PlaySettings)
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-effect" % "2.3.0"
  )

lazy val weaver_051 = project
  .in(file("weaver-051"))
  .settings(
    libraryDependencies += "com.disneystreaming" %% "weaver-framework" % "0.5.1" % Test,
    testFrameworks += new TestFramework("weaver.framework.TestFramework")
  )
  .dependsOn(common)

lazy val weaver_061 = project
  .in(file("weaver-061"))
  .settings(
    libraryDependencies += "com.disneystreaming" %% "weaver-cats" % "0.6.0-M1" % Test,
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )
  .dependsOn(common)

lazy val PlaySettings = Seq(
  libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.2"
)
