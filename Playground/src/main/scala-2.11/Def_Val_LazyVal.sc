def expr = {

  // evaluated immediately
  val x = {print("val "); 1}

  // evaluated once when needed
  lazy val y = {print("lazyVal "); 2}

  // evaluated when called
  def z = {print("def "); 3}


  z + y + x + z + y + x
}

expr

// look at the following, reminder: Stream's cons (#::)
// calls tail by name + tail is defined as lazy val
// Strange? lazy val overcomes forward reference error
// in this particular case :)

def sqrt(x: Double): Stream[Double] = {
  def improve(guess: Double) = (guess + x/guess) / 2
  lazy val guesses: Stream[Double] =
    1 #:: (guesses map improve)
  guesses
}

sqrt(4).take(8).toList

// just for perfect solution:

def goodEnough(guess: Double, x: Double) = {
  math.abs((guess * guess - x) / x) < 0.0001
}

val x = 4
sqrt(x).filter(goodEnough(_, x)).head
