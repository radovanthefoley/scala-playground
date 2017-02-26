package calculator

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import org.scalatest._

import TweetLength.MaxTweetLength

@RunWith(classOf[JUnitRunner])
class CalculatorSuite extends FunSuite with ShouldMatchers {

  /** ****************
    * * TWEET LENGTH **
    * *****************/

  def tweetLength(text: String): Int =
  text.codePointCount(0, text.length)

  test("tweetRemainingCharsCount with a constant signal") {
    val result = TweetLength.tweetRemainingCharsCount(Var("hello world"))
    assert(result() == MaxTweetLength - tweetLength("hello world"))

    val tooLong = "foo" * 200
    val result2 = TweetLength.tweetRemainingCharsCount(Var(tooLong))
    assert(result2() == MaxTweetLength - tweetLength(tooLong))
  }

  test("tweetRemainingCharsCount with a supplementary char") {
    val result = TweetLength.tweetRemainingCharsCount(Var("foo blabla \uD83D\uDCA9 bar"))
    assert(result() == MaxTweetLength - tweetLength("foo blabla \uD83D\uDCA9 bar"))
  }


  test("colorForRemainingCharsCount with a constant signal") {
    val resultGreen1 = TweetLength.colorForRemainingCharsCount(Var(52))
    assert(resultGreen1() == "green")
    val resultGreen2 = TweetLength.colorForRemainingCharsCount(Var(15))
    assert(resultGreen2() == "green")

    val resultOrange1 = TweetLength.colorForRemainingCharsCount(Var(12))
    assert(resultOrange1() == "orange")
    val resultOrange2 = TweetLength.colorForRemainingCharsCount(Var(0))
    assert(resultOrange2() == "orange")

    val resultRed1 = TweetLength.colorForRemainingCharsCount(Var(-1))
    assert(resultRed1() == "red")
    val resultRed2 = TweetLength.colorForRemainingCharsCount(Var(-5))
    assert(resultRed2() == "red")
  }

  test("TweetLength with variable signal") {
    val input: Var[String] = Var("I am Tweet")
    val remainer: Signal[Int] = TweetLength.tweetRemainingCharsCount(input)
    val color: Signal[String] = TweetLength.colorForRemainingCharsCount(remainer)
    assert(color() == "green")

    input.update("I am Tweet" * 14)
    assert(color() == "orange")

    input.update("I am Tweet" * 15)
    assert(color() == "red")
  }

  test("Polynomial with variable signal") {
    val a = Var(1d)
    val b = Var(0d)
    val c = Var(1d)
    val delta = Polynomial.computeDelta(a, b, c)
    val result: Signal[Set[Double]] = Polynomial.computeSolutions(a, b, c, delta)

    assert(result() === Set())

    c.update(0d)
    assert(result() === Set(0d))

    b.update(-3d)
    c.update(2d)
    assert(result() === Set(1d, 2d))
  }

  test("y = x^2") {
    val a = Var(1d)
    val b = Var(0d)
    val c = Var(0d)
    val delta = Polynomial.computeDelta(a, b, c)
    val result: Signal[Set[Double]] = Polynomial.computeSolutions(a, b, c, delta)

    assert(result() === Set(0d))
  }

  test("Calculator multiref") {
    val aSig: Signal[Expr] = Var(Literal(0))
    val bSig: Signal[Expr] = Var(Ref("a"))
    val cSig: Signal[Expr] = Var(Ref("b"))

    assert(Calculator.computeValues(Map(("a", aSig), ("b", bSig), ("c", cSig))).map { case (v, sig) => (v, sig()) }
      === Map(("a", 0d), ("b", 0d), ("c", 0d)))
  }

  test("Calculator cyclic") {
    val aSig: Var[Expr] = Var(Ref("c"))
    val bSig: Var[Expr] = Var(Ref("a"))
    val cSig: Var[Expr] = Var(Ref("b"))

    val inputs: Map[String, Var[Expr]] = Map("a" -> aSig, "b" -> bSig, "c" -> cSig)
    val outputs: Map[String, Signal[Double]] = Calculator.computeValues(inputs)

    // a -> c
    // b -> a
    // c -> b
    assert(outputs.count { case (v, sig) => java.lang.Double.isNaN(sig()) } == 3)

    // a -> 0
    // b -> a
    // c -> b
    aSig.update(Literal(0))
    assert(outputs.map { case (v, sig) => (v, sig()) } === Map("a" -> 0d, "b" -> 0d, "c" -> 0d))

    // a -> c
    // b -> a
    // c -> b
    aSig.update(Ref("c"))
    assert(outputs.count { case (v, sig) => java.lang.Double.isNaN(sig()) } == 3)

    // a -> a + 1
    // b -> a
    // c -> b
    aSig.update(Plus(Ref("a"), Literal(1)))
    assert(outputs.count { case (v, sig) => java.lang.Double.isNaN(sig()) } == 3)

    // a -> c + 1
    // b -> a
    // c -> b
    aSig.update(Plus(Ref("c"), Literal(1)))
    assert(outputs.count { case (v, sig) => java.lang.Double.isNaN(sig()) } == 3)

    // a -> c + 1
    // b -> a
    // c -> 1
    cSig.update(Literal(1))
    assert(outputs.map { case (v, sig) => (v, sig()) } === Map("a" -> 2d, "b" -> 2d, "c" -> 1d))
  }

  test("Calculator Literal") {
    // setup
    val aSig: Var[Expr] = Var(Literal(1.0))
    val inputs: Map[String, Var[Expr]] = Map("a" -> aSig)
    val outputs: Map[String, Signal[Double]] = Calculator.computeValues(inputs)

    // your desired test, output signal Signal[Double] mapped to Double
    assert(outputs.map { case (v, sig) => (v, sig()) } === Map("a" -> 1.0))

    // changing input testing new output
    aSig.update(Literal(2.0))
    assert(outputs.map { case (v, sig) => (v, sig()) } === Map("a" -> 2.0))
  }

}
