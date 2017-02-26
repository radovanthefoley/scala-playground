// polymorphic functions - abstraction over types

// EXERCISE 2: Implement isSorted, which checks whether
// an Array[A] is sorted according to a given comparison
// function.

def isSorted[A](as: Array[A], gt: (A, A) => Boolean): Boolean = {
  def f(a: A, list: List[A]): Boolean = {
    if (list.isEmpty) true
    else if (gt(a, list.head)) f(list.head, list.tail)
    else false
  }
  val list = as.toList
  if (list.isEmpty) true
  else f(list.head, list.tail)
}

isSorted[Int](Array(1, 2, 3), (a, b) => a < b)
isSorted(Array(1, 2, -1), (a: Int, b: Int) => a < b)
isSorted[Int](Array(1), (a, b) => a < b)
isSorted[Int](Array(), (a, b) => a < b)



// EXERCISE 3 (hard) partial1, takes a value and a function of two
// arguments, and returns a function of one argument as its result.
// The name comes from the fact that the function is being applied
// to some but not all of its required arguments.

def partial[A, B, C](a: A, f: (A, B) => C): B => C = {
  b => f(a, b)
}

val adder2 = partial[Int, Int, Int](2, (a, b) => a + b)
adder2(4)



// EXERCISE 4 (hard) currying, which converts a function of N arguments
// into a function of one argument that returns another function as its
// result.

def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
  a => b => f(a, b) // '=>' associates right a => b => c is a => (b => c)
}

val adderX = curry[Int, Int, Int]((a, b) => a + b)
adderX(2)(2)



// EXERCISE 5 uncurry, which reverses the transformation of curry.
// Note that since => associates to the right, A => (B => C)can be
// written asA => B => C.

def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
  (a, b) => f(a)(b)
}

val adderXuncurried = uncurry(adderX)
adderXuncurried(2, 2)



// EXERCISE 6 Implement the higher-order function that composes
// two functions.

def compose[A, B, C](f: A => B, g: B => C): A => C = {
  a => g(f(a))
}

val comp = compose[Int, Int, Int](a => a * 2, b => b + 2)
comp(1)

// This is such a common thing to want to do that Scala's standard
// library provides compose as a method on Function1. To compose
// two functions f and g, you simply say f compose g12. It also
// provides an andThen method. f andThen g is the same as g compose f