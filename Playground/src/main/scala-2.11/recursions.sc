import scala.annotation.tailrec

// recursion
def factorialRec(n: Int): BigInt = {
  if (n == 0) 1
  else n * factorialRec(n - 1)
}

// following results in java.lang.StackOverflowError
// factorialRec(6000)


def factorial(n: Int): BigInt = {
  // tail recursion
  @tailrec
  def factorialTailRec(n: Int, res: BigInt): BigInt = {
    if (n == 0) res
    else factorialTailRec(n - 1, res * n)
  }
  factorialTailRec(n, BigInt(1))
}

factorial(6000)

// tailrec requires additional param that accumulates
// result which is returned by base case

// generalized definition
// function is tail recursive if its last action is to
// call method (can be the same), these calls can share
// the same frame stack

// "can be the same?" so it does not have to?
// - sure, f() calls g() and g() calls f() -> recursion

// practical definition
// In a tail-recursive function, none of the recursive
// call do additional work after the recursive call is
// complete (additional work includes printing, etc),
// except to return the value of the recursive call.

// Outcome of all of this
// should all recursions be written in tail recursive
// form?
// - not necessary, only if we know it will operate over
// large collection of data (many recursive calls)
// - if not needed, recursion is better, since such
// function is more readable, we should strive for
// clarity whenever possible
// - Donald Knuth "Premature optimization is the source
// of all evil"