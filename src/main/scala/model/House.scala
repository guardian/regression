package model

case class House(id: String, lotArea: Double, salePrice: Double)
case class DetailedHouse(id: String, lotArea: Double, numberOfBedrooms: Int, numberOfBathrooms: Int, numberOfKitchens: Int, squareFeet: Double, salePrice: Double)