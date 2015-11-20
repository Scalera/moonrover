package scalera.moonrover

import scalera.moonrover.domain._
import scalera.moonrover.interpreter.Interpreter

class CommandSetTest extends BaseTest("CommandSet") {

  import CommandSet._

  it should "move some rover to some direction" in {
    val (r1,r2) = ("r1","r2")
    val moon = Moon.withLanding(r1,r2,distanceBetweenRovers = 2)
    Interpreter(moon, Seq(Move(r1,Right)))
      .fullEval.state.value.rovers(r1).position shouldEqual Undefined.right
  }

  it should "do nothing - you sure you wanna do this?" in {
    val (r1,r2) = ("r1","r2")
    val moon = Moon.withLanding(r1,r2)
    Interpreter(moon,Seq(Nop(r1))).fullEval.state.value shouldEqual moon
  }

  ignore should "change the command sequence when performing a GoTo" in {
    ()
  }

  ignore should "perform some given command when using an ifParachuteFound" in {
    ()
  }

}
