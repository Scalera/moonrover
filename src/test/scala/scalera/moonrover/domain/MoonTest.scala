package scalera.moonrover.domain

import org.scalatest.{FlatSpec, Matchers}

class MoonTest extends FlatSpec with Matchers {

  behavior of "Moon"

  it should "only admit two rovers on its surface" in {
    a [Exception] should be thrownBy{
      Moon(Map[Rover.Id,Rover]())
    }
    a [Exception] should be thrownBy{
      Moon(Map[Rover.Id,Rover]("r1" -> Rover()))
    }
    a [Exception] should be thrownBy{
      Moon(Map[Rover.Id,Rover]("r1" -> Rover(), "r2" -> Rover(),"r3" -> Rover()))
    }
  }

  it should "determine if a rover has found a parachute (itself's or the other one)" in {
    val rover1 = Rover()
    val rover2 = Rover(landingPosition = rover1.position.right.right)
    Moon(rover1, rover2).foundParachute(rover1.identifier) shouldEqual true
    Moon(rover1.move(Right), rover2).foundParachute(rover1.identifier) shouldEqual false
    Moon(rover1.move(Right).move(Right), rover2).foundParachute(rover1.identifier) shouldEqual true
  }

}
