package scalera.moonrover.interpreter

import scala.util.Random

import scalera.moonrover.interpreter.Program.LineId

case class Program[S](
                       commands: Map[LineId, Command[S]],
                       id: Program.Id = Program.someId) {

  type CommandPair = (LineId, Command[S])

  require(
    commands.nonEmpty,
    "Empty program! Define some commands.")

  require(
    !commands.keys.exists(_ < 0),
    "Command lines with negative identifier? Seriously?")

  require(!commands.exists {
    case (_, GoTo(line)) => !commands.map(_._1).toList.contains(line)
    case _ => false
  }, "GoTo's definitions point to some not existing lines!")

  private[interpreter] val sortedLineIds@((entryPoint, _) :: _): Seq[LineId] =
    commands.toSeq.map(_._1).sortWith(_ < _)

  def nextLine(current: LineId = entryPoint)(state: State[S]): Option[LineId] = {
    val (line, command) = (current, commands(current))
    val nextIndex: LineId = command match {
      case ConditionalCommand(condition, GoTo(jumpLine)) =>
        if (condition(state)) sortedLineIds.indexOf(jumpLine)
        else sortedLineIds.indexOf(line) + 1
      case GoTo(jumpLine) =>
        sortedLineIds.indexOf(jumpLine)
      case _ =>
        sortedLineIds.indexOf(line) + 1
    }
    if (nextIndex == sortedLineIds.size) None
    else Option(nextIndex)
  }
}

object Program {

  type Id = String

  type LineId = Int

  /**
   * The way to get some ugly identifier
   * for a program.
   * @return
   */
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
  def apply[S](commands: (Program.LineId, CommandDefinition[S])*): Id => Program[S] = { id =>
    Program(
      commands.map {
        case (line, commandDef) => line -> commandDef(id)
      }.toMap)
  }
}



