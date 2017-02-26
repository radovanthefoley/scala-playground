// Seq is super class for:
// List
// Vector
// Range
// String (java class, not direct)
// Array (java class, not direct)

val s = "Hello World!"

s exists(_.isUpper)

s forall(_.isUpper)

val pairs = List(1,2,3) zip s

pairs.unzip

s flatMap(List('.', _))

// s flatMap f -> s map(f) flatten

s.sum

s.max
