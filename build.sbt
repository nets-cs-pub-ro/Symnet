organization  := "org.change"

version       := "0.2"

scalaVersion  := "2.11.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

fork := true

libraryDependencies ++= {
  Seq(
    "org.antlr" % "antlr4" % "4.3",
    "commons-io" % "commons-io" % "2.4",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    "io.spray" %%  "spray-json" % "1.3.2",
    "org.pacesys" % "openstack4j-core" % "2.0.9",
    "org.pacesys.openstack4j.connectors" % "openstack4j-httpclient" % "2.0.9"
  )
}

test in assembly := {}

lazy val sample = taskKey[Unit]("Interpreting")

fullRunTask(sample, Compile, "org.change.v2.runners.experiments.SEFLRunner")

lazy val click = taskKey[Unit]("Symbolically running Template.click")

fullRunTask(click, Compile, "org.change.v2.runners.experiments.TemplateRunner")

lazy val symb = taskKey[Unit]("Symbolically running Template.click without validation")

fullRunTask(symb, Compile, "org.change.v2.runners.experiments.TemplateRunnerWithoutValidation")

lazy val mc = taskKey[Unit]("Running multiple VMs")

fullRunTask(mc, Compile, "org.change.v2.runners.experiments.MultipleVms")

lazy val fuck= taskKey[Unit]("Running multiple VMs")

fullRunTask(fuck, Compile, "org.change.v2.executor.clickabstractnetwork.AggregatedBuilder")

lazy val switch_bench = taskKey[Unit]("Switch Bench")

fullRunTask(switch_bench, Compile, "org.change.v2.runners.experiments.ciscoswitchtest.CiscoSwitchTestBench")

seq(Revolver.settings: _*)
