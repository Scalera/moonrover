package scalera.moonrover.interpreter

import scala.util.{Try, Random}

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

  private[interpreter] val sortedLineIds: Seq[LineId] =
    commands.toSeq.map(_._1).sortWith(_ < _)

  /**
    * Converts current command map into a stream of command.
    * @param current Current executing line.
    * @return A stream of commands.
    */
  def toStream(current: Option[LineId] = None): Stream[Command[S]] = {
    val (line, command) = current.fold(
      sortedLineIds.head, commands(sortedLineIds.head))(
      currentLine => (currentLine, commands(currentLine)))
    val nextIndex = command match {
      case GoTo(jumpLine) =>
        sortedLineIds.indexOf(jumpLine)
      case _ =>
        sortedLineIds.indexOf(line) + 1
    }
    command #:: (
      if (nextIndex == sortedLineIds.size) Stream.empty
      else toStream(Option(sortedLineIds(nextIndex))))
  }.filter{
    case GoTo(_) => false
    case _ => true
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



