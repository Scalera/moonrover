package scalera.moonrover.domain

import org.scalatest.{FlatSpec, Matchers}

class RoverTest extends FlatSpec with Matchers {

  behavior of "Rover"

  it should "move correctly without changing other immutable attributes" in {
    val rover = Rover()
    rover.position shouldEqual Undefined
    rover.move(Left).position shouldEqual Undefined.left
    rover.move(Left).landingPosition shouldEqual rover.landingPosition
    rover.move(Left).identifier shouldEqual rover.identifier
  }

  it should "determine if a rover has found a parachute (itself's or the other one)" in {
    val rover1 = Rover()
    val rover2 = Rover(landingPosition = rover1.position.right.right)
    rover1.hasFoundParachute()(Moon(rover1,rover2)) shouldEqual true
    rover1.move(Right)
      .hasFoundParachute()(Moon(rover1.move(Right),rover2)) shouldEqual false
    rover1.move(Right).move(Right)
      .hasFoundParachute()(Moon(rover1.move(Right).move(Right),rover2)) shouldEqual true
  }

}
