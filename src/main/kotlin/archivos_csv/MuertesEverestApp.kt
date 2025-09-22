package archivos_csv

import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlin.collections.forEach

data class MuerteEverest(
    val numero: Int,
    val nombre: String,
    val fecha: String,
    val edad: Int?,
    val expedicion: String,
    val nacionalidad: String,
    val causaMuerte: String,
    val ubicacion: String
)

// ---- ArrayList global ----
val muertes = arrayListOf<MuerteEverest>()

// ---- Función para leer el CSV y llenar la lista
fun leerCSV() {
    val file = File("muertes_monte_everest.csv")
    csvReader().open(file) {
        readAllWithHeaderAsSequence().forEach { row ->
            val muerte = MuerteEverest(
                numero = row["No."].orEmpty().toInt(),
                nombre = row["Nombre"].orEmpty(),
                fecha = row["Fecha"].orEmpty(),
                edad = row["Edad"]?.takeIf { it.isNotBlank() }?.toIntOrNull(),
                expedicion = row["Expedicion"].orEmpty(),
                nacionalidad = row["Nacionalidad"].orEmpty(),
                causaMuerte = row["Causa de muerte"].orEmpty(),
                ubicacion = row["Ubicación"].orEmpty()
            )
            muertes.add(muerte)
        }
    }
}

fun mostrarTodosLosRegistros() {
    println("-".repeat(100))
    muertes.forEach { println(it) }
    println()
}

fun mostrarEdadesNulas() {
    val nulos = muertes.filter { it.edad == null }
    println("Registros con edad = null: ")
    nulos.forEach {
        println("➡️ ${it.nombre} (${it.fecha}, ${it.expedicion})")
    }
    println()
}

// ✅️1) Mostrar nombre y nacionalidad de aquellas personas que pertenecieron a
// United Kingdom. (nacionalidad)
fun mostrarPersonasDeUK() {
    println("Personas que pertenecieron a United Kingdom")
    println(".".repeat(100))
    muertes
        .filter { it.nacionalidad.equals("United Kingdom", ignoreCase = true) }
        .forEach { println("🧗🏼‍♂️ Nombre: ${it.nombre}, Nacionalidad: ${it.nacionalidad}") }
    println()
}

// ✅ 2) Mostrar nombre y edad de aquellas personas cuya edad esté entre 18 y 21
fun mostrarPersonasEntre18y21() {
    println("Personas cuya edad esté entre 18 y 21")
    println(".".repeat(100))
    muertes
        .filter { it.edad != null && it.edad in 18..21 }
        .forEach { println("🧗🏼️ Nombre: ${it.nombre}, Edad: ${it.edad}") }
    println()
}

// ✅ 3) Mostrar nombre y edad de las personas que no sean menores a los 50 años.
fun mostrarMayoresde50() {
    println("Personas cuya edad no sea menor a los 50 años")
    println(".".repeat(100))
    muertes
        .filter { it.edad != null && it.edad >= 50 }
        .forEach { println("🧗🏼️ Nombre: ${it.nombre}, Edad: ${it.edad}") }
    println()
}

// ✅ 4) Mostrar nombre, edad y nacionalidad de las personas que tengan 25 años o que sean
// de nacionalidad rusa (Russia).
fun mostrarPersonasRusasOEdad25() {
    println("Personas rusas o de 25 años")
    println(".".repeat(100))
    muertes
        .filter { it.edad == 25 || it.nacionalidad.equals("Russia", ignoreCase = true) }
        .forEach {
            println(
                "🙍🏼‍♂️ Nombre: ${it.nombre}, edad: ${it.edad}, " +
                        "nacionalidad: ${it.nacionalidad}"
            )
        }
    println()
}


// ✅ 5) ¿Cuántas nacionalidades diferentes están representadas en el conjunto de datos?
fun contarNacionalidades() {
    val nacionalidadesUnicas = muertes
        .map { it.nacionalidad }
        .filter { it.isNotBlank() }
        .toSet()
    println("🚩 Hay ${nacionalidadesUnicas.size} nacionalidades diferentes")
    println()
    println(".".repeat(100))
}

//✅ 6) Mostrar nacionalidades diferentes en el conjunto de datos
fun mostrarNacionalidadesDiferentes() {
    val nacionalidadesUnicas = muertes
        .map { it.nacionalidad }
        .filter { it.isNotBlank() }
        .toSet()
    println("🚩 Nacionalidades diferentes en el conjunto de datos")
    nacionalidadesUnicas.forEachIndexed { index, nacion ->
        // Cada nombre ocupa un ancho fijo de 20 caracteres
        print(nacion.padEnd(20))
        // Cada vez que el índice + 1 sea múltiplo de 5, hacer salto de línea
        if ((index + 1) % 4 == 0) {
            println()
        }
    }
    println() // salto final por estética
}


// ✅ 7) Pedir una letra. Mostrar nombre y causa de muerte de las personas cuyos nombres
// empiecen con esa letra.
fun mostrarPorLetraInicial() {
    print("Ingresa una letra: ")
    val letra = readln().trim().uppercase()
    if (letra.isEmpty() || letra.length != 1) {
        println("Por favor, ingresa una sola letra válida")
        return
    }
    muertes
        .filter { it.nombre.uppercase().startsWith(letra) }
        .forEach { println("Nombre: ${it.nombre}, causa de muerte: ${it.causaMuerte}") }
}

// ✅8) Mostrar nombre y causa de muerte de todas las personas en orden ascendente según causa
// de muerte.
fun mostrarOrdenadoPorCausaDeMuerte() {
    muertes
        .sortedBy { it.causaMuerte }
        .forEach { println("Nombre: ${it.nombre}, causa de muerte: ${it.causaMuerte}") }
}

// ✅ 9) ¿Cuál es el nombre de la persona más joven que falleció en una expedición registrada?
fun mostrarPersonaMasJoven() {
    val personaMasJoven = muertes
        .filter { it.edad != null }
        .minByOrNull { it.edad!! }
    if (personaMasJoven != null) {
        println(
            "La persona más joven que falleció fue ${personaMasJoven.nombre} con " +
                    "${personaMasJoven.edad} años"
        )
    } else {
        println("No hay edades registradas para determinar la persona más joven")
    }
}

// ✅ 10) ¿Cuál es la edad promedio de las personas que fallecieron en el conjunto de datos?
fun calcularEdadPromedio() {
    val edades = muertes.mapNotNull { it.edad }
    if (edades.isNotEmpty()) {
        val promedio = edades.average()
        println(
            ("La edad promedio de las personas con edad registrada es %.2f " +
                    "años").format(promedio)
        )
    } else {
        println("No hay edades registradas para calcular el promedio")
    }
}

// ✅ 11) ¿Cuál fue la causa de muerte más común entre las personas fallecidas?
fun mostrarCausaMuerteMasComun() {
    val causas = muertes.map { it.causaMuerte }
        .filter { it.isNotBlank() }

    val causaMasComun = causas
        .groupingBy { it }
        .eachCount()
        .maxByOrNull { it.value }

    if (causaMasComun != null) {
        println(
            "La causa de muerte más común fue '${causaMasComun.key}' " +
                    "con ${causaMasComun.value} casos."
        )
    } else {
        println("No hay datos suficientes para determinar la causa de muerte más común")
    }
}

fun main() {
    leerCSV()
    // mostrarTodosLosRegistros()
    // mostrarEdadesNulas()
    // mostrarPersonasDeUK()
    // mostrarPersonasEntre18y21()
    // mostrarMayoresde50()
    // mostrarPersonasRusasOEdad25()
    // contarNacionalidades()
    // mostrarNacionalidadesDiferentes()
    // mostrarPorLetraInicial()
//    mostrarOrdenadoPorCausaDeMuerte()
//     mostrarPersonaMasJoven()
//     calcularEdadPromedio()
    mostrarCausaMuerteMasComun()
}

