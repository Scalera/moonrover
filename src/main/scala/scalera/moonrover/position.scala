package scalera.moonrover

/**
  * A concrete place in a infinite discrete line
  */
sealed trait Position {

  def +(numPositions: Int): Position = {
    if (numPositions < 0) this - (numPositions * -1)
    else if (numPositions == 0) this
    else this match {
      case Left(pos) => pos + (numPositions - 1)
      case _ => Right(this) + (numPositions - 1)
    }
  }

  def -(numPositions: Int): Position =
    if (numPositions < 0) this + (numPositions * -1)
    else if (numPositions == 0) this
    else this match {
      case Right(pos) => pos - (numPositions - 1)
      case _ => Left(this) - (numPositions - 1)
    }

  override def equals(obj: scala.Any): Boolean =
    (this,obj) match {
      case (Left(thisPos),Left(thatPos)) => thisPos equals thatPos
      case (Right(thisPos),)
      case _ => false
    }
}

case class Left(position: Position) extends Position

case class Right(position: Position) extends Position

/**
  * Somewhere over the moon
  */
case object Undefined extends Position {

}
