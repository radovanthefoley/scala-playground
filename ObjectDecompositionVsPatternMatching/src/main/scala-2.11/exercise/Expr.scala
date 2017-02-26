package exercise

// operators precedence with minimum usage of parentheses

trait Expr {
  def show: String = this match {
    case Number(n) => n.toString
    case Var(v) => v
    case Sum(e1, e2) => e1.show + " + " + e2.show
    case Prod(e1, e2) => (e1, e2) match {
      case (Sum(_, _), Sum(_, _)) => "(" + e1.show + ") * (" + e2.show + ")"
      case (Sum(_, _), _) => "(" + e1.show + ") * " + e2.show
      case (_, Sum(_, _)) => e1.show + " * (" + e2.show + ")"
      case _ => e1.show + " * " + e2.show
    }
  }
}

case class Number(n: Int) extends Expr

case class Sum(e1: Expr, e2: Expr) extends Expr

case class Prod(e1: Expr, e2: Expr) extends Expr

case class Var(v: String) extends Expr


object Main extends App {
  val exp0 = Sum(Prod(Number(2), Var("x")), Var("y"))
  println(exp0.show)
  val exp1 = Sum(Prod(Number(2), Var("x")), Prod(Number(3), Var("y")))
  println(exp1.show)
  val exp2 = Prod(Sum(Number(2), Var("x")), Var("y"))
  println(exp2.show)
  val exp3 = Prod(Sum(Number(2), Var("x")), Sum(Number(3), Var("y")))
  println(exp3.show)
  val exp4 = Prod(Sum(Sum(Number(2), Var("x")), Number(3)), Var("y"))
  println(exp4.show)
}
