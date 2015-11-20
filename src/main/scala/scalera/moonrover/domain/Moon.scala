package scalera.moonrover.domain

import scala.util.Random

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

  /**
   * Updates some [[Rover]] position over the surface of this moon
   * @param rover Rover identifier
   * @param movement Where did it move?
   * @return
   */
  def updateRoverLocation(rover: Rover.Id, movement: Movement): Moon = {
    rovers.get(rover).fold(this)(roverState =>
      Moon(rovers + (rover -> roverState.move(movement))))
  }

}

object Moon {

  def apply(rovers: Rover*): Moon =
    Moon(rovers.map(rover => rover.identifier -> rover).toMap)

  private val WhoCares = 10

  /** Default distance between rovers when landing*/
  val DistanceBetweenRovers = WhoCares

  /**
   * Populate a desert [[Moon]] with
   * a couple of kind rovers.
   * @param rover1 Rover 1 id.
   * @param rover2 Rover 2 id.
   * @return
   */
  def withLanding(
                   rover1: Rover.Id=Random.nextString(Rover.IdLength),
                   rover2: Rover.Id=Random.nextString(Rover.IdLength),
                   distanceBetweenRovers: Int = DistanceBetweenRovers): Moon = {
    val r1 = Rover(identifier = rover1)
    val r2Position = (r1 /: (1 to distanceBetweenRovers))(
      (rover,_) => rover.move(Right)).position
    val r2 = Rover(
      identifier = rover2,
      position = r2Position,
      landingPosition = r2Position)
    Moon(r1,r2)
  }

}
