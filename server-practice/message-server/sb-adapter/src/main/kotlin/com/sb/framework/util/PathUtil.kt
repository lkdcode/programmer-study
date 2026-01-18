package com.sb.framework.util

import com.fasterxml.uuid.Generators
import com.sb.framework.time.nowZoneDateTime
import java.util.UUID

class PathUtil {
    companion object {
        private const val CALLIGRAPHY = "calligraphy"
        private const val USER_PROFILE_IMAGE = "user_profile_image"
        private const val FILE_EXTENSION_PNG = ".png"

        private fun createYear(): String = nowZoneDateTime().year.toString()
        private fun createMonth(): String = String.format("%02d", nowZoneDateTime().month.value)
        private fun createDay(): String = String.format("%02d", nowZoneDateTime().dayOfMonth)

        private fun create(key: String): String =
            "$key/${createYear()}/${createMonth()}/${createDay()}/${Generators.timeBasedEpochGenerator().generate()}"

        fun createCalligraphyPath(calligraphyId: UUID): String =
            "$CALLIGRAPHY/${createYear()}/${createMonth()}/${createDay()}/$calligraphyId$FILE_EXTENSION_PNG"

        val getCalligraphyPath get() = create(CALLIGRAPHY)
        val getUserProfileImagePath get() = create(USER_PROFILE_IMAGE)
    }
}