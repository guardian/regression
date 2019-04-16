package util

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
}
