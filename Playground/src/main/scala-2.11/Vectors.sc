val v = Vector("one", "two", "three")
val vPrep = "zero" +: v
val vAppend = v :+ "four"

new Object() +: v

// vectors are implemented as shallow trees, array of
// 32, if more than 32 elements are needed, every cell
// becomes pointer to the next 32 sized array etc.
// quick for access, log32(N) for inserts (opposite
// to Lists)