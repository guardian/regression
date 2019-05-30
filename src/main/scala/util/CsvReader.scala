package util

import java.io._

import breeze.linalg._

import scala.io.Source

/**
  * A truly terrible CSV reader
  * It assumes you are reading CSV files that are in the data directory,
  * and you need to pass in a transformer that will convert a row to a
  * case class.
  */
object CsvReader {

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def asCaseClassList[T](csvFile: String, hasHeader: Boolean, transformer: Array[String] => T): List[T] = {
    import scala.collection.mutable.ListBuffer

    val files = getListOfFiles("data")
    val file = files.find(file => file.getName == csvFile)

    val bufferedSource = scala.io.Source.fromFile(file.get)
    val iterator = if(hasHeader) bufferedSource.getLines().drop(1) else bufferedSource.getLines()

    var horribleMutableList = new ListBuffer[T]()

    for (line <- iterator) {
      val cols: Array[String] = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)").map(_.trim)
//      val cols: Array[String] = line.split(",").map(_.trim)
      val newItem = transformer(cols)
      horribleMutableList += newItem
    }
    bufferedSource.close

    horribleMutableList.toList
  }

  /*
    Warning: only reads doubles!
   */
  def asDenseMatrix(csvFile: String, hasHeader: Boolean): DenseMatrix[Double]= {

    val files = getListOfFiles("data")
    val file = files.find(file => file.getName == csvFile).getOrElse(throw new FileNotFoundException(s"couldn't find $csvFile"))
    val skipHeaderLine = if(hasHeader) 1 else 0

    csvread(file, ',', skipLines = skipHeaderLine)
  }
}

object CSVWriter {

  def writeCaseClassListToCsv[T](csvFile: String, transformer: T => Array[String], data: List[T]) = {
    val writer = new BufferedWriter(new FileWriter(csvFile))

    val rows: Seq[String] = data.map { row =>
      s"${transformer(row).mkString(",")}\n"
    }
    rows.foreach(writer.write)
    writer.close()
  }

}

