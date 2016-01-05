package scalera.moonrover.interpreter

import scala.language.implicitConversions

/**
 * A [[State]] represents the snapshot of the
 * observed reality in a concrete moment.
 * @param value State value
 * @tparam S Parameter type.
 */
case class State[S](value: S) {

  val lastChangeTimestamp: Long = System.currentTimeMillis()

  def update(s: S): State[S] = State(s)

}

object State {

  //  DSL helpers

  implicit def toState[T](t: T): State[T] = State(t)

}
