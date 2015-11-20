package scalera.moonrover.interpreter

import scala.util.Random

import scalera.moonrover.interpreter.Program.LineId

case class Program[S](
                       commands: Map[LineId, Command[S]],
                       id: Program.Id = Program.someId) {

  require(
    commands.nonEmpty,
    "Empty program! Define some commands.")

  require(
    !commands.keys.exists(_ < 0),
    "Command lines with negative identifier? Seriously?")
}

object Program {

  type Id = String

  type LineId = Int

  private[moonrover] def someId: Id =
    Random.nextString(10)

  type CommandDefinition[S] = Program.Id => Command[S]

  /**
   * Builder from a bunch of line and command-like pairs.
   * @param commands Bunch of commands definitions.
   *                 When the program id is chosen,
   *                 it will fulfill command definitions to
   *                 get actual commands.
   * @tparam S State type.
   * @return
   */
  def apply[S](commands: (Program.LineId, CommandDefinition[S])*): Program[S] = {
    val id = Program.someId
    Program(
      commands.map {
        case (line, commandLike) => line -> commandLike(id)
      }.toMap)
  }

}



