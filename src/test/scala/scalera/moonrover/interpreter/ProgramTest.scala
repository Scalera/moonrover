package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest
import scalera.moonrover.CommandSet.{Move, Nop}
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

  it should "generate a valid command stream" in {
    val id = "rover-1"
    Program(
      1 -> LEFT,
      2 -> GOTO(4),
      3 -> RIGHT,
      4 -> GOTO(1))(id).toStream().take(4).toList shouldEqual
      List(Move(id,Left), Move(id,Left), Move(id,Left), Move(id,Left))
    val program2 = Program(
      1 -> NOP,
      2 -> NOP)(id)
    program2.toStream().take(2).toList shouldEqual List(Nop(id), Nop(id))
  }

}
