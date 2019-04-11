package util.vectorised

import breeze.linalg.{DenseMatrix, DenseVector}
import org.specs2.mutable.Specification

class VectorisedLinearErrorCalculatorTest extends Specification {

  "linearMeanSquaredError" should {

    "work for the vectorised model just the same" in {

      val data: DenseMatrix[Double] = DenseMatrix(
        (1.0,1.0),
        (174.0, 189.0)
      )

      val y = DenseVector(96.0, 87.0)

      val theta = DenseVector(
        1.0, 1.0
      )

      val linearMeanSquaredError = VectorisedErrorCalculator.linearMeanSquaredError(data, y, theta)

      linearMeanSquaredError shouldEqual(4212.5)
    }

    "still work when the data matrix isn't square" in {

      val data: DenseMatrix[Double] = DenseMatrix(
        (1.0, 1.0, 1.0),
        (174.0,96.0, 112.0)
      )

      val y = DenseVector(122.0, 125.0, 123.0)

      val theta = DenseVector(1.0, 1.0)

      val linearMeanSquaredError = VectorisedErrorCalculator.linearMeanSquaredError(data, y, theta)

      linearMeanSquaredError shouldEqual(615.5)
    }
  }
}
