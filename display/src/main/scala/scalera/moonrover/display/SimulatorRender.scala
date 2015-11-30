package scalera.moonrover.display

import org.scalajs.dom.CanvasRenderingContext2D
import scalera.moonrover.Simulator

object SimulatorRender {

  def render(
              simulator: Simulator)(
              implicit ctx: CanvasRenderingContext2D): Simulator = {
    //  Clear canvas, ...
    clear()
    //  ...render simulator ...
    renderSimulator(simulator)
    //  ...and return new simulator state
    simulator.tick
  }

  def renderSimulator(
                       simulator: Simulator)(
    implicit ctx: CanvasRenderingContext2D): Unit = {
    //TODO renderSimulator
    ctx.fillText(
      simulator.toString,
      100,
      100)
  }

  /**
   * Clear the canvas.
   * @param ctx Implicit canvas
   * @return
   */
  def clear()(implicit ctx: CanvasRenderingContext2D): Unit = {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height)
  }
}
