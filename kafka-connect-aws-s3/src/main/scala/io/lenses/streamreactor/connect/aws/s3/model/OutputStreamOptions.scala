/*
 * Copyright 2021 Lenses.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lenses.streamreactor.connect.aws.s3.model

import cats.syntax.all._
import io.lenses.streamreactor.connect.aws.s3.config.{FormatSelection, S3ConfigDefBuilder}
import io.lenses.streamreactor.connect.aws.s3.config.S3ConfigSettings.LOCAL_TMP_DIRECTORY
import io.lenses.streamreactor.connect.aws.s3.formats.S3FormatWriter
import io.lenses.streamreactor.connect.aws.s3.model.location.{LocalPathLocation, LocalRootLocation, RemoteS3PathLocation}
import io.lenses.streamreactor.connect.aws.s3.sink.ProcessorException
import io.lenses.streamreactor.connect.aws.s3.storage.StorageInterface
import io.lenses.streamreactor.connect.aws.s3.storage.stream.{BuildLocalOutputStream, S3OutputStream}

import java.nio.file.Files
import java.util.UUID
import scala.util.Try

object OutputStreamOptions {

  private def createLocalDirBasedOnSinkName(sinkName: String): String = {
    Files.createTempDirectory(s"$sinkName.${UUID.randomUUID().toString}.").toAbsolutePath.toString
  }

  private def fetchFromProps(props: Map[String,_], propertyToFetch: String): Option[String] = {
    props.get(propertyToFetch).collect {
      case value: String if value.trim.nonEmpty => value.trim
    }
  }

  def apply(confDef: S3ConfigDefBuilder): Either[Exception, OutputStreamOptions] = {
    val thing: Option[String] = fetchFromProps(confDef.getParsedValues, LOCAL_TMP_DIRECTORY)
      .orElse{
        confDef.sinkName.map(createLocalDirBasedOnSinkName)
      }
    thing match {
      case Some(value: String) => OutputStreamOptions(LocalRootLocation(value)).asRight
      case None => new IllegalStateException(s"Either a local temporary directory ($LOCAL_TMP_DIRECTORY) or a Sink Name (name) must be configured.").asLeft[OutputStreamOptions]
    }
  }

}

case class OutputStreamOptions(localLocation: LocalRootLocation) {

   def createFormatWriter(formatSelection: FormatSelection, path: RemoteS3PathLocation, initialOffset: Offset, updateOffsetFn: Offset => () => Unit)(implicit storageInterface: StorageInterface): Either[ProcessorException, S3FormatWriter] = {
    val writer = for {
      outputStream <- Try(createOutputStreamFn(path.toLocalPathLocation(localLocation), initialOffset, updateOffsetFn)).toEither
      writer <- Try(S3FormatWriter(formatSelection, outputStream)).toEither
    } yield writer
     writer.leftMap(ProcessorException(_))
  }

  private def createOutputStreamFn(location: LocalPathLocation, initialOffset: Offset, updateOffsetFn: Offset => () => Unit)(implicit storageInterface: StorageInterface): () => S3OutputStream = {
    () => new BuildLocalOutputStream(location, updateOffsetFn)
  }

}

