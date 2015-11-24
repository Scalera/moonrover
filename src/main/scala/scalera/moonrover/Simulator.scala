package scalera.moonrover

import scalera.moonrover.Simulator.MaxTicks
import scalera.moonrover.domain.{Rover, Moon}
import scalera.moonrover.domain.Rover.Id
import scalera.moonrover.interpreter.{CommandSeq, Interpreter, Program}

import scala.annotation.tailrec

/**
  * A landing simulator. It's able to
  * guess if, given a program, two moon
  * rovers are capable to find each other
  * in the moon surface
  * @param program Given rover program.
  */
case class Simulator(
                      program: Id => Program[Moon],
                      roverId1: Rover.Id,
                      roverId2: Rover.Id,
                      interpreter: Interpreter[Moon]) {
  thisSimulator =>

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
  @tailrec
  private[moonrover] final def run(
                                    currentTick: Int = 0,
                                    maxTicks: Int = MaxTicks): (Simulator, Option[Int]) = {
    if (currentTick > maxTicks) (this, None)
    else {
      if (interpreter.state.value.areRoversTogether)
        (this, Option(maxTicks - currentTick))
      else
        thisSimulator.copy(interpreter = thisSimulator.interpreter.eval)
          .run(currentTick + 1)
    }
  }

  /**
    * Simulates the rover landing with given
    * program.
    * @return Returns, if possible, how
    *         many clock ticks took both rovers to find
    *         each other.
    */
  def run: (Simulator, Option[Int]) =
    run(0, MaxTicks)

}

object Simulator {

  /**
    * Maximum ticks to try program before
    * marking it as useless.
    */
  val MaxTicks = 200

  def apply(program: Id => Program[Moon]): Simulator = {
    val rover1 = Rover.someRoverId
    val rover2 = Rover.someRoverId
    val interpreter = initialInterpreter(program, rover1, rover2)
    Simulator(program, rover1, rover2, interpreter)
  }

  /**
    * Initialize the interpreter with
    * the identical program for both rovers
    * @param program Program definition.
    * @param roverId1 Rover 1 id.
    * @param roverId2 Rover 2 id.
    * @return
    */
  def initialInterpreter(
                          program: Id => Program[Moon],
                          roverId1: Rover.Id,
                          roverId2: Rover.Id): Interpreter[Moon] = {
    val moon = Moon.withLanding(rover1 = roverId1, rover2 = roverId2)
    Interpreter(
      moon,
      program(roverId1).toStream().zip(program(roverId2).toStream()).map {
        case (command1, command2) => CommandSeq(command1, command2)
      })
  }

}