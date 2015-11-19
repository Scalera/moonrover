package scalera.moonrover

import scala.util.Random

/**
  * A [[Rover]] is a nice vehicle for
  * exploring the Moon surface.
  * @param position Current relative position to landing's.
  * @param landingPosition Landing place. Its always [[Undefined]],
  *                        but everybody nows not all 'Undefined' are
  *                        the same (so do 'Infinite's).
  * @param identifier The identifier of current rover.
  */
case class Rover(
                  position: Position = Undefined,
                  landingPosition: Position = Undefined,
                  identifier: Rover.Id = Random.nextString(5)) {

  /**
    * Move the rover one step to specified direction
    * @param movement It's just a movement
    * @return
    */
  def move(movement: Movement): Rover =
    this.copy(position = if (movement == Left) position.left else position.right)

  /**
    * Determines whether the specified rover has found a parachute
    * in the ground. It could be its, or the other rover's.
    * @return
    */
  def hasFoundParachute()(implicit moon: Moon): Boolean =
    moon.foundParachute(identifier)

}

object Rover {

  type Id = String

}