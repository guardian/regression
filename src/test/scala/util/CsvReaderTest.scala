package util

import breeze.linalg.DenseMatrix
import model.{HeightWeight, House, Passenger}
import org.specs2.mutable.Specification

class CsvReaderTest extends Specification {

  "csvFileToJson" should {

    "read a csv house prices file" in {
      def transformer(strings: Array[String]) = House(strings(0), strings(1).toDouble, strings(2).toDouble)

      val expected = List(
        House("717",10800.0,159500.0),
        House("453",9303.0,204000.0),
        House("1003",11957.0,232000.0),
        House("215",10900.0,161750.0),
        House("929",11838.0,236500.0)
      )

      val housePrices = CsvReader.asCaseClassList("house-prices-sample.csv", true, transformer)
      housePrices.take(9) shouldEqual(expected)
    }

  }

  "also be able to read a csv house prices file as a DenseMatrix" in {
    val expected = DenseMatrix(
      (717.0,10800.0,159500.0),
      (453.0,9303.0,204000.0),
      (1003.0,11957.0,232000.0),
      (215.0,10900.0,161750.0),
      (929.0,11838.0,236500.0)
    )

    val housePrices = CsvReader.asDenseMatrix("house-prices-sample.csv", true)
    housePrices shouldEqual(expected)
  }

}
