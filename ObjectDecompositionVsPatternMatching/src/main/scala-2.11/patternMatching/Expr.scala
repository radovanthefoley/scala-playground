package patternMatching

// pattern case matching style handy in case more new methods in future is expected than new subclasses
// matching methods can be defined also outside the class hierarchy (def eval(e: Expr): Int = e match{...})

trait Expr {
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }
  def show: String = this match {
    case Number(n) => n.toString
    case Sum(e1, e2) => e1.show + " + " + e2.show
  }
}

case class Number(n: Int) extends Expr

case class Sum(e1: Expr, e2: Expr) extends Expr


object Main extends App {
  val exp = Sum(Sum(Number(1), Number(2)), Number(3))

  println(exp.show)
  println(exp.eval)
}
