package me.macchiatow.rps

import me.macchiatow.rps.Regulation.Move.{PAPER, ROCK, SCISSORS}

object Regulation {

  object Move extends Enumeration {
    type Move = Value
    val ROCK, PAPER, SCISSORS = Value

    def random(): Move = Move(scala.util.Random.nextInt(Move.maxId))
  }

  // key is beaten by value
  val Rules = Map(
    ROCK -> PAPER,
    PAPER -> SCISSORS,
    SCISSORS -> ROCK
  )

  object RoundResult extends Enumeration {

    sealed case class RoundResult(human: Int, agent: Int) extends Val

    val HUMAN_WON = RoundResult(1, 0)
    val HUMAN_LOST = RoundResult(0, 1)
    val DRAW = RoundResult(0, 0)
  }

}
