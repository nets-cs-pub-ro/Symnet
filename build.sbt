organization  := "org.change"

version       := "0.1"

scalaVersion  := "2.11.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

fork := true

libraryDependencies ++= {
  Seq(
    "org.antlr" % "antlr4" % "4.3",
    "commons-io" % "commons-io" % "2.4",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    "com.googlecode.scalascriptengine" %% "scalascriptengine" % "1.3.10",
    "org.scala-lang" % "scala-library" % "2.11.1",
    "org.scala-lang" % "scala-compiler" % "2.11.1"
  )
}

lazy val interpret = taskKey[Unit]("interpreting")

// this can go either in a `build.sbt` or the settings member
//   of a Project in a full configuration
fullRunTask(interpret, Compile, "org.change.v2.runners.Run")

seq(Revolver.settings: _*)
