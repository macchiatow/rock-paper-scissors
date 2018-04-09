package me.macchiatow.rps

import me.macchiatow.rps.App.Strategy.{BEAT_AGENT_MOST_MOVE, BEAT_HUMAN_MOST_MOVE, MIRROR_HUMAN_LAST, RANDOM}
import me.macchiatow.rps.Regulation.Move.{PAPER, ROCK, SCISSORS}
import org.scalatest.WordSpec
import org.scalatest.concurrent.Eventually

class AppSpec extends WordSpec with Eventually {

  "The App" when {

    val trainSet = TrainSet(20)

    Seq(
      (ROCK, ROCK),
      (PAPER, PAPER),
      (ROCK, SCISSORS),
      (ROCK, ROCK),
      (ROCK, SCISSORS),
      (PAPER, SCISSORS),
      (SCISSORS, PAPER)
    ) foreach {
      trainSet.teach
    }

    "next agent move expected" should {

      "BEAT_AGENT_MOST_MOVE be the strategy, choose ROCK" in {
        assert(App.nextAgentMove(trainSet, BEAT_AGENT_MOST_MOVE) == ROCK)
      }
      "BEAT_HUMAN_MOST_MOVE is the strategy, choose PAPER" in {
        assert(App.nextAgentMove(trainSet, BEAT_HUMAN_MOST_MOVE) == PAPER)
      }
      "MIRROR_HUMAN_LAST is the strategy, choose SCISSORS" in {
        assert(App.nextAgentMove(trainSet, MIRROR_HUMAN_LAST) == SCISSORS)
      }
    }

    "picking best strategy expected" should {
      "train set be empty, choose RANDOM" in {
        assert(App.nextStrategy(TrainSet(20), Score(), MIRROR_HUMAN_LAST) == RANDOM)
      }
      "human never won or agent won last round, use previous strategy" in {
        assert(App.nextStrategy(trainSet, Score(0, 100, agentWinLast = true), MIRROR_HUMAN_LAST) == MIRROR_HUMAN_LAST)
      }

      "human win >= 55% rounds, choose some new strategy" in {
        eventually {
          assert(App.nextStrategy(trainSet, Score(56, 44), MIRROR_HUMAN_LAST) == BEAT_HUMAN_MOST_MOVE)
        }
      }
    }
  }
}
