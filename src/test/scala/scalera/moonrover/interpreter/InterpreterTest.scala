package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest
import scalera.moonrover.domain.Moon

class InterpreterTest extends BaseTest("Interpreter") {

  private val add = new Command[Int] {
    def perform(state: State[Int]): State[Int] =
      state.value + 1
  }

  it should "eval a command and return a new interpreter" in {
    val addCommand: State[Int] => Command[Int] = _ => add
    val interpreter = Interpreter(1, Stream(addCommand,addCommand,addCommand))
    val newInterpreter = interpreter.eval
    newInterpreter.state shouldEqual State(2)
    newInterpreter.commandsLeft shouldEqual
      Stream(addCommand, addCommand)
  }

  it should "make full evaluation of pending commands" in {
    val commands: Stream[State[Int] => Command[Int]] =
      Stream(_ => add, _ => add, _ => add)
    val interpreter = Interpreter(1, commands).fullEval
    interpreter.state shouldEqual State(4)
    interpreter.commandsLeft.isEmpty shouldEqual true
  }
}
