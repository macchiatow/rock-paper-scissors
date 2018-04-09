package me.macchiatow.rps

import java.io.File

import me.macchiatow.rps.Regulation.Move._
import org.scalatest.{Matchers, WordSpec}

class TrainSetSpec extends WordSpec with Matchers {


  "A TrainSet" when {
    "created from a file" should {
      "have only valid pairs" in {
        val trainSet = TrainSet(new File(getClass.getResource("/trainset_halfbroken.txt").getPath))
        assert(trainSet.size == 2)
        assert(trainSet.maxCap == TrainSet.DefaultCapacity)
      }

      "be empty and be of default capacity if file broken or does not exist" in {
        val trainSet = TrainSet(new File(getClass.getResource("/trainset_broken.txt").getPath))
        assert(trainSet.maxCap == TrainSet.DefaultCapacity)
        assert(trainSet.isEmpty)

        val trainSet2 = TrainSet(new File("./notexist"))
        assert(trainSet2.maxCap == TrainSet.DefaultCapacity)
        assert(trainSet2.isEmpty)
      }

      "be relevant with correct file" in {
        val trainSet = TrainSet(new File(getClass.getResource("/trainset.txt").getPath))
        assert(trainSet.maxCap == 16)
        assert(trainSet.size == 16)
      }
    }

    "instantiated with 0" should {
      "throw an exception" in {
        assertThrows[AssertionError] {
          TrainSet(0)
        }
      }
    }

    "samples provided" should {
      "samples queue not exceed defined value" in {
        val size = 20
        val trainSet = new TrainSet(size)

        (0 to 30) foreach { _ =>
          trainSet.teach((ROCK, ROCK))
        }

        assert(trainSet.size == size)
      }

      "handle agentCommonMove, humanCommonMove and humanLastMove helpers" in {
        val trainSet = new TrainSet(20)

        Seq(
          (ROCK, ROCK),
          (PAPER, PAPER),
          (ROCK, SCISSORS),
          (ROCK, SCISSORS),
          (ROCK, SCISSORS),
          (PAPER, SCISSORS),
          (PAPER, ROCK)
        ) foreach {
          trainSet.teach
        }

        assert(trainSet.agentCommonMove == SCISSORS)
        assert(trainSet.humanCommonMove == ROCK)
        assert(trainSet.humanLastMove == PAPER)
      }
    }
  }
}
