package me.macchiatow.rps

case class Score(humanScore: Int = 0, agentScore: Int = 0, agentWinLast: Boolean = false) {

  def update(result: Regulation.RoundResult.RoundResult): Score = {
    Score(humanScore + result.human, agentScore + result.agent, result == Regulation.RoundResult.HUMAN_LOST)
  }
}