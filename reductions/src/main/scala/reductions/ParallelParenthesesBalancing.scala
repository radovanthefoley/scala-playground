package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false
  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer (new Warmer.Default)

  def main(args: Array[String]): Unit = {
    //val length = 100000000
    val length = 10000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def balance(chars: Array[Char]): Boolean = {
    def balance(chars: List[Char], level: Int): Boolean = {
      if (level < 0) false
      else if (chars.isEmpty) {
        if (level == 0) true
        else false
      } else {
        if (chars.head == '(') balance(chars.tail, level + 1)
        else if (chars.head == ')') balance(chars.tail, level - 1)
        else balance(chars.tail, level)
      }
    }

    balance(chars.toList, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def zeroIfSubstractNegative(a: Int, b: Int) = {
      val c = a - b
      if (c > 0) c
      else 0
    }

    def traverse(idx: Int, until: Int, unbL: Int, unbR: Int): (Int, Int) = {
      if (idx == until) (unbL, unbR)
      else {
        val z = chars.charAt(idx)
        if (z == '(') {
          traverse(idx + 1, until, unbL + 1, unbR)
        } else if (z == ')') {
          if (unbL > 0) traverse(idx + 1, until, unbL - 1, unbR)
          else traverse(idx + 1, until, unbL, unbR + 1)
        } else traverse(idx + 1, until, unbL, unbR)
      }
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from <= threshold) traverse(from, until, 0, 0)
      else {
        val mid = (from + until) / 2
        parallel(reduce(from, mid), reduce(mid, until)) match {
          case (l, r) => (zeroIfSubstractNegative(l._1, r._2) + r._1, l._2 + zeroIfSubstractNegative(r._2, l._1))
        }
      }
    }

    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
