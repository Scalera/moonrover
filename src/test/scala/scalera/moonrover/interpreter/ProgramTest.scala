package scalera.moonrover.interpreter

import org.scalatest.{FlatSpec, Matchers}

class ProgramTest extends FlatSpec with Matchers{

  behavior of "Program"

  it should "fail when including negative line ids" in {
    a [Exception] should be thrownBy {
      Program(Map(-1 -> None.orNull))
    }
  }

  it should "fail when providing no commands" in {
    a [Exception] should be thrownBy {
      Program(Map())
    }
  }

}
