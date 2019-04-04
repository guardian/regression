package util

import breeze.linalg.{DenseMatrix, DenseVector}
import model.SimplePoint
import org.specs2.mutable.Specification

class LinearErrorCalculatorTest extends Specification {

  "linearMeanSquaredError" should {
    "sum up the squares of errors" in {

      val data = List(
        SimplePoint(174.0,96.0),
        SimplePoint(189.0,87.0)
      )

      val linearMeanSquaredError: Double = LinearErrorCalculator.linearMeanSquaredError(data, 1, 1)

      linearMeanSquaredError shouldEqual(4212.5)
    }
  }

  "linearMeanSquaredError" should {
    "work for the vectorised model" in {

      val data = DenseMatrix(
        (174.0,96.0),
        (189.0,87.0)
      )

      val y = DenseVector(122.1, 125.0)

      val theta = DenseVector(1.0, 1.0)

      val linearMeanSquaredError = VectorisedErrorCalculator.linearMeanSquaredError(data, y, theta)

      linearMeanSquaredError shouldEqual(4212.5)
    }
  }

  "linear mean absolute error" should {
    "sum up absolute errors" in {
      val data = List(
        SimplePoint(174.0,96.0),
        SimplePoint(189.0,87.0)
      )

      val meanAbsoluteError: Double = LinearErrorCalculator.linearMeanAbsoluteError(data, 1, 1)

      meanAbsoluteError shouldEqual(91.0)
    }
  }
}
