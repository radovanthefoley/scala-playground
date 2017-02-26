package naturals

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NatSuite extends FunSuite {

  trait Data {
    val n0 = Zero
    val n1 = n0.successor
    val n2 = n1.successor
    val n3 = n2.successor
    val n4 = n3.successor
  }

  test("0 is 0") {
    new Data {
      assert(n0.isZero)
    }
  }

  test("Zero arithmetics") {
    new Data {
      assert(n0 - n0 isZero, "0 - 0 = 0")
      assert(n0 + n0 isZero, "0 + 0 = 0")
      assert(!(n0 + n1 isZero), "0 + 1 != 0")
    }
  }

  test("0 is minimum") {
    new Data {
      intercept[java.util.NoSuchElementException] {
        n0.predecessor
      }
      intercept[java.util.NoSuchElementException] {
        n0 - n1
      }
      intercept[java.util.NoSuchElementException] {
        n2 - (n1 + n3)
      }
    }
  }

  test("Succ arithmetics") {
    new Data {
      assert(n2 - n2 isZero, "2 - 2 = 0")
      assert(n1 + n3 - n4 isZero, "1 + 3 - 4 = 0")
      assert(!(n0 + n1 isZero), "0 + 1 != 0")
      assert(!(n1 - n0 isZero), "1 - 0 != 0")
    }
  }

  test("Succ arithmetics outside of so far defined") {
    new Data {
      assert(n4 + n1 - (n4 + n1) isZero, "4 + 1 - (4 + 1) = 0")
    }
  }
}
