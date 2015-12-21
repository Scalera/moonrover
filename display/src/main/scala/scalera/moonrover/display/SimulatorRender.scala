package scalera.moonrover.display

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import scalatags.JsDom.all._
import org.scalajs.dom.raw.{HTMLImageElement, HTMLElement, ImageData}
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

    //Background

    val imageSelector: Int = (
      simulator.state.value.foundParachute(simulator.roverId1),
      simulator.state.value.foundParachute(simulator.roverId2)) match {
      case (true,true) => 1
      case (false,false) => 2
      case (false,true) => 3
      case (true,false) => 4
    }
    val image = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    image.src = s"canvas-0$imageSelector.png"
    ctx.drawImage(image,0,0)/*
    ctx.fillText(
      "scalera.es",//simulator.toString,
      718,
      575)*/
  }

  /**
   * Clear the canvas.
   * @param ctx Implicit canvas
   * @return
   */
  def clear()(implicit ctx: CanvasRenderingContext2D): Unit = {
    //ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height)
  }

}
