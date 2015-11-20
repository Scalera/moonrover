package scalera.moonrover

import scalera.moonrover.CommandSet._
import scalera.moonrover.domain.{Moon, Left, Right}
import scalera.moonrover.interpreter.Program.CommandDefinition
import scalera.moonrover.interpreter.Program

/**
 * DSL for program definition.
 */
object RoverProgram {

  val NOP: CommandDefinition[Moon] =
    Nop

  val LEFT: CommandDefinition[Moon] =
    Move(_, Left)

  val RIGHT: CommandDefinition[Moon] =
    Move(_, Right)

  def GOTO(line: Program.LineId): CommandDefinition[Moon] =
    GoTo(_, line)

  def IFFOUNDPARACHUTE(cd: CommandDefinition[Moon]): CommandDefinition[Moon] =
    id => IfParachuteFound(id, cd(id))

  /*
  Program(
    1 -> NOP,
    2 -> LEFT,
    3 -> GOTO(1),
    4 -> IFFOUNDPARACHUTE(GOTO(2)),
    5 -> RIGHT)
    */
}
