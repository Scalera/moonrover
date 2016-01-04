package scalera.moonrover.display

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import scalatags.JsDom.all._
import org.scalajs.dom.raw.{HTMLImageElement, HTMLElement, ImageData}
import scalera.moonrover.Simulator
import scalera.moonrover.domain.Rover

object SimulatorRender {

  var charSize: Option[Int] = None

  def render(
              simulator: Simulator,
              currentTick: Int)(
              implicit ctx: CanvasRenderingContext2D): Simulator = {
    //  Clear canvas, ...
    clear()
    //  ...render simulator ...
    renderSimulator(simulator, currentTick)
    //  ...and return new simulator state
    simulator.tick
  }

  def renderSimulator(
                       simulator: Simulator,
                       currentTick: Int)(
                       implicit ctx: CanvasRenderingContext2D): Unit = {

    //  Background

    val imageSelector: Int = (
      simulator.state.value.foundParachute(simulator.roverId1),
      simulator.state.value.foundParachute(simulator.roverId2)) match {
      case (true, true) => 1
      case (false, false) => 2
      case (false, true) => 3
      case (true, false) => 4
    }
    val image = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    image.src = s"canvas-0$imageSelector.png"
    ctx.drawImage(image, 0, 0)

    //  Distance between rovers

    ctx.fillStyle = "white"
    ctx.font = "30px sans-serif"
    ctx.textAlign = "center"
    ctx.textBaseline = "middle"
    ctx.fillText(
      simulator.state.value.distanceBetweenRovers().toString,
      400,
      378)

    //  Current tick

    ctx.fillStyle = "white"
    ctx.font = "30px sans-serif"
    ctx.textAlign = "center"
    ctx.textBaseline = "middle"
    ctx.fillText(
      currentTick.toString,
      760,
      150)

    //  Current program line

    renderCurrentProgram(simulator, simulator.roverId1)
    renderCurrentProgram(simulator, simulator.roverId2)
  }

  /**
   * Render current state of the specified set
   * of commands for the rover.
   * @param simulator Simulator state.
   * @param rover Rover program id.
   * @param ctx Implicit canvas rendering context
   * @return
   */
  def renderCurrentProgram(
                            simulator: Simulator,
                            rover: Rover.Id)(
                            implicit ctx: CanvasRenderingContext2D): Unit = {

    //  Set style

    val maxHeightForText = 120
    val charHeight = synchronized{
      charSize.fold(
        setCharHeight(simulator.program(rover).commands.size, maxHeightForText))(
          size => size)}

    ctx.fillStyle = "white"
    ctx.font = s"${charHeight}px sans-serif"
    ctx.textAlign = "left"
    ctx.textBaseline = "top"

    val text = simulator.program(rover).commands.toList.sortWith {
      case ((line1, _), (line2, _)) => line1 < line2
    }.map {
      case (line, command) =>
        line -> s"${fulfil(3)(line.toString)} - $command"
    }

    val (textX, textY) = (rover match {
      case roverId if roverId == simulator.roverId1 => 15
      case _ => 415
    }, 420)

    (textY /: text) {
      case (y, (lineId, line)) =>
        val currentLine =
          if (rover == simulator.roverId1) simulator.lineRover1
          else simulator.lineRover2
        if (currentLine==Some(lineId)){
          ctx.fillStyle = "blue"
        } else {
          ctx.fillStyle = "white"
        }
        ctx.fillText(line, textX, y, 370)
        y + charHeight + 4
    }

  }

  /**
   * Clean up the canvas.
   * @param ctx Implicit canvas
   * @return
   */
  def clear()(implicit ctx: CanvasRenderingContext2D): Unit = {

  }

  //  Helpers

  /**
   * Calculate and set the global char height for the
   * program output
   * @param lines Program amount of lines.
   * @param maxHeight Maximum height of the rectangle that
   *                  will hold the program text.
   * @return
   */
  private def setCharHeight(lines: Int, maxHeight: Double): Int = {
    val height = (3 to 18)
      .reverseMap(n => n -> ((n + 4) * lines))
      .filter(_._2 < maxHeight)
      .head._1
    synchronized(charSize=Some(height))
    height
  }

  /**
   * Fulfil from the left with zeros the program line id.
   * @param maxLength Maximum string length.
   * @return
   */
  private def fulfil(maxLength: Int): String => String = s =>
    if (s.length >= maxLength) s else fulfil(maxLength)(s"0$s")

}
