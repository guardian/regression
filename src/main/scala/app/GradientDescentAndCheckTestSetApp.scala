package app

import com.cibo.evilplot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import model.{HeightWeight, House, SimplePoint}
import util.univariate.GradientDescent.LearnedParameterSet
import util._
import util.univariate.{FeatureScaler, GradientDescent, LinearErrorCalculator}


object GradientDescentAndCheckTestSetApp {

  def main(args: Array[String]): Unit = {

    def arrayToHouse(strings: Array[String]) = House(strings(0), strings(1).toDouble, strings(2).toDouble)
    def HouseTo2dPoint(data: List[House]) = data.map(dataPoint => SimplePoint(dataPoint.lotArea, dataPoint.salePrice))

    //load in the training data
    val sizesPrices = CsvReader.asCaseClassList("house-prices-train.csv", true, arrayToHouse)
    val data = HouseTo2dPoint(sizesPrices)
    val normalisedData = FeatureScaler.meanNormalisedData(data)

    //run gradient descent
    val learnedParameters: LearnedParameterSet = GradientDescent.gradientDescent(normalisedData, 0, 1, 1, 5000)

    //plot the trend of cost vs iteration so we know if gradient descent is working
    displayPlot(Plotter.costItersPlot(learnedParameters.history, None))
    //plot our training data, and the line that we fit with gradient descent
    displayPlot(Plotter.scatterPlotWithFittedLine(
      data = normalisedData,
      theta0 = learnedParameters.theta0,
      theta1 = learnedParameters.theta1,
      title = Some("Lot size vs selling price TRAINING data with the line we fit"),
      xLabel = "Lot size",
      yLabel = "Selling price"
    )
    )


    //load in test data
    val housePricesTestData = CsvReader.asCaseClassList("house-prices-test.csv", true, arrayToHouse)
    val housePricesTestDataPoints = HouseTo2dPoint(housePricesTestData)
    val housePricesTestDataScaled = FeatureScaler.meanNormalisedData(housePricesTestDataPoints)

    displayPlot(Plotter.scatterPlotWithFittedLine(
      data = housePricesTestDataScaled,
      theta0 = learnedParameters.theta0,
      theta1 = learnedParameters.theta1,
      title = Some("Lot size vs selling price TEST data with the line we fit"),
      xLabel = "Lot size",
      yLabel = "Selling price"
    )
    )
    //just print out the mean absolute error for the line we fit when we compare to the test set
    val meanSquaredError = LinearErrorCalculator.linearMeanSquaredError(normalisedData, learnedParameters.theta0, learnedParameters.theta1)
    println(s"Hello, world! This is the mean squared error: $meanSquaredError")
  }

}
