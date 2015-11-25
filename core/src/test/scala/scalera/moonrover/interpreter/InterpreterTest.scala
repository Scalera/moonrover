package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest
import scalera.moonrover.domain.Moon
import scalera.moonrover.interpreter.Program.CommandDefinition

class InterpreterTest extends BaseTest("Interpreter") {

  private val add = new Command[Int] {
    def perform(state: State[Int]): State[Int] =
      state.value + 1
  }

  val addCommand: CommandDefinition[Int] = _ => add

  it should "eval a command and return a new interpreter" in {
    val interpreter = Interpreter(1, Program(
      1 -> addCommand,
      2 -> addCommand,
      3 -> addCommand)(Program.someId))
    val newInterpreter = interpreter.eval
    newInterpreter.state shouldEqual State(2)
    newInterpreter.currentProgramLine shouldEqual Some(2)
  }

  it should "make full evaluation of pending commands" in {
    val interpreter = Interpreter(1, Program(
      1 -> addCommand,
      2 -> addCommand,
      3 -> addCommand)(Program.someId)).fullEval
    interpreter.state shouldEqual State(4)
    interpreter.currentProgramLine.isEmpty shouldEqual true
  }
}
