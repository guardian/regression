package util

import com.cibo.evilplot.colors.HTMLNamedColors
import com.cibo.evilplot.geometry.Drawable
import com.cibo.evilplot.numeric.{Bounds, Point}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import com.cibo.evilplot.plot.{FunctionPlot, Overlay, ScatterPlot, _}
import model.{GradientDescentHistoryPoint, HeightWeight, SimplePoint}

object Plotter {

  def costItersPlot(data: List[GradientDescentHistoryPoint], title: Option[String]) = {
    val points = data.map(d => Point(d.iteration, d.cost))

    val titleOrDefault = title.getOrElse("Cost vs Iterations plot")
    ScatterPlot(
      points
    ).title(titleOrDefault)
      .xAxis()
      .yAxis()
      .frame()
      .xLabel("iteration number")
      .yLabel("cost")
      .render()
  }

  def heightWeightPlot(data: List[HeightWeight], theta0: Double, theta1: Double, title: Option[String]): Drawable = {

    val points = data.map(d => Point(d.height, d.weight))

    val titleOrDefault = title.getOrElse("Height vs weight data and line fit via linear regression")

    Overlay(
      FunctionPlot.series(function = x => theta1 * x + theta0, name = s"y = $theta1 + $theta0",
        color = HTMLNamedColors.green,
        xbounds = Some(Bounds(-1, 1))
      ),
      ScatterPlot(
        points,
      )
    ).title(titleOrDefault)
      .overlayLegend()
      .standard()
      .render()
  }

  def scatterPlotWithFittedLine(
    data: List[SimplePoint],
    theta0: Double,
    theta1: Double,
    title: Option[String],
    xLabel: String,
    yLabel: String): Drawable = {

    val points = data.map(d => Point(d.x, d.y))

    val titleOrDefault = title.getOrElse(s"$xLabel vs $yLabel data and line fit via linear regression")

    Overlay(
      FunctionPlot.series(function = x => theta1 * x + theta0, name = s"y = $theta1 + $theta0",
        color = HTMLNamedColors.green,
        xbounds = Some(Bounds(-1, 1))
      ),
      ScatterPlot(
        points,
      )
    ).title(titleOrDefault)
      .xLabel(xLabel)
      .yLabel(yLabel)
      .overlayLegend()
      .standard()
      .render()
  }

}
