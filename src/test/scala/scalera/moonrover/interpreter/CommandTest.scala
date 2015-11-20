package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest

class CommandTest extends BaseTest("Command") {

  val myCommand = new Command[Int]{
    override def perform(state: State[Int]): State[Int] =
      state.update(state.value + 1)
  }

  it should "be able to perform changes over some state" in {
    myCommand.perform(State(1)) shouldEqual State(2)
  }

  it should "be able to group commands" in {
    CommandSeq(myCommand,myCommand,myCommand)
      .perform(State(1)) shouldEqual State(4)
  }

}
