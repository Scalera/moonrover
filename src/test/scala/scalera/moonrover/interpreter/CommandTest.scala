package scalera.moonrover.interpreter

import org.scalatest.{FlatSpec, Matchers}

class CommandTest extends FlatSpec with Matchers {

  behavior of "Command"

  it should "be able to perform changes over some state" in {
    val myCommand = new Command[Int]{
      override def perform(state: State[Int]): State[Int] =
        state.update(state.value + 1)
    }
    myCommand.perform(State(1)) shouldEqual State(2)
  }

}
