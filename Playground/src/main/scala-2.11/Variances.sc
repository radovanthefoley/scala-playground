class Something
class Creature extends Something
class Human extends Creature
class Dog extends Creature

class SackInvariant[A] {}
def install(softDrinkVM: SackInvariant[Creature]): Unit = {}
install(new SackInvariant[Creature])
//install(new SackInvariant[Human])
//install(new SackInvariant[Something])
def returnInvariant(): SackInvariant[Creature] = {
  new SackInvariant[Creature]
}

class SackCovariant[+A] {}
def install(softDrinkVM: SackCovariant[Creature]): Unit = {}
install(new SackCovariant[Creature])
install(new SackCovariant[Human])
//install(new SackCovariant[Something])
def returnCovariant(): SackCovariant[Creature] = {
  new SackCovariant[Creature]   //imagine return
  new SackCovariant[Human]
  //new SackCovariant[Something]
}

class SackContravariant[-A] {}
def install(softDrinkVM: SackContravariant[Creature]): Unit = {}
install(new SackContravariant[Creature])
//install(new SackContravariant[Human])
install(new SackContravariant[Something])
def returnContravariant(): SackContravariant[Creature] = {
  new SackContravariant[Creature] //imagine return
  //new SackContravariant[Human]
  new SackContravariant[Something]
}



sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  }
  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }
  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}


val example = Cons(1, Cons(2, Cons(3, Nil)))
val total = List.sum(example)
val example2: List[Any] = List(1,2,3, new Object())
//val total2 = List.sum(example)


// covariance and contravariance subtyping
//
// Function[-T,+R]
//
// AnyVal
//   ^
// Int
//   ^
// Nothing

val f1 = new Function[Int, Int] {
  override def apply(v1: Int): Int = v1
}
val f2: Function[Nothing, AnyVal] = f1

// f2 >: f1 (f1 is subtype of f2)
// this holds in order for f1 to safely conform
// restrictions coming from f2 contract