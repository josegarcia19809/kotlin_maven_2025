package archivos_csv

import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

data class MuerteEverest(
    val numero: Int,
    val nombre: String,
    val fecha: String,
    val edad: Int,
    val expedicion: String,
    val nacionalidad: String,
    val causaMuerte: String,
    val ubicacion: String
)

// ---- ArrayList global ----
val muertes = arrayListOf<MuerteEverest>()

