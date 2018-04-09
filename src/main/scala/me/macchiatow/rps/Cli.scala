package me.macchiatow.rps

import java.io.File

class Cli extends scopt.OptionParser[Cli.Config]("rock-paper-scissors") {
  head("rock-paper-scissors", "1.x")

  help("help").text("prints this usage text")

  opt[File]("train").optional().valueName("<file>").
    action((x, c) => c.copy(file = Some(x))).
    text("initial training set file")

  opt[Int]("sample-size").action((x, c) =>
    c.copy(sampleSize = x)).text("limit last training examples size (possible value >= 3)")

  checkConfig(c => if (c.file.isDefined && c.sampleSize > 0 || (c.file.isEmpty && c.sampleSize < 3))
    failure("Exactly one of train or sample-size flags must be set") else success)
}

object Cli {

  case class Config(sampleSize: Int = 0, file: Option[File] = None)

}