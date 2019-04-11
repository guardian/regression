package util.vectorised

import breeze.linalg._
import breeze.stats.mean

import org.specs2.mutable.Specification

class MatricesTest extends Specification {

  "Matrix operations in breeze" should {
    "make row vectors by default" in {
      val vector = DenseVector(0, 1, 2, 3, 4, 5)

      vector.toDenseMatrix.cols shouldEqual(6)
      vector.toDenseMatrix.rows shouldEqual(1)
    }

    "work for elementwise addition" in {
      val dm = DenseMatrix(
        (1.0,2.0,3.0),
        (4.0,5.0,6.0)
      )

      val ones = DenseMatrix(
        (1.0,1.0,1.0),
        (1.0,1.0,1.0)
      )

      val expected = DenseMatrix((2.0,3.0,4.0),
        (5.0,6.0,7.0))

      val scaled = dm + ones
      scaled shouldEqual (expected)
    }

    "fail when dimensions don't match" in {
      val dm = DenseMatrix(
        (1.0,2.0,3.0),
        (4.0,5.0,6.0)
      )

      val ones = DenseMatrix(
        (1.0,1.0,1.0),
        (1.0,1.0,1.0),
        (1.0,1.0,1.0)
      )

      dm + ones must throwAn[IllegalArgumentException]
    }

    "work for matrix multiplication" in {
      val matrix1 = DenseMatrix(
        (1.0,2.0),
        (4.0,5.0)
      )

      val matrix2 = DenseMatrix(
        (2.0,2.0),
        (1.0,1.0)
      )

      val expected = DenseMatrix(
        (4.0, 4.0),
        (13.0, 13.0)
      )
       matrix1 * matrix2 shouldEqual(expected)
    }

    "work for matrix multiplication again" in {
      val matrix1 = DenseMatrix((1.0,2.0))

      val matrix2 = DenseMatrix(
      (1.0,2.0),
      (4.0,5.0)
      )

      val expected = DenseMatrix((9.0, 12.0))

      matrix1 * matrix2 shouldEqual(expected)
    }

    "detect daft dimensions for matrix multiplication" in {
      val matrix1 = DenseMatrix((1.0,2.0))

      val matrix2 = DenseMatrix(
        (1.0,2.0),
        (4.0,5.0)
      )

      matrix2 * matrix1 must throwAn[IllegalArgumentException]
    }
  }

//  "mean" should {
//
//  }

}
