package app

import com.cibo.evilplot.displayPlot
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import model.{HeightWeight, House}
import util.{CSVWriter, CsvReader}


object TestTrainDataSpliterApp {


  def transformer(strings: Array[String]) = House(strings(0), strings(4).toDouble, strings(80).toDouble) //there is no sale price for the test set
  val housePrices = CsvReader.asCaseClassList("house-prices-training-data-original.csv", true, transformer)

  val housePricesShuffled = scala.util.Random.shuffle(housePrices)

  val splitPoint = (housePricesShuffled.size * 0.2).toInt

  val (testSet, trainSet) = housePricesShuffled.splitAt(splitPoint)

  def toStringsTransformer(data: House) = Array(data.id, data.lotArea.toString, data.salePrice.toString)
  CSVWriter.writeCaseClassListToCsv("house-prices-test.csv", toStringsTransformer, testSet)
  CSVWriter.writeCaseClassListToCsv("house-prices-train.csv", toStringsTransformer, trainSet)

  def main(args: Array[String]): Unit = {

    println("Hello, world!")
  }

}
