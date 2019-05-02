package util.vectorised

import breeze.linalg.{*, DenseMatrix, DenseVector, max, min}
import breeze.stats.mean

object VectorisedFeatureScaler {

  def meanNormalisedData(data: DenseMatrix[Double]): DenseMatrix[Double] = {
    val meanVector: DenseVector[Double] = mean(data(*, ::))

    val rangeVector: DenseVector[Double] =  max(data(*, ::)) - min(data(*, ::))

    val dataWithSubtractedMean: DenseMatrix[Double] = data(::, *) - meanVector

    dataWithSubtractedMean(::, *) / rangeVector
  }
}
