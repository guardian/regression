package util.vectorised

import breeze.linalg.DenseMatrix
import org.specs2.mutable.Specification

class VectorisedFeatureScalerTest extends Specification {

  "meanNormalisedData" should {
    "should also work for the vectorised implementation " in {
      val inputData: DenseMatrix[Double] = DenseMatrix(
        (10.0, 15.0, 5.0),
        (15.0, 10.0, 20.0)
      )

      val expected: DenseMatrix[Double] = DenseMatrix(
        (0.0, 0.5, -0.5),
        (0.0, -0.5, 0.5)
      )

      val scaled = VectorisedFeatureScaler.meanNormalisedData(inputData)
      scaled shouldEqual (expected)
    }
  }
}
