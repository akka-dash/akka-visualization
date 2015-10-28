organization  := "io.akka.visualization"

name := "akka-visualization"

scalaVersion := "2.11.6"

seq(Revolver.settings: _*)

val sprayVersion = "1.3.3"
val akkaVersion = "2.3.4"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "io.spray" %% "spray-can" % sprayVersion,
  "io.spray" %%  "spray-routing" % sprayVersion,
  "io.spray" %%  "spray-json" % "1.3.1",
  "io.spray" %% "spray-testkit" % sprayVersion % "test",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka"  %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka"  %% "akka-testkit" % akkaVersion % "test"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions += "-target:jvm-1.7"

isSnapshot := true

publishMavenStyle := true

scalariformSettings