// Stream's tail is evaluated only if needed

val xs = Stream(1, 2, 3)
val xsAlt = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty))) // same as above
(1 to 1000).toStream // => Stream(1, ?)
0 #:: xs

// Same as Stream.cons(x, xs)
// In the Stream's cons operator, the second parameter (the tail)
// is defined as a "call by name" parameter.
// Note that x::xs always produces a List

xs match {
  case 1 #:: xs => println(xs)
}

def streamRange(lo: Int, hi: Int): Stream[Int] = {
  print(s"${lo} ")
  if(lo >= hi)  Stream.empty
  else lo #:: streamRange(lo + 1, hi)
}

streamRange(1, 10).take(3)
streamRange(1, 10).take(3).toList

// lazy eval thanks to call by name
// and lazy val combination:

// def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
//    def head = hd
//    lazy val tail = tl
//    ...
// }

// if lazy val was not used, tail would be recomputed
// everytime when used