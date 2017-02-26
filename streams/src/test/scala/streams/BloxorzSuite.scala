package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
      * This method applies a list of moves `ls` to the block at position
      * `startPos`. This can be used to verify if a certain list of moves
      * is a valid solution, i.e. leads to the goal.
      */
    def solve(ls: List[Move]): Block =
    ls.foldLeft(startBlock) { case (block, move) => move match {
      case Left => block.left
      case Right => block.right
      case Up => block.up
      case Down => block.down
    }
    }
  }

  trait Level0 extends SolutionChecker {
    /* terrain for level 1*/

    val level =
      """------
        |--ST--
        |--oo--
        |--oo--
        |------""".stripMargin

    val optsolution = List(Down, Right, Up)
  }

  test("terrain function level 0") {
    new Level0 {
      assert(!terrain(Pos(0, 0)), "0,0")
      assert(terrain(Pos(1, 2)), "1,2") // start
      assert(terrain(Pos(1, 3)), "1,3") // goal
      assert(terrain(Pos(2, 2)), "2,2")
      assert(terrain(Pos(2, 3)), "2,3")
      assert(terrain(Pos(3, 2)), "3,2")
      assert(terrain(Pos(3, 3)), "3,3")
      assert(!terrain(Pos(4, 2)), "4,2")
      assert(!terrain(Pos(4, 3)), "4,3")
      assert(!terrain(Pos(1, 1)), "1,1")
      assert(!terrain(Pos(-1, -1)), "-1,-1")
      assert(!terrain(Pos(1, -144)), "0,-144")
    }
  }

  test("findChar level 0") {
    new Level0 {
      assert(startPos == Pos(1, 2))
      assert(goal == Pos(1, 3))
    }
  }

  test("block standing level 0") {
    new Level0 {
      assert(Block(Pos(1, -48), Pos(1, -48)).isStanding)
    }
  }

  test("block legal level 0") {
    new Level0 {
      assert(!Block(Pos(1, -48), Pos(1, -48)).isLegal)
    }
  }

  test("neighborsWithHistory level 0") {
    new Level0 {
      assert(neighborsWithHistory(startBlock, Nil).toSet === Set(
        (Block(Pos(2, 2), Pos(3, 2)), List(Down))
      ))
      assert(neighborsWithHistory(Block(Pos(2, 2), Pos(3, 2)), List(Down)).toSet === Set(
        (Block(Pos(2, 3), Pos(3, 3)), List(Right, Down)),
        (Block(Pos(1, 2), Pos(1, 2)), List(Up, Down))
      ))
      assert(neighborsWithHistory(Block(Pos(2, 3), Pos(3, 3)), List(Right, Down)).toSet === Set(
        (Block(Pos(2, 2), Pos(3, 2)), List(Left, Right, Down)),
        (Block(Pos(1, 3), Pos(1, 3)), List(Up, Right, Down))
      ))
      assert(neighborsWithHistory(Block(Pos(1, 3), Pos(1, 3)), List(Up, Right, Down)).toSet === Set(
        (Block(Pos(2, 3), Pos(3, 3)), List(Down, Up, Right, Down))
      ))
    }
  }

  test("newNeighborsOnly + neighborsWithHistory combination level 0") {
    new Level0 {
      assert(newNeighborsOnly(
        neighborsWithHistory(startBlock, Nil),
        Set()
      ).toSet === Set(
        (Block(Pos(2, 2), Pos(3, 2)), List(Down))
      ))
      assert(newNeighborsOnly(
        neighborsWithHistory(Block(Pos(2, 2), Pos(3, 2)), List(Down)),
        Set(startBlock)
      ).toSet === Set(
        (Block(Pos(2, 3), Pos(3, 3)), List(Right, Down))
      ))
      assert(newNeighborsOnly(
        neighborsWithHistory(Block(Pos(2, 3), Pos(3, 3)), List(Right, Down)),
        Set(startBlock, Block(Pos(2, 2), Pos(3, 2)))
      ).toSet === Set(
        (Block(Pos(1, 3), Pos(1, 3)), List(Up, Right, Down))
      ))
      assert(newNeighborsOnly(
        neighborsWithHistory(Block(Pos(1, 3), Pos(1, 3)), List(Up, Right, Down)),
        Set(startBlock, Block(Pos(2, 2), Pos(3, 2)), Block(Pos(2, 3), Pos(3, 3)))
      ).toSet === Set())
    }
  }

  test("optimal solution for level 0") {
    new Level0 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 0") {
    new Level0 {
      assert(solution.length == optsolution.length)
    }
  }


  trait Level1 extends SolutionChecker {
    /* terrain for level 1*/

    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0, 0)), "0,0")
      assert(terrain(Pos(1, 1)), "1,1") // start
      assert(terrain(Pos(4, 7)), "4,7") // goal
      assert(terrain(Pos(5, 8)), "5,8")
      assert(!terrain(Pos(5, 9)), "5,9")
      assert(terrain(Pos(4, 9)), "4,9")
      assert(!terrain(Pos(6, 8)), "6,8")
      assert(!terrain(Pos(4, 11)), "4,11")
      assert(!terrain(Pos(-1, 0)), "-1,0")
      assert(!terrain(Pos(0, -1)), "0,-1")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1, 1))
      assert(goal == Pos(4, 7))
    }
  }

  test("block standing level 1") {
    new Level1 {
      assert(Block(Pos(1, 1), Pos(1, 1)).isStanding, "1,1 + 1,1")
      assert(!Block(Pos(1, 1), Pos(1, 2)).isStanding, "1,1 + 1,2")
      assert(!Block(Pos(0, -1), Pos(0, 0)).isStanding, "0,-1 + 0,0")
    }
  }

  test("block legal level 1") {
    new Level1 {
      assert(Block(Pos(0, 0), Pos(0, 0)).isLegal, "0,0 + 0,0")
      assert(!Block(Pos(-1, -1), Pos(-1, -1)).isLegal, "-1,-1 + -1,-1")
      assert(!Block(Pos(0, -1), Pos(0, 0)).isLegal, "0,-1 + 0,0")
    }
  }

  test("neighborsWithHistory level 1") {
    new Level1 {
      assert(neighborsWithHistory(Block(Pos(1, 1), Pos(1, 1)), List(Left, Up)).toSet === Set(
        (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))
      ))
    }
  }

  test("newNeighborsOnly level 1") {
    new Level1 {
      assert(newNeighborsOnly(
        Set(
          (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
          (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))
        ).toStream,
        Set(Block(Pos(1, 2), Pos(1, 3)), Block(Pos(1, 1), Pos(1, 1)))
      ).toSet === Set(
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))
      ))
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }


  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }


}
