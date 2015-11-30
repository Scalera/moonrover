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

    var handler: Option[IntervalHandler] = None

    def run: Unit = {
      println(sim)
      sim = SimulatorRender.render(sim)
      if (sim.state.value.areRoversTogether) finished
    }

    def finished: Unit = {
      //  Remove interval
      handler.foreach(dom.clearInterval)
      //  TODO Congratulate winner
      ()
    }

    handler = Some(dom.setInterval(() => run, 1000))

  }
}



