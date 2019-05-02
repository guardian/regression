package util.univariate

import model.univariate.SimplePoint

object FeatureScaler {

  def avg(nums: List[Double]): Double = nums.sum / nums.length.toDouble

  def meanNormalisedData(data: List[SimplePoint]): List[SimplePoint] = {

    def scaled(range: Double, avg: Double, dataPoint: Double): Double = (dataPoint - avg) / range

    val xList: List[Double] = data.map(_.x)

    val minX = xList.min
    val maxX = xList.max
    val range = maxX - minX
    val xAvg = avg(xList)

    data.map { point =>
      SimplePoint(
        scaled(range, xAvg, point.x),
        point.y
      )
    }
  }
}
