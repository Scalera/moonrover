package scalera.moonrover.domain

import scala.util.Random

/**
 * A concrete place in a infinite discrete line
 */
sealed trait Position {

  val movements: List[Movement] = Nil

  private def move(to: Movement): Position = {
    val removeMov = if (to == Left) Right else Left
    this withMovements (movements.span(_ != removeMov) match {
      case (withoutMov, Nil) => to +: movements
      case (withoutMov, _ :: withMov) => withoutMov ::: withMov
    })
  }

  /**
   * Calculate postition just one discrete step to the right
   * @return
   */
  def right: Position = move(Right)

  /**
   * Calculate postition just one discrete step to the left
   * @return
   */
  def left: Position = move(Left)

  /**
   * Create a brand new immutable [[Position]]
   * with a given bunch of movements.
   * @param newMovements Given new movements.
   * @return
   */
  def withMovements(newMovements: List[Movement]): Position =
    new Position {
      override val movements = newMovements
    }

  /**
   * Relative position to initial one.
   * @return
   */
  def relative = movements.map(_.value).sum

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Position => this.relative == that.relative
      case _ => false
    }
}

object Position {

  /**
   * Generates a new position to the right
   * different from the given one.
   * @param position Given reference position
   * @return A new position.
   */
  def somewhereElse(position: Position): Position = {
    (position /: (1 to Random.nextInt(10) + 2))(
      (pos, _) => pos.right)
  }
}

/**
 * Somewhere over the moon
 */
case object Undefined extends Position
