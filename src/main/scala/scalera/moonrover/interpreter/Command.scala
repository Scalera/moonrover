package scalera.moonrover.interpreter

/**
 * A [[Command]] represent an action over
 * some state.
 * @tparam S Type of the state to update.
 */
trait Command[S] {

  def perform(state: State[S]): State[S]

}
