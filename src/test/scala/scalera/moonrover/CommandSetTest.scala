package scalera.moonrover

import scalera.moonrover.domain._
import scalera.moonrover.interpreter.{Command, GoTo, Interpreter}

class CommandSetTest extends BaseTest("CommandSet") {

  import CommandSet._

  it should "move some rover to some direction" in {
    val (r1,r2) = ("r1","r2")
    val moon = Moon.withLanding(r1,r2,distanceBetweenRovers = 2)
    Interpreter(moon, Stream(Move(r1,Right)))
      .fullEval.state.value.rovers(r1).position shouldEqual Undefined.right
  }

  it should "do nothing - you sure you wanna do this?" in {
    val (r1,r2) = ("r1","r2")
    val moon = Moon.withLanding(r1,r2)
    Interpreter(moon,Stream(Nop(r1))).fullEval.state.value shouldEqual moon
  }

  it should "ignore the effects in state of a GoTo command" in {
    val (r1,r2) = ("r1","r2")
    val moon = Moon.withLanding(r1,r2)
    Interpreter(moon,Stream[Command[Moon]](GoTo(15))).fullEval.state.value shouldEqual moon
  }

  it should "perform some given command when using an ifParachuteFound" in {
    val (r1,r2) = ("r1","r2")
    val moon = Moon.withLanding(r1,r2,distanceBetweenRovers = 2)
    val moonAfterCond = IfParachuteFound(r1,Move(r1,Left)).perform(moon)
    moonAfterCond.value.rovers(r1).position should not be equal(moon.rovers(r1).position)
    val moonAfterSecondCond = IfParachuteFound(r1,Move(r1,Left)).perform(moonAfterCond)
    moonAfterSecondCond.value.rovers(r1).position shouldEqual moonAfterCond.value.rovers(r1).position
  }

}
