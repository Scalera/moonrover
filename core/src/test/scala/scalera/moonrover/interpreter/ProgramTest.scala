package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest
import scalera.moonrover.CommandSet._
import scalera.moonrover.RoverProgram._
import scalera.moonrover.domain._

class ProgramTest extends BaseTest("Program") {

  it should "fail when including negative line ids" in {
    a[Exception] should be thrownBy {
      Program(Map(-1 -> None.orNull))
    }
  }

  it should "fail when providing no commands" in {
    a[Exception] should be thrownBy {
      Program()("my-rover")
    }
  }

  it should "fail when adding GoTo's without existing lines" in {
    a[Exception] should be thrownBy {
      Program(1 -> GOTO(3))("my-rover")
    }
  }

  it should "be able to determine next line to execute" in {
    val r1 = "rover-1"
    val program = Program(
      1 -> NOP,
      2 -> RIGHT,
      3 -> LEFT,
      4 -> `IF FOUND PARACHUTE`(LEFT),
      5 -> RIGHT,
      6 -> `IF FOUND PARACHUTE`(GOTO(8)),
      7 -> GOTO(1),
      8 -> NOP
    )(r1)
    val s0 = Moon.withLanding(rover1 = r1,distanceBetweenRovers = 2)
    program.entryPoint shouldEqual 1

    val s1 = Nop(r1).perform(s0)
    program.nextLine()(s1) shouldEqual Some(2)

    val s2 = Move(r1,Right).perform(s1)
    program.nextLine(2)(s2) shouldEqual Some(3)

    val s3 = Move(r1,Left).perform(s2)
    program.nextLine(3)(s3) shouldEqual Some(4)

    val s4 = IfParachuteFound(r1,Move(r1,Left)).perform(s3)
    program.nextLine(4)(s4) shouldEqual Some(5)

    val s5 = Move(r1,Right).perform(s4)
    program.nextLine(5)(s5) shouldEqual Some(6)

    val s6 = IfParachuteFound(r1,GoTo(8)).perform(s5)
    program.nextLine(6)(s6) shouldEqual Some(8)
  }
}
