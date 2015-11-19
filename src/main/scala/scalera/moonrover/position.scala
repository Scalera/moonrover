package scalera.moonrover

import scala.util.Random

/**
  * A concrete place in a infinite discrete line
  */
sealed trait Position {

  val movements: List[Movement] = Nil

  private def move(removeMov: Movement): Position = {
    val opposite = if (removeMov==Left) Right else Left
    this withMovements (movements.span(_ != removeMov) match {
      case (withoutMov, Nil) => opposite +: movements
      case (withoutMov, _ :: withMov) => withoutMov ::: withMov
    })
  }

  /**
    * Calculate postition just one discrete step to the right
    * @return
    */
  def right: Position = move(removeMov = Left)

  /**
    * Calculate postition just one discrete step to the left
    * @return
    */
  def left: Position = move(removeMov = Right)

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
    * Generates a new position different from the given one.
    * @param position Given reference position
    * @return A new position.
    */
  def somewhereElse(position: Position): Position = {
    val direction = Random.nextBoolean()
    val movementPolicy: Position => Position = pos =>
      if (direction) pos.right
      else pos.left
    (position /: (1 to Random.nextInt(10) + 2))(
      (pos,_) => movementPolicy(pos))
  }

}

/**
  * Somewhere over the moon
  */
case object Undefined extends Position
