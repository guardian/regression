package util.vectorised

import breeze.linalg._
import breeze.stats.mean
import org.specs2.mutable.Specification

class MatricesTest extends Specification {

  "Matrix operations in breeze" should {

    "toDenseMatrix" should {
      "make row vectors by default" in {
        val vector = DenseVector(0, 1, 2, 3, 4, 5)

        vector.toDenseMatrix.cols shouldEqual(6)
        vector.toDenseMatrix.rows shouldEqual(1)
      }
    }

    "+" should {
      "provide elementwise addition" in {
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
    }

    "*" should {
      "apply matrix multiplication rules" in {
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

      "work for matrix multiplication" in {
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
  }

  "mean" should {
    "return the mean of all elements in the matrix" in {
      val matrix = DenseMatrix(
        (1.0,2.0),
        (4.0,5.0)
      )

      mean(matrix) shouldEqual(3.0)
    }

  }

  "*:*" should {
    "give you elementwise multiplication" in {
      val matrix = DenseMatrix(
        (1.0,2.0),
        (4.0,5.0)
      )

      val eachElementSquared = DenseMatrix(
        (1.0,4.0),
        (16.0,25.0)
      )

      matrix *:* matrix shouldEqual(eachElementSquared)
    }

    "work for scalar multiplication" in {
      val matrix = DenseMatrix(
        (1.0,2.0),
        (4.0,5.0)
      )

      val doubleIt = matrix *:* 2.0

      val expected = DenseMatrix(
        (2.0, 4.0),
        (8.0, 10.0)
      )

      doubleIt shouldEqual(expected)
    }
  }

  "broadcasting" should {
    //https://github.com/scalanlp/breeze/wiki/Quickstart#Broadcasting
    "mean you can apply an operation to the rows or columns of a matrix" in {

      "give me the mean of each row, as a vector" in {
        val matrix = DenseMatrix(
          (1.0,2.0),
          (4.0,5.0)
        )

        val means = mean(matrix(*, ::))

        means shouldEqual(DenseVector(1.5, 4.5))
      }

      "give me the mean of each column, as a vector" in {
        val matrix = DenseMatrix(
          (1.0,2.0),
          (4.0,5.0)
        )

        val means = mean(matrix(::, *))

        means shouldEqual(Transpose(DenseVector(2.5, 3.5)))
      }
    }
  }


}
