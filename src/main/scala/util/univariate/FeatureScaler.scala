package util.univariate

import model.SimplePoint


object FeatureScaler {

  def avg(nums: List[Double]): Double = nums.sum / nums.length.toDouble

  def meanNormalisedData(data: List[SimplePoint]): List[SimplePoint] = {

    def scaled(min: Double, max: Double, avg: Double, dataPoint: Double): Double = {
      val range = max - min
      (dataPoint - avg) / range
    }

    val xList = data.map(_.x)

    val minX = xList.min
    val maxX = xList.max
    val xAvg = avg(xList)

    data.map { point =>
      SimplePoint(
        scaled(minX, maxX, xAvg, point.x),
        point.y
      )
    }
  }

}
