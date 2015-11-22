package scalera.moonrover.interpreter

import scala.annotation.tailrec

/**
  * An [[Interpreter]] works as a Turing Machine
  * that evaluates some command list, changing
  * its 'memory' state.
  * @param state Internal interpreter's state.
  * @param commandsLeft Pending commands to be executed.
  * @tparam S State type.
  */
case class Interpreter[S](
                           state: State[S],
                           commandsLeft: Iterable[Command[S]]) {

  /**
    * Evaluates next command,
    * returning a new immutable [[Interpreter]].
    * @return
    */
  def eval: Interpreter[S] = commandsLeft match {
    case (nextCommand :: rest) =>
      Interpreter(nextCommand.perform(state), rest)
    case _ => this
  }

  /**
    * Evaluates all pending commands,
    * @return A new immutable [[Interpreter]].
    */
  @tailrec
  final def fullEval: Interpreter[S] = commandsLeft match {
    case Nil => this
    case _ => this.eval.fullEval
  }


}
