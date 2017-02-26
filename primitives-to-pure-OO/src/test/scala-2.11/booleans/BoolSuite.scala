package booleans

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoolSuite extends FunSuite {

  test("&& tests") {
    assertResult(tru, "t && t is t") {
      tru && tru
    }
    assertResult(fals, "t && f is f") {
      tru && fals
    }
    assertResult(fals, "f && t is f") {
      fals && tru
    }
    assertResult(fals, "f && f is f") {
      fals && fals
    }
  }

  test("|| tests") {
    assertResult(tru, "t || t is t") {
      tru || tru
    }
    assertResult(tru, "t || f is t") {
      tru || fals
    }
    assertResult(tru, "f || t is t") {
      fals || tru
    }
    assertResult(fals, "f || f is f") {
      fals || fals
    }
  }

  test("! tests") {
    assertResult(fals, "!t is f") {
      !tru
    }
    assertResult(tru, "!f is t") {
      !fals
    }
  }

  test("== tests") {
    assertResult(tru, "t == t is t") {
      tru == tru
    }
    assertResult(fals, "t == f is f") {
      tru == fals
    }
    assertResult(fals, "f == t is f") {
      fals == tru
    }
    assertResult(tru, "f == f is t") {
      fals == fals
    }
  }

  test("!= tests") {
    assertResult(fals, "t != t is f") {
      tru != tru
    }
    assertResult(tru, "t != f is t") {
      tru != fals
    }
    assertResult(tru, "f != t is t") {
      fals != tru
    }
    assertResult(fals, "f != f is f") {
      fals != fals
    }
  }

  test("^ tests") {
    assertResult(fals, "t ^ t is f") {
      tru ^ tru
    }
    assertResult(tru, "t ^ f is t") {
      tru ^ fals
    }
    assertResult(tru, "f ^ t is t") {
      fals ^ tru
    }
    assertResult(fals, "f ^ f is f") {
      fals ^ fals
    }
  }

  test("-> tests") {
    assertResult(tru, "t -> t is t") {
      tru -> tru
    }
    assertResult(fals, "t -> f is f") {
      tru -> fals
    }
    assertResult(tru, "f -> t is t") {
      fals -> tru
    }
    assertResult(tru, "f -> f is t") {
      fals -> fals
    }
  }

}
