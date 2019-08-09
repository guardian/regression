package util


import model.{House, Passenger}
import org.specs2.mutable.Specification

class TitanicDatasetCsvReaderTest extends Specification {

  "csvFileToPassengerList" should {

    "read a CSV of titanic passenger data" in {

      val expectedPassengers = List(
        Passenger(
          id = 1,
          survived = false,
          ticketClass = 3,
          name = "Braund, Mr. Owen Harris",
          sex = "male",
          age = Some(22),
          numberOfSiblingsAboard = 1,
          numberOfParentsAboard = 0,
          ticketNumber = "A/5 21171",
          passengerFare = 7.25,
          cabin = None,
          embarkPort = "S"
        ),
        Passenger(
          id = 2,
          survived = true,
          ticketClass = 1,
          name = "Cumings, Mrs. John Bradley (Florence Briggs Thayer)",
          sex = "female",
          age = Some(38),
          numberOfSiblingsAboard = 1,
          numberOfParentsAboard = 0,
          ticketNumber = "PC 17599",
          passengerFare = 71.2833,
          cabin = Some("C85"),
          embarkPort = "C"
        ),
        Passenger(
          id = 3,
          survived = true,
          ticketClass = 3,
          name = "Heikkinen, Miss. Laina",
          sex = "female",
          age = Some(26),
          numberOfSiblingsAboard = 0,
          numberOfParentsAboard = 0,
          ticketNumber = "STON/O2. 3101282",
          passengerFare = 7.925,
          cabin = None,
          embarkPort = "S"
        )
      )


      val passengerList = TitanicDatasetCsvReader.readFile("data/titanic-train-sample.csv")

      passengerList shouldEqual (expectedPassengers)

    }


  }

}
