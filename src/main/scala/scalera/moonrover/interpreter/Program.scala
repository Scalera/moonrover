package scalera.moonrover.interpreter

import scalera.moonrover.interpreter.Program.LineId

case class Program[S](commands: Map[LineId,Command[S]]) {

  require(
    commands.size > 0,
    "Empty program! Define some commands.")

  require(
    !commands.keys.exists(_ < 0),
    "Command lines with negative identifier? Seriously?")

}

object Program {

  type LineId = Int

}



