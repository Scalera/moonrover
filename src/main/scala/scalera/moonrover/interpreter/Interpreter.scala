package scalera.moonrover.interpreter

import scala.annotation.tailrec

import scalera.moonrover.interpreter.Program.LineId

/**
 * An [[Interpreter]] works as a Turing Machine
 * that evaluates some command list, changing
 * its 'memory' state.
 * @param state Internal interpreter's state.
 * @tparam S State type.
 */
case class Interpreter[S](
                           state: State[S],
                           program: Program[S],
                           currentProgramLine: Option[LineId]) {

  /**
   * Evaluates next command,
   * returning a new immutable [[Interpreter]].
   * @return
   */
  def eval: Interpreter[S] = currentProgramLine.map { line =>
    val newState = program.commands(line).perform(state)
    Interpreter(newState, program, program.nextLine(line)(newState))
  }.getOrElse(this)

  /**
   * Evaluates all pending commands,
   * @return A new immutable [[Interpreter]].
   */
  @tailrec
  final def fullEval: Interpreter[S] = currentProgramLine match {
    case None => this
    case _ => this.eval.fullEval
  }
}

object Interpreter {

  def apply[S](state: State[S], program: Program[S]): Interpreter[S] =
    new Interpreter(state, program, program.nextLine()(state))
}
