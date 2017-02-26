package oodecomposition

// object decomposition style handy in case more new subclasses in future is expected than new methods

trait Expr {
  def eval: Int
  def show: String
}

class Number(n: Int) extends Expr {
  override def eval = n
  override def show: String = n.toString
}

class Sum(e1: Expr, e2: Expr) extends Expr{
  override def eval = e1.eval + e2.eval
  override def show: String = e1.show + " + " + e2.show
}

object Main extends App {
  val one = new Number(1)
  val two = new Number(2)
  val three = new Number(3)

  val exp = new Sum(new Sum(one, two), three)
  println(exp.show)
  println(exp.eval)
}
