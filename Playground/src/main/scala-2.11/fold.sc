val l = List(0, 1, 2)

def concat[A](xs: List[A], x: A): List[A] = {
  x :: xs
}

// ---------
// reverse can be implemented using foldLeft

l.foldLeft[List[Int]](Nil)((x, xs) => concat(x, xs))
// l.foldLeft(List[Int]())(concat) // shorter version

l.foldRight[List[Int]](Nil)((x, xs) => concat(xs, x))


// ---------
// append can be implemented using foldRight

l.foldLeft(List(3))((x, xs) => concat(x, xs))
// l.foldLeft(List(3))(concat) // shorter version

l.foldRight(List(3))((x, xs) => concat(xs, x))
