package me.macchiatow.rps

import java.io.File

import me.macchiatow.rps.Regulation.Move
import me.macchiatow.rps.Regulation.Move.Move

import scala.collection.mutable.ListBuffer
import scala.io.Source

class TrainSet(val maxCap: Int) extends Traversable[(Move, Move)] {

  assert(maxCap >= 3)

  val list: ListBuffer[(Move, Move)] = ListBuffer()

  def teach(humanAgentMove: (Move, Move)) {
    if (list.size == maxCap) {
      list.trimStart(1)
    }
    list.append(humanAgentMove)
  }

  def foreach[U](f: ((Move, Move)) => U): Unit = list.foreach(f)

  def humanCommonMove: Move = {
    mostCommonMove(_._1)
  }

  def humanLastMove: Move = {
    list.last._1
  }

  def agentCommonMove: Move = {
    mostCommonMove(_._2)
  }

  private def mostCommonMove(participant: ((Move, Move)) => Move): Move = {
    val sortedByMost = list
      .groupBy(participant)
      .mapValues(_.size)
      .toSeq
      .sortBy(p => -p._2)

    util.Random.shuffle(
      sortedByMost.takeWhile {
        case (_: Move, times: Int) => times == sortedByMost.head._2
      }
    ).head._1
  }

}

object TrainSet {

  val DefaultCapacity = 100

  def apply(max: Int): TrainSet = new TrainSet(max)

  def apply(file: File): TrainSet = {
    val buffer: ListBuffer[(Move, Move)] = ListBuffer()

    for (
      f <- Some(file).seq if f.exists();
      line <- Source.fromFile(f).getLines;
      split = line.split(",") if split.size == 2;
      humanMove <- Move.values.find(_.toString == split(0)).seq;
      agentMove <- Move.values.find(_.toString == split(1)).seq
    ) {
      buffer.append((humanMove, agentMove))
    }

    val trainSet = TrainSet(if (buffer.size < 3) DefaultCapacity else buffer.size)
    for (pair <- buffer) {
      trainSet.teach(pair)
    }

    trainSet
  }
}
