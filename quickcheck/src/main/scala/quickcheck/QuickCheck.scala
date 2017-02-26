package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    x <- arbitrary[A]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(x, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  def allegedlySorted(h: H): List[A] = {
    if (isEmpty(h)) Nil
    else findMin(h) :: allegedlySorted(deleteMin(h))
  }

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("minAfterMeld") = forAll { (h1: H, h2: H) =>
    val h1NotEmpty = insert(0, h1)
    val h2NotEmpty = insert(0, h2)
    val globalMin = ord.min(findMin(h1NotEmpty), findMin(h2NotEmpty))
    globalMin == findMin(meld(h1NotEmpty, h2NotEmpty))
  }

  property("insertion") = forAll { (h: H) =>
    val list = allegedlySorted(h)
    def inserted(l: List[A], h: H): H = l match {
      case Nil => h
      case x :: xs => inserted(xs, insert(x, h))
    }
    val hIns = inserted(list, empty)
    list == allegedlySorted(hIns)
  }

  property("melding") = forAll { (h: H) =>
    val list = allegedlySorted(h)
    def melded(l: List[A], h: H): H = l match {
      case Nil => h
      case x :: xs => melded(xs, meld(insert(x, empty), h))
    }
    val hIns = melded(list, empty)
    list == allegedlySorted(hIns)
  }

  property("empty") = forAll { (x: A, y: A) =>
    isEmpty(deleteMin(insert(0, empty)))
    findMin(insert(y, insert(x, empty))) == ord.min(x, y)
  }

}
