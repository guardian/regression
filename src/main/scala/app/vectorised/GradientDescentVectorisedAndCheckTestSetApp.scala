package app.vectorised

import breeze.linalg.{DenseMatrix, DenseVector}
import com.cibo.evilplot.displayPlot
import util.vectorised.{VectorisedErrorCalculator, VectorisedFeatureScaler, VectorisedGradientDescent}
import util.{CsvReader, Plotter}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._


object GradientDescentVectorisedAndCheckTestSetApp {

  def main(args: Array[String]): Unit = {

    val detailedHouseData: DenseMatrix[Double] = CsvReader.asDenseMatrix("house-prices-train-detailed.csv", true)

    val featureMatrixX: DenseMatrix[Double] = detailedHouseData(::, 0 to 5).t
    val y: DenseVector[Double] = detailedHouseData(::, 6)


    //load in the training data
    val normalisedData = VectorisedFeatureScaler.meanNormalisedData(featureMatrixX)

    val featureZero = DenseMatrix.ones[Double](1, featureMatrixX.cols)
    val normalisedDataWithZeroFeature = DenseMatrix.vertcat(featureZero, normalisedData)

    val theta = DenseVector.ones[Double](featureMatrixX.rows + 1)

    //new thetas 0 DenseVector(179428.50730805576, 6733.298825663688)
    //run gradient descent
    val learnedParameters = VectorisedGradientDescent.gradientDescent(normalisedDataWithZeroFeature, y.toDenseVector, theta, 0.0001, 3000, Some(1.0))

    //plot the trend of cost vs iteration so we know if gradient descent is working
    displayPlot(Plotter.costItersPlot(learnedParameters.history, None))



    //load in test data
    val detailedHouseTestData: DenseMatrix[Double] = CsvReader.asDenseMatrix("house-prices-test-detailed.csv", true)

    val featureMatrixTestX: DenseMatrix[Double] = detailedHouseTestData(::, 0 to 5).t
    val normalisedTestData = VectorisedFeatureScaler.meanNormalisedData(featureMatrixTestX)
    val testY: DenseVector[Double] = detailedHouseTestData(::, 6)
    val featureZeroTest = DenseMatrix.ones[Double](1, featureMatrixTestX.cols)

    val normalisedTestDataWithZeroFeature = DenseMatrix.vertcat(featureZeroTest, normalisedTestData)

    //just print out the mean absolute error for the line we fit when we compare to the test set
    val meanSquaredError = VectorisedErrorCalculator.linearMeanSquaredError(normalisedTestDataWithZeroFeature, testY, learnedParameters.theta)
    val meanSquaredErrorTrain = VectorisedErrorCalculator.linearMeanSquaredError(normalisedDataWithZeroFeature, y, learnedParameters.theta)

    println(s"Hello, world! This is the mean squared error for the test set: $meanSquaredError")
    println(s"Hello, world! This is the mean squared error for the training set: $meanSquaredErrorTrain")
  }


}
