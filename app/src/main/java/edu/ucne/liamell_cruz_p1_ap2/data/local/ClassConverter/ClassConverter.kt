package edu.ucne.liamell_cruz_p1_ap2.data.local.ClassConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.format(formatter)

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate = LocalDate.parse(dateString, formatter)
}
