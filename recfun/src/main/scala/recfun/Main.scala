package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def balance(chars: List[Char], level: Int): Boolean = {
      if (level < 0) false
      else {
        if (chars.isEmpty) {
          if (level == 0) {
            true
          } else {
            false
          }
        } else {
          if (chars.head == '(') {
            balance(chars.tail, level + 1)
          } else if (chars.head == ')') {
            balance(chars.tail, level - 1)
          } else {
            balance(chars.tail, level)
          }
        }
      }
    }
    balance(chars, 0)
  }

  /**
    * Exercise 3
    */
  /*  def countChange(money: Int, coins: List[Int]): Int = {
      val coinsSorted = coins.sortWith(_ < _)
      var count = 0
      def counting(sum: Int, index: Int): Unit = {
        if (sum + coinsSorted(index) == money) {
          count += 1
        }
        else if (sum < money) {
          for (i <- 0 to index) {
            counting(sum + coinsSorted(index), i)
          }
        }
      }

      for (i <- 0 to coinsSorted.size - 1) {
        counting(0, i)
      }
      count
    }*/
  def countChange(money: Int, coins: List[Int]): Int = {
    def count(m: Int, c: List[Int]): Int = {
      if (c.isEmpty) 0
      else if (m - c.head == 0) 1
      else if (m - c.head < 0) 0
      else count(m - c.head, c) + count(m, c.tail)
    }
    count(money, coins.sorted)
  }

/*  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0) 0
    else if (money == 0) 1
    else (for (c <- coins) yield countChange(money - c, coins.drop(coins.indexOf(c)))).sum
  }*/
}
