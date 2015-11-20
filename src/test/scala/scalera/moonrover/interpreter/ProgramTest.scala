package scalera.moonrover.interpreter

import scalera.moonrover.BaseTest

class ProgramTest extends BaseTest("Program") {

  it should "fail when including negative line ids" in {
    a [Exception] should be thrownBy {
      Program(Map(-1 -> None.orNull))
    }
  }

  it should "fail when providing no commands" in {
    a [Exception] should be thrownBy {
      Program()
    }
  }

}
