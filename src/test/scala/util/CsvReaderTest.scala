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

    "not split on commas in quotes" in {

      /**
        * PassengerId,Survived,Pclass,Name,Sex,Age,SibSp,Parch,Ticket,Fare,Cabin,Embarked
        * 1,0,3,"Braund, Mr. Owen Harris",male,22,1,0,A/5 21171,7.25,,S
        * 2,1,1,"Cumings, Mrs. John Bradley (Florence Briggs Thayer)",female,38,1,0,PC 17599,71.2833,C85,C
        * 3,1,3,"Heikkinen, Miss. Laina",female,26,0,0,STON/O2. 3101282,7.925,,S
        *
        * @param strings
        * @return
        */
      def transformer(strings: Array[String]) = House(strings(0), strings(1).toDouble, strings(2).toDouble)

      val expected = List(
//        Passenger(1.0, 0.0, 3.0, 0.0, 22.0, 1.0, 1.0, )
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
