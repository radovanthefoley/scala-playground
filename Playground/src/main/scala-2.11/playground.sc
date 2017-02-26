object Obj {
  // default apply method: List(), List(1) ...
  def apply = "default"

  def apply[T](t: T) = t
}

Obj()
Obj(null)

def f1 = new Function1[Int, Int] {
  // every function under the hood
  override def apply(v1: Int): Int = v1
}
def f2 = (i: Int) => i // anonymous function assigned
val f3 = new Function1[Int, Int] {
  override def apply(v1: Int): Int = v1
}
val f4 = (i: Int) => i
def f5(i: Int) = {
  i
}

def sum(x: Int, y: Int) = x + y // partial application
val add2 = sum(2, _: Int) // = (y:Int) => sum(2,y)
add2(3)

def multiply(x: Int)(y: Int) = x * y // currying
multiply(3)(4)
def twice(y: Int) = multiply(2)(y)
twice(3)
val thrice = multiply(3) _
thrice(3)

val curriedSum = (sum _).curried // any function with multiple arguments can be curried
curriedSum(2)(3)

def capitalizeAll(args: String*) = {
  // variable length arguments
  args.map { arg =>
    arg.capitalize
  }
}
capitalizeAll("jou", "hou")

class Calculator(brand: String) {
  // everything except def in class is constructor
  // constructor
  val color: String = if (brand == "TI") {
    "blue"
  } else if (brand == "HP") {
    "black"
  } else {
    "white"
  }
}

trait Cache[K, V] {
  // types
  def get(key: K): V

  def put(key: K, value: V)

  def delete(key: K)
}

class c {
  def methodWithType[K](key: K) {}
}


class Bar(foo: String) {
  // apply
  override def toString = "Bar " + foo
}

object Bar {
  // companion object, usage: factory
  def apply(foo: String) = new Bar(foo)
}

Bar("foo") // no need for new
Bar

class Stack[T] {
  // variances
  def doNothing(e: T) {}
}

val stack = new Stack[Object]()
stack.doNothing(new Object)
stack.doNothing("string")
stack.doNothing(new AnyRef)

trait C[+T] {
  def m[U >: T](p: U)
}

// flattens Lists of whatever
def flatten(xs: List[Any]): List[Any] = {
  def flattenAny(x: Any): List[Any] = x match {
    case List() => Nil
    case y :: ys => flattenAny(y) ::: flattenAny(ys)
    case y => y :: Nil
  }
  xs match {
    case List() => Nil
    case x :: xs => flattenAny(x) ::: flatten(xs)
  }
}

flatten(List(List(1, 1), 2, List(3, List(5, 8))))


// merge from merge sort
def merge(xs: List[Int], ys: List[Int]): List[Int] =
(xs, ys) match {
  case (Nil, Nil) => List()
  case (Nil, ys) => ys
  case (xs, Nil) => ys
  case (x :: xss, y :: yss) => {
    if (x < y) x :: merge(xss, ys)
    else y :: merge(xs, yss)
  }
}

merge(List(0, 2, 4, 7), List(-1, 3, 4, 10, 15))


def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 => {
    val (same, rest) = xs.span(x.equals(_))
    same :: pack(rest)
  }
}

pack(List("a", "a", "a", "b", "c", "c", "a"))


def encode[T](xs: List[T]): List[(T, Int)] = {
  pack(xs).map(ys => (ys.head, ys.length))
}

encode(List("a", "a", "a", "b", "c", "c", "a"))


def mapFun[T, U](xs: List[T])(f: T => U): List[U] =
  (xs foldRight List[U]()) ((e: T, res: List[U]) => f(e) :: res)

mapFun(List(0, 2, 4, 7))(e => ('a' + e).toChar)


def lengthFun[T](xs: List[T]): Int =
  (xs foldRight 0) ((_: T, res: Int) => res + 1)

lengthFun(List(0, 2, 4, 7))