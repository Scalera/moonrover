package scalera.moonrover.interpreter

import scalera.moonrover.interpreter.Program._

/**
 * A [[Command]] represent an action over
 * some state.
 * @tparam S Type of the state to update.
 */
trait Command[S] {

  def perform(state: State[S]): State[S]

}

/**
 * For performing multiple commands...
 * @param commands Bunch of commands to be executed.
 * @tparam S Type of the state to update.
 */
case class CommandSeq[S](commands: List[Command[S]]) extends Command[S] {

  override def perform(state: State[S]): State[S] =
    (state /: commands)((s,c) => c.perform(s))

}

object CommandSeq {
  def apply[S](commands: Command[S]*): CommandSeq[S] =
    CommandSeq(commands.toList)
}

/**
  * Change program sequence
  * and jump onto given line
  * @param line Line to jump.
  */
case class GoTo[S](line: LineId) extends Command[S] {
  override def perform(state: State[S]): State[S] = state
}
