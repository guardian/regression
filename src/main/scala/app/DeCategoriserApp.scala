package app

import model.{DetailedHouse, House, Passenger}
import util.{CSVWriter, CsvReader}

object DeCategoriserApp {

  //case class Passenger(id: Double, survived: Double, ticketClass: Double, sex: String, age: Double, siblingsAboard: Double, parentsAboard: Double, embarkPort: String)
  def transformer(strings: Array[String]): Passenger = Passenger(
    id = strings(0).toDouble,
    survived = strings(1).toDouble,
    ticketClass = strings(2).toDouble,
    sex = sexAsDouble(strings(4)),
    age = strings(5).toDouble,
    siblingsAboard = strings(6).toDouble,
    parentsAboard = strings(7).toDouble,
    embarkPort = embarkPortAsDouble(strings(8).toString)
  )


  def embarkPortAsDouble(port: String) = {
    port.toUpperCase match {
      case "C" => 1.0
      case "Q" => 2.0
      case "S" => 3.0
    }
  }

  def sexAsDouble(sex: String) = {
    sex match {
      case "female" => 1.0
      case "male" => 2.0
    }
  }
  val passengers = CsvReader.asCaseClassList("titanic-train.csv", true, transformer)

  def toStringsTransformer(data: Passenger) = Array(data.id.toString, data.survived.toString, data.ticketClass.toString,
    data.sex.toString, data.age.toString, data.siblingsAboard.toString, data.parentsAboard.toString, data.embarkPort.toString)

  CSVWriter.writeCaseClassListToCsv[Passenger]("titanic-train-doubles.csv", toStringsTransformer, passengers)

  def main(args: Array[String]): Unit = {

    println("Hello, world!")
  }

}
