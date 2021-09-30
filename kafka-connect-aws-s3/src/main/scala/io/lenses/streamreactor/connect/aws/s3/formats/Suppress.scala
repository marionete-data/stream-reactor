package io.lenses.streamreactor.connect.aws.s3.formats

import scala.util.{Success, Try}

object Suppress {

  def apply(r: => Unit): Try[Unit] =
    Try(r).orElse(Success(()))

}
