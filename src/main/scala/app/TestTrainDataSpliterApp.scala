package app

import com.cibo.evilplot.displayPlot
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import model.{DetailedHouse, HeightWeight, House}
import util.{CSVWriter, CsvReader}


object TestTrainDataSpliterApp {


  def transformer(strings: Array[String]) = House(strings(0), strings(4).toDouble, strings(80).toDouble) //there is no sale price for the test set
  def detailedHousetransformer(strings: Array[String]) = DetailedHouse(
    id = strings(0),
    lotArea = strings(4).toDouble,
    numberOfBedrooms = strings(51).toInt,
    numberOfBathrooms = strings(49).toInt,
    numberOfKitchens = strings(50).toInt,
    squareFeet = strings(46).toDouble,
    salePrice = strings(80).toDouble
  )
  val housePrices = CsvReader.asCaseClassList("house-prices-training-data-original.csv", true, detailedHousetransformer)

  val housePricesShuffled = scala.util.Random.shuffle(housePrices)

  val splitPoint = (housePricesShuffled.size * 0.2).toInt

  val (testSet, trainSet) = housePricesShuffled.splitAt(splitPoint)

  def toStringsTransformer(data: DetailedHouse) = Array(data.id, data.lotArea.toString, data.numberOfBedrooms.toString,
    data.numberOfBathrooms.toString, data.numberOfKitchens.toString, data.squareFeet.toString, data.salePrice.toString)
  CSVWriter.writeCaseClassListToCsv[DetailedHouse]("house-prices-test-detailed.csv", toStringsTransformer, testSet)
  CSVWriter.writeCaseClassListToCsv[DetailedHouse]("house-prices-train-detailed.csv", toStringsTransformer, trainSet)

  def main(args: Array[String]): Unit = {

    println("Hello, world!")
  }

}
