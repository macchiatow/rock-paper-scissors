[![Build Status](https://travis-ci.org/macchiatow/rock-paper-scissors.svg?branch=master)](https://travis-ci.org/macchiatow/rock-paper-scissors)
## What is Rock–paper–scissors?

Rock–paper–scissors (also known as paper, scissors, stone or other variants) is a hand game usually played between two people, in which each player simultaneously forms one of three shapes with an outstretched hand. These shapes are "rock" (a closed fist), "paper" (a flat hand), and "scissors" (a fist with the index and middle fingers extended, forming a V). "Scissors" is identical to the two-fingered V sign (aka "victory" or "peace sign") except that it is pointed horizontally instead of being held upright in the air. A simultaneous, zero-sum game, it has only two possible outcomes other than a tie: one of the two players wins, and the other player loses.

A player who decides to play rock will beat another player who has chosen scissors ("rock crushes scissors" or sometimes "blunts scissors"), but will lose to one who has played paper ("paper covers rock"); a play of paper will lose to a play of scissors ("scissors cuts paper"). If both players choose the same shape, the game is tied and is usually immediately replayed to break the tie.

## Why this project created?
This an educational project which in some cases can beat human strategy. Created for fun and demonstration of some typical ScalaLang concepts: pattern-matching, tail-recursion, functional programming, BDD. 

## Research background
### Can one always win at Rock-Paper-Scissors?
The Nash equilibrium of Rock-paper-scissors is random play. However, this strategy may not be optimal in real cases especially with multiple runs, since it is generally very hard for a person to act randomly, and it is attractive to exploit this non-randomness.
Approaches to win can be groped for two categories:
#### Heuristic functions
* Mirroring the opponent's last move.
* Choose the action that beats the most common action of the opponent.
* Choose the action that beats the most common action of the agent itself (manipulation).
* Choose action randomly.
#### Statistic functions
* Markov Chains
* Neural Network
* Reinforcement learning 

### Our approach
We choose heuristic functions for easiness of implementation. At a game beginning agent pick strategy to move randomly. As long as agent won last round or winning >45% overall, it will stick to the strategy. 
When both success criteria fail, agent randomly chooses new strategy from mentioned above four heuristic functions.
 

## Getting started
### Build
We use sbt, the de facto build tool for Scala. Sbt compiles, runs, and tests projects among other related tasks. We assume you know how to use a terminal. The project comes with provided sbt, you don't need to install it.

Run tests:
```go
./sbt test
```
### Play
Usage
```go
rock-paper-scissors 1.x
Usage: rock-paper-scissors [options]
  --help                 prints this usage text
  --train <file>         initial training set file
  --sample-size <value>  limit last training examples size (possible value >= 3)
```
Examples:
```go
 ./sbt "run --help"
 ./sbt "run --sample-size 15"
 ./sbt "run --train train.txt"
```
### Specification
BDD specification placed in `src/test/scala/me/macchiatow/rps/`

## Disclaimer
Since this is neither scientific nor research project, some theory background had been plagiarized.