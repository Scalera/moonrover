package scalera.moonrover.interpreter

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
case class CommandSeq[S](commands: Command[S]*) extends Command[S] {

  override def perform(state: State[S]): State[S] =
    (state /: commands)((s,c) => c.perform(s))

}
