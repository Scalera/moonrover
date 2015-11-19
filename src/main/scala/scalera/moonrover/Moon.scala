package scalera.moonrover

/**
  * The Moon itself...and our [[Rover]]'s context
  */
case class Moon(rovers: Map[Rover.Id,Rover]) {

  require(
    rovers.size == 2,
    "Not enough money for sending more than two rovers to the Moon")

  /**
    * Determines whether the specified rover has found a parachute
    * in the ground. It could be its, or the other rover's.
    * @param rover Given rover id.
    * @return
    */
  def foundParachute(rover: Rover.Id): Boolean = {
    val (currentRover,theOtherOne) =
      Option(rovers.partition{case (id,_) => rover == id}).map{
        case (rovers1,rovers2) => (rovers1.values.head,rovers2.values.head)
      }.get
    currentRover.position.equals(theOtherOne.landingPosition) ||
      currentRover.position.equals(currentRover.landingPosition)
  }

}

object Moon {

  def apply(rovers: Rover*): Moon =
    Moon(rovers.map(rover => rover.identifier -> rover).toMap)
}
