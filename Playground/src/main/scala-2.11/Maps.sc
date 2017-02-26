("SVK" -> "BA", "AUS" -> "VIE")

val capitals = Map("SVK" -> "BA", "AUS" -> "VIE")

// direct access causes exception
//capitals("CZE")

capitals get "SVK" // returns Option <-- Some
capitals get "CZE" // returns Option <-- None

// which helps with pattern matching
def showCapital(country: String) = capitals get country match {
  case Some(capital) => capital
  case None => "missing data"
}

showCapital("CZE")
showCapital("SVK")

// alternative are maps as total functions
val capitalsDirect = capitals withDefaultValue "missing data"
capitalsDirect("CZE")

val fruits = List("apple", "peach", "lemon", "pear")
val grupedFruits: Map[Char, List[String]] = fruits.groupBy(_.head)


val map1 = Map(0 -> 0, 1 -> 1)
val map2 = Map(1 -> 10, 4 -> 4)

// second is superimposed on top of first in ++ op
map1 ++ map2
map2 ++ map1