package app

import com.cibo.evilplot._
import com.cibo.evilplot.colors.HTMLNamedColors
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot.renderers.PointRenderer
import com.cibo.evilplot.colors.HTMLNamedColors._
import com.cibo.evilplot.numeric.Bounds
import com.cibo.evilplot.plot._
import model.House
import util.CsvReader


object PlotStuffApp {

    def transformer(strings: Array[String]) = House(strings(0), strings(1).toDouble, strings(2).toDouble)
    val housePrices = CsvReader.asCaseClassList("house-prices-train.csv", true, transformer)
    val points = housePrices.map(d => Point(d.salePrice, d.lotArea))

    val labelledPlot = ScatterPlot(
      points
    )
      .xAxis()
      .yAxis()
      .frame()
      .xLabel("sale price")
      .yLabel("lot area")
      .render()
//    displayPlot(labelledPlot)

  val functionPlot = Overlay(
    FunctionPlot.series(x => 0.05 * x + 1, "y = 20x + 1",
      HTMLNamedColors.green, xbounds = Some(Bounds(0, 800000))),
    ScatterPlot(
      points
    )
  ).title("House prices from the Ames dataset.")
    .overlayLegend()
    .standard()
    .render()

  displayPlot(functionPlot)


  def main(args: Array[String]): Unit = {

    println("Hello, world!")
  }
}
