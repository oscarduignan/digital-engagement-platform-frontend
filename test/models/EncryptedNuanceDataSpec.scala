/*
 * Copyright 2020 HM Revenue & Customs
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

package models

import org.scalatest.{Matchers, WordSpec}
import play.api.Configuration
import services.NuanceEncryptionService

case class EncryptedNuanceDataSpec() extends WordSpec with Matchers {

  "encryptedNuanceData class" should {

    "allow retrieval of Nuance JSON fields" in {

      val configuration = Configuration.from(
        Map(
          "request-body-encryption.hashing-key" -> "yNhI04vHs9<_HWbC`]20u`37=NGLGYY5:0Tg5?y`W<NoJnXWqmjcgZBec@rOxb^G",
          "request-body-encryption.key" -> "QmFyMTIzNDVCYXIxMjM0NQ==",
          "request-body-encryption.previousKeys" -> List.empty
        )
      )

      val service = NuanceEncryptionService(configuration)
      val encryptedNuanceData = EncryptedNuanceData.create(
        service,
        sessionID = "x"
      )

      val nuanceHashID = encryptedNuanceData.head
      val nuanceEncryptedID = encryptedNuanceData.last

      nuanceHashID should fullyMatch regex "[0-9A-Za-z]+"
      nuanceEncryptedID should startWith("ENCRYPTED-")

      val encryptedNuanceData2 = EncryptedNuanceData.create(
        service,
        sessionID = "x"
      )

      val nuanceHashID2 = encryptedNuanceData2.head
      val nuanceEncryptedID2 = encryptedNuanceData2.last

      nuanceHashID should be(nuanceHashID2)
      nuanceEncryptedID shouldNot be(nuanceEncryptedID2)
    }
  }
}