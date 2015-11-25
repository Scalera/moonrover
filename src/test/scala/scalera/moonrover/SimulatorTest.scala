package scalera.moonrover

import scalera.moonrover.RoverProgram._
import scalera.moonrover.interpreter.Program

class SimulatorTest extends BaseTest("Simulator") {

  it should "run the simulation and find out if the program is useless" in {
    val sim = Simulator(
      Program(
        1 -> NOP,
        2 -> LEFT,
        3 -> GOTO(1)))
    val (offSim,result) = sim.run
    result shouldEqual None
  }

}
