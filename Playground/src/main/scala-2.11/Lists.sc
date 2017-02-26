val list1 = List(0, 1, 2, 3)
val list2 = List(4, 5)

list1(0)

list1 ::: list2 // O(n_Of_list1)
list2.:::(list1)// previous in fact transform to this
list1 ++ list2  // generalized version of previous for any traversable


-1 :: list1
list1 :: list2
list2.::(list1) // previous in fact transform to this

val xs = List(
  Set(1, 2, 3),
  Set(1, 2, 3)
).flatten

list1.head      // O(1)

list1.length

list1.last      // O(n)

list1.init      // return all except the last, exception if empty, O(n)
List(0).init

list1.tail      // O(1)

list1 take 2
list1 take 10

list1 drop 2
list1 drop 10

list1.reverse

list1 updated(0, -1)

list1 indexOf 1

list1 contains 0

val nums = List(2, -4, 5, 7, 1)

nums filter(_ > 0)
nums filterNot(_ > 0)
nums partition(_ > 0)   // both previous into pair in 1 traversal

nums takeWhile (_ > 0)
nums dropWhile(_ > 0)
nums span(_ > 0)        // both previous into pair in 1 traversal