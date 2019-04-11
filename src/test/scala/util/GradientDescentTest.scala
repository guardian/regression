package util

import breeze.linalg.{DenseMatrix, DenseVector}
import model.SimplePoint
import org.specs2.mutable.Specification

class GradientDescentTest extends Specification{

  "updatedTheta0" should {
    "be updated from the value passed in" in {
      val normalisedData = List(
        SimplePoint(0.22352941176470573, 0.3949999999999999),
        SimplePoint(-0.4529411764705884, -0.6050000000000001),
        SimplePoint(-0.48235294117647076, -0.45500000000000007),
        SimplePoint(0.19411764705882337, 0.2699999999999999),
        SimplePoint(0.5176470588235292, 0.3949999999999999)
      )

      val updatedTheta0: Double = GradientDescent.theta0Updated(normalisedData, 1, 1, 0.01)

      updatedTheta0 shouldEqual (0.99)
    }
  }

  "updatedTheta1" should {
    "be updated from the value passed in" in {

      val normalisedData = List(
        SimplePoint(0.22352941176470573, 0.3949999999999999),
        SimplePoint(-0.4529411764705884, -0.6050000000000001),
        SimplePoint(-0.48235294117647076, -0.45500000000000007),
        SimplePoint(0.19411764705882337, 0.2699999999999999),
        SimplePoint(0.5176470588235292, 0.3949999999999999)
      )

      val updatedTheta1: Double = GradientDescent.theta1Updated(normalisedData, 1, 1, 0.01)

      updatedTheta1 shouldEqual (1.0000905017301038)
    }
  }

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

      updatedTheta shouldEqual (expected) //todo compare with an epsilon
    }
  }

}
