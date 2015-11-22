package scalera.moonrover

import scalera.moonrover.CommandSet._
import scalera.moonrover.RoverProgram._
import scalera.moonrover.interpreter.{CommandSeq, Program}
import scalera.moonrover.domain._

class SimulatorTest extends BaseTest("Simulator") {

  it should "generate the correct command stream for both rovers" in {
    val sim = new Simulator(
      Program(
        1 -> NOP,
        2 -> LEFT,
        3 -> `IF FOUND PARACHUTE`(RIGHT),
        4 -> RIGHT))

    sim.interpreter.commandsLeft.toList shouldEqual List(
      CommandSeq(List(
        Nop(sim.roverId1),
        Nop(sim.roverId2))),
      CommandSeq(List(
        Move(sim.roverId1, Left),
        Move(sim.roverId2, Left))),
      CommandSeq(List(
        IfParachuteFound(sim.roverId1, Move(sim.roverId1, Right)),
        IfParachuteFound(sim.roverId2, Move(sim.roverId2, Right)))),
      CommandSeq(List(
        Move(sim.roverId1, Right),
        Move(sim.roverId2, Right))))

  }

  ignore should "run the simulation and find out if the program is useful" in {
    val sim = new Simulator(
      Program(
        1 -> NOP,
        2 -> LEFT,
        3 -> GOTO(1)))
    val (offSim,result) = sim.run
    result shouldEqual None
  }

}
