package booleans

/**
  * Pure OO representation of booleans
  */
abstract class Bool {
  def ifThenElse[T](t: => T, e: => T): T

  def && (that: => Bool): Bool = ifThenElse(that, fals)
  def || (that: => Bool): Bool = ifThenElse(tru, that)
  def ^ (that: => Bool): Bool = ifThenElse(!that,that)
  def -> (that: => Bool): Bool = ifThenElse(that,tru)

  def unary_!(): Bool = ifThenElse(fals, tru)

  def ==(that: => Bool): Bool = ifThenElse(that, !that)
  def !=(that: => Bool): Bool = ifThenElse(!that, that)
}

object tru extends Bool {
  override def ifThenElse[T](t: => T, e: => T): T = t

  override def toString: String = "true"
}

object fals extends Bool {
  override def ifThenElse[T](t: => T, e: => T): T = e

  override def toString: String = "false"
}

object Main extends App {
  println(tru && tru)
}
