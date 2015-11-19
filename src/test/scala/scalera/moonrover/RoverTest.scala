package scalera.moonrover

import org.scalatest.{FlatSpec, Matchers}

class RoverTest extends FlatSpec with Matchers{

  behavior of "Rover"

  it should "move correctly without changing other immutable attributes" in {
    val rover = Rover()
    rover.position shouldEqual Undefined
    rover.move(Left).position shouldEqual Undefined.left
    rover.move(Left).landingPosition shouldEqual rover.landingPosition
    rover.move(Left).identifier shouldEqual rover.identifier
  }

}
