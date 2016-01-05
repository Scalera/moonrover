package scalera.moonrover

import scalera.moonrover.RoverProgram._
import scalera.moonrover.interpreter.Program

/**
 * Define here the program that
 * your rovers must execute
 */
object Launch {

  val program = Program(
    1 -> RIGHT,
    2 -> GOTO(1))

}
