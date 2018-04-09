package me.macchiatow.rps

import me.macchiatow.rps.App.Strategy.Strategy
import me.macchiatow.rps.Cli.Config
import me.macchiatow.rps.Regulation.Move.{Move, PAPER, ROCK, SCISSORS}
import me.macchiatow.rps.Regulation.RoundResult

import scala.annotation.tailrec

object App {

  object Strategy extends Enumeration {
    type Strategy = Value
    val MIRROR_HUMAN_LAST, BEAT_HUMAN_MOST_MOVE, BEAT_AGENT_MOST_MOVE, RANDOM = Value

    def newStrategy(): Strategy = Strategy(scala.util.Random.nextInt(Strategy.maxId))
  }

  def main(args: Array[String]): Unit = {
    (new Cli).parse(args, Config()) foreach { conf =>
      val trainSet = conf.file.map(TrainSet(_)) getOrElse TrainSet(conf.sampleSize)

      readChoice(trainSet, Score(), currentStrategy = Strategy.RANDOM)
      println("Bye bye!")
    }
  }

  @tailrec
  def readChoice(trainSet: TrainSet, score: Score, currentStrategy: Strategy): Unit = {
    scala.io.StdIn.readLine("Choose optimal strategy to win: (r)ock, (p)aper, (s)cissors or (q)uit, (S)core # ") match {
      case "r" | "rock" => play(humanMove = ROCK, trainSet, score, currentStrategy)
      case "p" | "paper" => play(humanMove = PAPER, trainSet, score, currentStrategy)
      case "s" | "scissors" => play(humanMove = SCISSORS, trainSet, score, currentStrategy)

      case "q" | "quit" =>
      case "S" | "score" =>
        printScore(score)
        readChoice(trainSet, score, currentStrategy)
      case invalid =>
        println(s"Cannot recognize '$invalid'")
        readChoice(trainSet, score, currentStrategy)
    }
  }

  def nextStrategy(trainSet: TrainSet, score: Score, currentStrategy: Strategy): Strategy = score match {
    case _ if trainSet.isEmpty => Strategy.RANDOM
    case Score(humanScore, _, agentWinLast) if humanScore == 0 || agentWinLast => currentStrategy
    case Score(humanScore, agentScore, _) if agentScore < .45f * (agentScore + humanScore)=> Strategy.newStrategy()
    case _ => currentStrategy
  }


  def play(humanMove: Move, trainSet: TrainSet, score: Score, currentStrategy: Strategy): Unit = {
    val agentStrategy = nextStrategy(trainSet, score, currentStrategy)
    val agentMove = nextAgentMove(trainSet, agentStrategy)

    print(s"Agent move: $agentMove, ")

    val roundScore = humanMove match {
      case hm if hm == agentMove =>
        println("draw")
        RoundResult.DRAW
      case hm if hm != Regulation.Rules(agentMove) =>
        println("you lost!")
        RoundResult.HUMAN_LOST
      case _ =>
        println("you win!")
        RoundResult.HUMAN_WON
    }

    trainSet.teach((humanMove, agentMove))

    val newScore = score.update(roundScore)
    printScore(newScore)
    readChoice(trainSet, newScore, agentStrategy)
  }

  def nextAgentMove(trainSet: TrainSet, strategy: Strategy): Move = strategy match {
    case Strategy.RANDOM => Regulation.Move.random()
    case Strategy.MIRROR_HUMAN_LAST => trainSet.humanLastMove
    case Strategy.BEAT_HUMAN_MOST_MOVE => Regulation.Rules(trainSet.humanCommonMove)
    case Strategy.BEAT_AGENT_MOST_MOVE => Regulation.Rules(trainSet.agentCommonMove)
  }


  def printScore(score: Score): Unit = {
    println(s"Score: You ${score.humanScore} : Agent ${score.agentScore}")
  }
}