package util

import model.SimplePoint

import scala.annotation.tailrec


object FeatureScaler {

  def scaledDividedByMax(data: List[SimplePoint]): List[SimplePoint] = {
    val xList = data.map(_.x)
    val yList = data.map(_.y)

    val maxX = xList.max
    val maxY = yList.max

    data.map { point =>
      SimplePoint(
        point.x / maxX,
        point.y / maxY
      )
    }
  }

  def minMaxScaledData(data: List[SimplePoint]): List[SimplePoint] = {
    val xList = data.map(_.x)
    val yList = data.map(_.y)

    val maxX = xList.max
    val maxY = yList.max
    val minX = yList.min
    val minY = yList.min

    data.map { point =>
      SimplePoint(
        (point.x - minX) / (maxX - minX),
        (point.y - minY) / (maxY - minY)
      )
    }

  }

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
