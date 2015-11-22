package scalera.moonrover.domain

import scalera.moonrover.BaseTest

class MoonTest extends BaseTest("Moon") {

  it should "only admit two rovers on its surface" in {
    a[Exception] should be thrownBy {
      Moon(Map[Rover.Id, Rover]())
    }
    a[Exception] should be thrownBy {
      Moon(Map[Rover.Id, Rover]("r1" -> Rover()))
    }
    a[Exception] should be thrownBy {
      Moon(Map[Rover.Id, Rover]("r1" -> Rover(), "r2" -> Rover(), "r3" -> Rover()))
    }
  }

  it should "determine if a rover has found a parachute (itself's or the other one)" in {
    val (r1, r2) = ("r1", "r2")
    val moon = Moon.withLanding(r1, r2, distanceBetweenRovers = 2)
    moon.foundParachute(r1) shouldEqual true
    moon.updateRoverLocation(r1, Right).foundParachute(r1) shouldEqual false
    moon.updateRoverLocation(r1, Right).updateRoverLocation(r1, Right).foundParachute(r1) shouldEqual true
  }

  it should "update some rover's location over its surface" in {
    val (r1, r2) = ("r1", "r2")
    val moon = Moon.withLanding(r1, r2, distanceBetweenRovers = 2)
    moon
      .updateRoverLocation(r1, Left)
      .rovers(r1).position shouldEqual moon.rovers(r1).position.left
  }

  it should "generate a landing scenario with two rovers at some distance between them" in {
    val (r1, r2) = ("r1", "r2")
    val moon = Moon.withLanding(r1, r2, distanceBetweenRovers = 2)
    moon.rovers("r1").position shouldEqual Undefined
    moon.rovers("r1").landingPosition shouldEqual Undefined
    moon.rovers("r2").position shouldEqual Undefined.right.right
    moon.rovers("r2").landingPosition shouldEqual Undefined.right.right
  }

  it should "determine if rovers are already together or not" in {
    val (r1, r2) = ("r1", "r2")
    val moon = Moon.withLanding(r1, r2, distanceBetweenRovers = 2)
    moon.areRoversTogether shouldEqual false
    val newMoon = moon
      .updateRoverLocation(r1, Right)
      .updateRoverLocation(r1, Right)
    newMoon.areRoversTogether shouldEqual true
  }

}
