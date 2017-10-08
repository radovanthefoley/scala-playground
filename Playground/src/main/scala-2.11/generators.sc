// for + generator(<-) + ranges + filter(if)
for (x <- 1 to 5) yield x * x
for (x <- 1 to 3; y <- 1 to x) yield (x, y)
val flatten = (xss: List[List[Int]]) => for (xs <- xss; x <- xs) yield x
flatten(List(List(1, 2), List(3, 4))) // generators

val factors = (n: Int) => for (x <- 1 to n; if n % x == 0) yield x
factors(10) // guards

val primes = (xs: List[Int]) =>
  for (x <- xs;
       allFactors = factors(x)
       if allFactors.length == 2
       //if allFactors(0) == 1
       //if allFactors(1) == x
  ) yield x
primes(List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))

case class Company(name: String, region: String, avgSalary: Int)

case class Employee(name: String, companyName: String, age: Int)

val companies = List(Company("SAL", "HE", 2000),
  Company("GOK", "DA", 2500),
  Company("MIK", "DA", 3000))

val employees = List(Employee("Joana", "GOK", 20),
  Employee("Mikey", "MIK", 31),
  Employee("Susan", "MIK", 27),
  Employee("Frank", "GOK", 28),
  Employee("Ellen", "SAL", 29))

val result =
  for (e <- employees
       if e.age > 25;
       salary = e.age * 100;

       c <- companies
       if c.region == "DA"
       if c.name == e.companyName
       if c.avgSalary < salary
  ) yield (e.name, c.name, salary - c.avgSalary)

println(result)


// for (e <- employees if e.age < 25) yield e.name
// is the same as
// employees filter (e => e.age < 25) map (e => e.name)

def scalarProduct(xs: List[Double], ys: List[Double]): Double =
  (for ((x, y) <- xs zip ys) yield x * y) sum