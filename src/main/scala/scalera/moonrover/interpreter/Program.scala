package scalera.moonrover.interpreter

import scalera.moonrover.interpreter.Program.{Index, LineId}

case class Program[S](commands: Map[LineId, Command[S]]) {

  require(
    commands.nonEmpty,
    "Empty program! Define some commands.")

  require(
    !commands.keys.exists(_ < 0),
    "Command lines with negative identifier? Seriously?")

  val lines: Seq[(LineId, Index)] = commands.toSeq.sortWith {
    case ((thisLine, _), (thatLine, _)) => thisLine < thatLine
  }.map(_._1).zipWithIndex.map(_.swap)

}

object Program {

  type LineId = Int

  type Index = Int

}



