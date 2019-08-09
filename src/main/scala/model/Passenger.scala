package model

case class Passenger(
  id: Int,
  survived: Boolean,
  ticketClass: Int,
  name: String,
  sex: String,
  age: Option[Int],
  numberOfSiblingsAboard: Int,
  numberOfParentsAboard: Int,
  ticketNumber: String,
  passengerFare: Double,
  cabin: Option[String],
  embarkPort: String
)