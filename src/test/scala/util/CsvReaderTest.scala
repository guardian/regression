package util

import model.{HeightWeight, House}
import org.specs2.mutable.Specification

class CsvReaderTest extends Specification {

  "csvFileToJson" should {
    "read a csv file" in {
      def transformer(strings: Array[String]) = HeightWeight(strings(0), strings(1).toDouble, strings(2).toDouble)

      val expected = List(
        HeightWeight("Male",174.0,96.0),
        HeightWeight("Male",189.0,87.0),
        HeightWeight("Female",185.0,110.0),
        HeightWeight("Female",195.0,104.0),
        HeightWeight("Male",149.0,61.0),
        HeightWeight("Male",189.0,104.0),
        HeightWeight("Male",147.0,92.0),
        HeightWeight("Male",154.0,111.0),
        HeightWeight("Male",174.0,90.0),
        HeightWeight("Female",169.0,103.0)
      )

      val heightsAndWeights = CsvReader.asCaseClassList("height-weight-sample.csv", true, transformer)
      heightsAndWeights shouldEqual(expected)
    }

    "read a csv house prices file" in {
      def transformer(strings: Array[String]) = House(strings(0), strings(4).toDouble, strings(80).toDouble)

      val expected = List(
        House("1",8450.0,208500.0),
        House("2",9600.0,181500.0),
        House("3",11250.0,223500.0),
        House("4",9550.0,140000.0),
        House("5",14260.0,250000.0),
        House("6",14115.0,143000.0),
        House("7",10084.0,307000.0),
        House("8",10382.0,200000.0),
        House("9",6120.0,129900.0)
      )

      val housePrices = CsvReader.asCaseClassList("house-prices-sample.csv", true, transformer)
      housePrices.take(9) shouldEqual(expected)
    }
  }

}
