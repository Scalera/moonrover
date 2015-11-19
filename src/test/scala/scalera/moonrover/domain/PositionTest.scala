package scalera.moonrover.domain

import org.scalatest.{FlatSpec, Matchers}

class PositionTest extends FlatSpec with Matchers{

  behavior of "Position"

  it should "compare properly different positions" in {

    Undefined shouldEqual Undefined
    Undefined.right should not equal Undefined
    Undefined.right shouldEqual Undefined.right
    Undefined.left shouldEqual Undefined.left
    Undefined.left.right shouldEqual Undefined.left.right
    Undefined.right.left shouldEqual Undefined.left.right
    Undefined.left.left.right.right shouldEqual Undefined.left.right.left.right

  }

  it should "generate a different position for 'somewhereElse'" in {
    val position1 = Undefined
    val position2 = Position.somewhereElse(position1)
    position2 should not be equal(position1)
    val position3 = Position.somewhereElse(position2)
    position3 should not be equal(position2)
    position3 should not be equal(position1)
  }

}
