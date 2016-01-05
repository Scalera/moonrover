package scalera.moonrover

import org.scalatest.{Matchers, FlatSpec}

/**
 * Base class for defining the whole set of tests.
 * @param component The component or class to be tested.
 */
class BaseTest(component: String) extends FlatSpec with Matchers {

  behavior of component

}
