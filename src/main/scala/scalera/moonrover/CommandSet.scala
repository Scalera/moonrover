package scalera.moonrover

import scalera.moonrover.domain._
import scalera.moonrover.interpreter._

object CommandSet {

  /**
   * Move some rover to some direction
   * @param rover Given rover id.
   * @param movement Direction.
   */
  case class Move(rover: Rover.Id, movement: Movement) extends Command[Moon] {

    override def perform(state: State[Moon]): State[Moon] = {
      state.value.updateRoverLocation(rover, movement)
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
   * Conditional executing of some
   * given command.
   * @param rover Given rover id.
   * @param conditionalCommand Command that will be performed
   *                           in case of affirmative condition.
   */
  class IfParachuteFound(
                          rover: Rover.Id,
                          conditionalCommand: Command[Moon]) extends ConditionalCommand[Moon](
    _.value.foundParachute(rover), conditionalCommand)

  object IfParachuteFound {

    def apply(rover: Rover.Id,
              conditionalCommand: Command[Moon]): IfParachuteFound =
      new IfParachuteFound(rover, conditionalCommand)
  }

}
