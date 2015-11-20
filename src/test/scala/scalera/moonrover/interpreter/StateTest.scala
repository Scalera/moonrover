package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest

class StateTest extends BaseTest("State") {

  it should "update its value and the last change timestamp" in {
    val s1 = State(1)
    Thread.sleep(50)
    val s2 = s1.update(2)
    s2.value shouldEqual 2
    s2.value should not equal s1.value
    s2.lastChangeTimestamp > s1.lastChangeTimestamp shouldEqual true
  }

  it should "apply its DSL to implicitly transform any value to state" in {
    val s1: State[Int] = 1
    s1.value shouldEqual 1
  }

}
