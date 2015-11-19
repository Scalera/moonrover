package scalera.moonrover

import org.scalatest.{Matchers, FlatSpec}

class MoonTest extends FlatSpec with Matchers {

  behavior of "Moon"

  it should "determine if a rover has found a parachute (itself's or the other one)" in {
    val rover1 = Rover()
    val rover2 = Rover(landingPosition = rover1.position.right.right)
    Moon(rover1, rover2).foundParachute(rover1.identifier) shouldEqual true
    Moon(rover1.move(Right), rover2).foundParachute(rover1.identifier) shouldEqual false
    Moon(rover1.move(Right).move(Right), rover2).foundParachute(rover1.identifier) shouldEqual true
  }

}
