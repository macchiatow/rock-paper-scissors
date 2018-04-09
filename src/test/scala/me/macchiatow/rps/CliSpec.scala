package me.macchiatow.rps

import me.macchiatow.rps.Cli.Config
import org.scalatest.WordSpec

class CliSpec extends WordSpec {

  "A Cli config" when {
    "executed without arguments" should {
      "be empty" in {
        assert((new Cli).parse(List(), Config()).isEmpty)
      }
    }

    "executed with `--train` flag" should {
      "be empty when value not provided" in {
        assert((new Cli).parse(List("--train"), Config()).isEmpty)
      }

      "NOT be empty and file url is defined when value provided" in {
        assert((new Cli).parse(List("--train", "./file"), Config()).flatMap(_.file).isDefined)
      }
    }

    "executed with `--sample-size` flag" should {
      "be empty when value not provided or smaller than 3" in {
        assert((new Cli).parse(List("--sample-size", "2"), Config()).isEmpty)
        assert((new Cli).parse(List("--sample-size"), Config()).isEmpty)
      }

      "NOT be empty and sample-size defined defined correctly when value >= 3 provided" in {
        assert((new Cli).parse(List("--sample-size", "3"), Config()).exists(_.sampleSize >= 3))
      }
    }

    "executed with both `--sample-size`and `--train` flags" should {
      "be empty" in {
        assert((new Cli).parse(List("--sample-size", "3", "--train", "./file"), Config()).isEmpty)
      }
    }
  }

}
