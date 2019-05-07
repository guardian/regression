package app.vectorised

import breeze.linalg.{DenseMatrix, DenseVector}
import com.cibo.evilplot.displayPlot
import util.vectorised.{VectorisedFeatureScaler, VectorisedGradientDescent}
import util.{CsvReader, Plotter}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._


object GradientDescentVectorisedApp {

  def main(args: Array[String]): Unit = {

    val detailedHouseData: DenseMatrix[Double] = CsvReader.asDenseMatrix("house-prices-train-detailed.csv", true)

    val featureMatrixX: DenseMatrix[Double] = detailedHouseData(::, 1 to 5).t
    val y: DenseVector[Double] = detailedHouseData(::, 6)

    //load in the training data
    val normalisedData = VectorisedFeatureScaler.meanNormalisedData(featureMatrixX)

    val zeroFeature = DenseMatrix.ones[Double](1, featureMatrixX.cols)
    val normalisedDataWithZeroFeature = DenseMatrix.vertcat(zeroFeature, normalisedData)

    val theta = DenseVector.ones[Double](featureMatrixX.rows + 1)

    //new thetas 0 DenseVector(179428.50730805576, 6733.298825663688)
    //run gradient descent
    val learnedParameters = VectorisedGradientDescent.gradientDescent(normalisedDataWithZeroFeature, y.toDenseVector, theta, 0.0001, 3000, None)

    //plot the trend of cost vs iteration so we know if gradient descent is working
    displayPlot(Plotter.costItersPlot(learnedParameters.history, None))

  }


}
