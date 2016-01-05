package scalera.moonrover.display

import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.html
import scalera.moonrover.{Simulator, Launch}

@JSExport
object Boot {

  type IntervalHandler = Int

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    println("Initializing simulator ...")

    implicit val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    var sim = Simulator(Launch.program)

    var currentTick: Int = 0

    var handler: Option[IntervalHandler] = None

    def preStart(): Unit = {
      SimulatorRender.renderBackground(sim)
    }

    def run: Unit = {
      println(sim)
      sim = SimulatorRender.render(sim, currentTick)
      if (sim.state.value.areRoversTogether) {
        SimulatorRender.render(sim,currentTick)
        finished
      } else {
        currentTick += 1
      }
    }

    def finished: Unit = {
      //  Remove interval
      handler.foreach(dom.clearInterval)
      dom.alert(s"Congratulations!\n" +
        s"Your rovers found at tick $currentTick.")
    }

    preStart()

    handler = Some(dom.setInterval(() => run, 1500))

  }
}



