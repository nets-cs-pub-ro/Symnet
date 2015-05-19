organization  := "org.change"

version       := "0.1"

scalaVersion  := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.0"
  Seq(
    "org.antlr" % "antlr4" % "4.3",
    "commons-io" % "commons-io" % "2.4",
    "org.scalatest" % "scalatest_2.10" % "2.2.4" % "test"
  )
}

seq(Revolver.settings: _*)
