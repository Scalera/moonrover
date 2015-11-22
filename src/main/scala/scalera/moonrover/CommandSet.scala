package scalera.moonrover

import scalera.moonrover.domain._
import scalera.moonrover.interpreter.Program.LineId
import scalera.moonrover.interpreter.{Command, State}

object CommandSet {

  /**
   * Move some rover to some direction
   * @param rover Given rover id.
   * @param movement Direction.
   */
  case class Move(rover: Rover.Id, movement: Movement) extends Command[Moon] {
    override def perform(state: State[Moon]): State[Moon] = {
      state.value.updateRoverLocation(rover,movement)
    }
  }

  /**
   * Just do nothing.
   * Even doing nothing takes a while...
   * @param rover Given rover id.
   */
  case class Nop(rover: Rover.Id) extends Command[Moon] {
    override def perform(state: State[Moon]): State[Moon] = state
  }

  /**
   * Change program sequence
   * and jump onto given line
   * @param rover Given rover id.
   * @param line Line to jump.
   */
  case class GoTo(rover: Rover.Id, line: LineId) extends Command[Moon] {
    //TODO Implement perform in GoTo command
    override def perform(state: State[Moon]): State[Moon] = ???
  }

  /**
   * Conditional executing of some
   * given command.
   * @param rover Given rover id.
   * @param command Command that will be performed
   *                in case of affirmative condition.
   */
  case class IfParachuteFound(
                               rover: Rover.Id,
                               command: Command[Moon]) extends Command[Moon] {
    override def perform(state: State[Moon]): State[Moon] =
      if (state.value.foundParachute(rover)) command.perform(state) else state
  }



}
