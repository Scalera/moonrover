package scalera.moonrover

import scalera.moonrover.Simulator.MaxTicks
import scalera.moonrover.domain.{Rover, Moon}
import scalera.moonrover.domain.Rover.Id
import scalera.moonrover.interpreter.Program.LineId
import scalera.moonrover.interpreter._

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
                      state: State[Moon],
                      lineRover1: Option[LineId],
                      lineRover2: Option[LineId]) {
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
    if (currentTick > maxTicks)
      (this, None)
    else if (state.value.areRoversTogether)
      (this, Option(maxTicks - currentTick))
    else
      tick.run(currentTick + 1)
  }

  /**
   * Evaluates a new command in the given program
   * @return A new modified but immutable [[Simulator]]
   */
  def tick: Simulator = {
    val int1 = Interpreter(state, program(roverId1), lineRover1).eval
    val int2 = Interpreter(int1.state, program(roverId2), lineRover2).eval
    thisSimulator.copy(
      state = int2.state,
      lineRover1 = int1.currentProgramLine,
      lineRover2 = int2.currentProgramLine)
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
    val roverId1 = Rover.someRoverId
    val roverId2 = Rover.someRoverId
    Simulator(
      program,
      roverId1,
      roverId2,
      Moon.withLanding(rover1 = roverId1, rover2 = roverId2),
      Option(program(roverId1).entryPoint),
      Option(program(roverId2).entryPoint))
  }
}