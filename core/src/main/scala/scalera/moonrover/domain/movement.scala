package scalera.moonrover.domain

/**
  * A [[Movement]] represents a number of
  * steps away from an initial position.
  * @param value Number of steps.
  */
sealed abstract class Movement(val value: Int)

case object Right extends Movement(+1)

case object Left extends Movement(-1)