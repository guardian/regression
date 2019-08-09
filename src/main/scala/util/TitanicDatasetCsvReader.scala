package util

import java.io.File

import breeze.linalg.{DenseMatrix, DenseVector}
import com.github.tototoshi.csv._
import model.Passenger

import scala.collection.immutable

object TitanicDatasetCsvReader {

  def readFile(filename: String): List[Passenger] = {
    val reader = CSVReader.open(new File(filename))
    val csvStream: immutable.Seq[Map[String, String]] = reader.toStreamWithHeaders
    csvStream.map { mapForPassenger: Map[String, String] =>
      Passenger(
      // toInt with throw exceptions if it can't parse the string as an int
      // we may need to deal with that at some point
      id = mapForPassenger("PassengerId").toInt,
      survived = if (mapForPassenger("Survived") == "1") true else false,
      ticketClass = mapForPassenger("Pclass").toInt,
      name = mapForPassenger("Name"),
      sex = mapForPassenger("Sex"),
      age = if (mapForPassenger("Age").trim.isEmpty) None else Some(mapForPassenger("Age").toInt),
      numberOfSiblingsAboard = mapForPassenger("SibSp").toInt,
      numberOfParentsAboard = mapForPassenger("Parch").toInt,
      ticketNumber = mapForPassenger("Ticket"),
      passengerFare = mapForPassenger("Fare").toDouble,
      cabin = if (mapForPassenger("Cabin").trim.isEmpty) None else Some(mapForPassenger("Cabin")),
      embarkPort = mapForPassenger("Embarked")
    )}.toList
  }

  //TODO preprocessing step to remove data points that are missing data
  def toDenseVectorList(passengers: List[Passenger]) =

    for {
      passenger <- passengers
      age <- passenger.age
    } yield {
      val sex = passenger.sex match {
        case "female" => 1.0
        case _ => 0.0
      }

      val hasCabin = if(passenger.cabin.isDefined) 1 else 0

      val isEmbarkPortC = if(passenger.embarkPort == "C") 1 else 0
      val isEmbarkPortQ = if(passenger.embarkPort == "Q") 1 else 0
      val isEmbarkPortS = if(passenger.embarkPort == "S") 1 else 0

      DenseVector(
        passenger.ticketClass.toDouble,
        age.toDouble,
        passenger.numberOfSiblingsAboard.toDouble,
        passenger.numberOfParentsAboard.toDouble,
        passenger.passengerFare,
        hasCabin,
        isEmbarkPortC,
        isEmbarkPortQ,
        isEmbarkPortS
      )
    }

//    val asRowList: List[DenseVector[Double]] = passengers flatMap { passenger =>
//      val sex = passenger.sex match {
//        case "female" => 1.0
//        case _ => 0.0
//      }
//
//      val hasCabin = if(passenger.cabin.isDefined) 1 else 0
//
//      val isEmbarkPortC = if(passenger.embarkPort == "C") 1 else 0
//      val isEmbarkPortQ = if(passenger.embarkPort == "Q") 1 else 0
//      val isEmbarkPortS = if(passenger.embarkPort == "S") 1 else 0
//
//      if (passenger.age.isEmpty) {
//        // Excludes passengers whose age is missing
//        None
//      } else {
//        Some(DenseVector(
//          passenger.ticketClass.toDouble,
//          passenger.age.get.toDouble,
//          passenger.numberOfSiblingsAboard.toDouble,
//          passenger.numberOfParentsAboard.toDouble,
//          passenger.passengerFare,
//          hasCabin,
//          isEmbarkPortC,
//          isEmbarkPortQ,
//          isEmbarkPortS
//        ))
//      }
//
//    }

}
