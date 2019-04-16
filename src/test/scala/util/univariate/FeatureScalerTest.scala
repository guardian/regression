package util.univariate

import model.univariate.SimplePoint
import org.specs2.mutable.Specification

class FeatureScalerTest extends Specification {

  "meanNormalisedData" should {
    "scale the features and not the y in " in {
      val inputData = List(
        SimplePoint(78, 90),
        SimplePoint(55, 50),
        SimplePoint(54, 56),
        SimplePoint(77, 85),
        SimplePoint(88, 90)
      )

      val expected = List(
        SimplePoint(0.22352941176470573, 90),
        SimplePoint(-0.4529411764705884, 50),
        SimplePoint(-0.48235294117647076, 56),
        SimplePoint(0.19411764705882337, 85),
        SimplePoint(0.5176470588235292, 90)
      )

      val scaled = FeatureScaler.meanNormalisedData(inputData)
      scaled shouldEqual (expected)
    }
  }


  "avg" should {
    "give the average of a list of values" in {
      val inputValues = List(78.0, 55.0, 54.0, 77.0, 88.0)

      FeatureScaler.avg(inputValues) shouldEqual (70.4)
    }
  }
}
