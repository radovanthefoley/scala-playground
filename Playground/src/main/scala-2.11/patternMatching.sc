// :: is called 'cons'
val fruitList1 = List("apple", "banana") // is syntax sugar for:
val fruitList2 = "apple" :: ("banana" :: Nil) // cons associated from right:
val fruitList3 = "apple" :: "banana" :: Nil // which is finally translated to
val fruitList4 = Nil.::("banana").::("apple") // :: ~ prepend

val emptyList1 = List() // is syntax sugar for:
val emptyList2: List[Nothing] = Nil


def listMatcher(list: List[Any]): String = list match {
  case List(2 :: xs) => "contains list starting with 2 as only element"
  case 1 :: 2 :: xs => "starts with 1 and 2"
  case xs :: 4 :: Nil => "has at least two and ends with 4"
  case 4 :: Nil => "ends with 4"
  case x :: Nil => "has one element" // same as case List(x)
  case Nil => "is empty" // same as case List()
  case _ => "none of defined"
}

println(listMatcher(List(1, 2)))
println(listMatcher(List(1, 2, 3)))
println(listMatcher(List(1)))
println(listMatcher(List()))
println(listMatcher(List(List(2))))
println(listMatcher(List(List(2, 3))))
println(listMatcher(List(4)))
println(listMatcher(List(1, 4)))