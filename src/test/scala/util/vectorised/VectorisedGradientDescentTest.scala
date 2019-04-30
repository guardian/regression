package util.vectorised

import breeze.linalg.DenseMatrix
import org.specs2.mutable.Specification

class VectorisedGradientDescentTest extends Specification {

  "vectorisedThetaUpdate" should {
    "be updated from the value passed in" in {

      val theta = DenseMatrix(
        (1.0, 1.0, 1.0)
      )

      val x = DenseMatrix(
        (1.0, 1.0, 1.0),
        (0.2, 0.1, 0.2),
        (0.9, 0.5, 0.5)
      )

      val y = DenseMatrix(
        (0.4, 0.3, 0.4)
      )

      val expected = DenseMatrix(
        (0.8566666666666667),
        (0.9756666666666667),
        (0.9056666666666666)
      ).t


      val updatedTheta: DenseMatrix[Double] = VectorisedGradientDescent.vectorisedThetaUpdate(xData = x, yData = y, theta = theta, learningRate = 0.1, None)

      updatedTheta shouldEqual (expected)
    }
  }
}
