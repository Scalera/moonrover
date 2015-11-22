package scalera.moonrover

import scalera.moonrover.Simulator.MaxTicks
import scalera.moonrover.domain.{Rover, Moon}
import scalera.moonrover.domain.Rover.Id
import scalera.moonrover.interpreter.{CommandSeq, Interpreter, Program}

/**
  * A landing simulator. It's able to
  * guess if, given a program, two moon
  * rovers are capable to find each other
  * in the moon surface
  * @param program Given rover program.
  */
class Simulator(program: Id => Program[Moon]) { thisSimulator =>

  lazy val roverId1: Rover.Id = Rover.someRoverId

  lazy val roverId2: Rover.Id = Rover.someRoverId

  /*
   * Initialize the interpreter with
   * the identical program for both rovers
   */
  lazy val interpreter = {
    val moon = Moon.withLanding(rover1 = roverId1, rover2 = roverId2)
    Interpreter(
      moon,
      program(roverId1).toStream().zip(program(roverId2).toStream()).map {
        case (command1, command2) => CommandSeq(command1, command2)
      })
  }

  /**
    * Simulates the rover landing with given
    * program.
    * @param currentTick clock pulse number.
    * @param maxTicks maximum clock ticks to declare this
    *                 program useless.
    * @return Returns, if possible, how
    *         many clock ticks took both rovers to find
    *         each other.
    */
  private[moonrover] def run(currentTick: Int = 0,maxTicks: Int = MaxTicks): (Simulator,Option[Int]) = {
    if (currentTick > maxTicks) (this,None)
    else {
      if (interpreter.state.value.areRoversTogether) (this, Option(maxTicks - currentTick))
      else new Simulator(program){
        override lazy val roverId1: Rover.Id = thisSimulator.roverId1
        override lazy val roverId2: Rover.Id = thisSimulator.roverId2
        override lazy val interpreter = thisSimulator.interpreter.eval
      }.run(currentTick + 1)
    }
  }

  /**
    * Simulates the rover landing with given
    * program.
    * @return Returns, if possible, how
    *         many clock ticks took both rovers to find
    *         each other.
    */
  def run: (Simulator,Option[Int]) =
    run(0,MaxTicks)

}

object Simulator {

  /**
    * Maximum ticks to try program before
    * marking it as useless.
    */
  val MaxTicks = 200

}