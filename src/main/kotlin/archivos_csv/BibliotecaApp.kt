package archivos_csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

// ---- Modelo ----
data class Prestamo(
    val usuarioId: String,
    val edad: Int,
    val genero: String,     // "F" | "M"
    val categoria: String,  // Novela, Ciencia, Historia, Infantil, ...
    val formato: String,    // Impreso, Ebook, Audiolibro
    val devueltoEnTiempo: Boolean // true si "Si", false si "No"
)

// ---- ArrayList global ----
val prestamos = arrayListOf<Prestamo>()

// ---- Función para leer el CSV y llenar la lista ----
fun leerPrestamos() {
    prestamos.clear() // limpiar lista

    csvReader().open(File("prestamos.csv")) {
        readAllWithHeaderAsSequence().forEach { row ->
            prestamos.add(
                Prestamo(
                    usuarioId = row.getValue("UsuarioId"),
                    edad = row.getValue("Edad").toInt(),
                    genero = row.getValue("Genero"),
                    categoria = row.getValue("Categoria"),
                    formato = row.getValue("Formato"),
                    devueltoEnTiempo = row.getValue("DevueltoEnTiempo")
                        .equals("Si", ignoreCase = true)
                )
            )
        }
    }
    println("Se cargaron ${prestamos.size} préstamos desde el CSV ✅")
}

// ---- Función para imprimir todos los registros ----
fun imprimirPrestamos() {
    println("=== Lista de préstamos ===")
    prestamos.forEach { prestamo ->
        println(
            "Usuario: ${prestamo.usuarioId} | " +
                    "Edad: ${prestamo.edad} | " +
                    "Género: ${prestamo.genero} | " +
                    "Categoría: ${prestamo.categoria} | " +
                    "Formato: ${prestamo.formato} | " +
                    "Devuelto a tiempo: ${if (prestamo.devueltoEnTiempo) "Sí" else "No"}"
        )
    }
}

// Consultas al sistema

// 1) Mostrar cuántos préstamos hay en total
fun totalPrestamos() {
    println("Total de préstamos: ${prestamos.size}")
    println()
}

// 2) Mostrar todos los usuarios (id) que hicieron préstamos
fun mostrarUsuarios() {
    println("============ Usuarios con préstamo===============")
    prestamos.forEach { println(it.usuarioId) }
    println()
}

// 3) Mostrar las edades de todos los usuarios
fun mostrarEdades() {
    println("==== Edades de los Usuarios con préstamo=========")
    prestamos.forEach { println(it.edad) }
    println()
}

// 4) Mostrar el id del usuario y su género
fun mostrarIdGenero() {
    println("==== Usuarios con préstamo y su género=========")
    prestamos.forEach { println("id: ${it.usuarioId} - género: ${it.genero}") }
    println()
}

// 5) Contar cuántos préstamos fueron devueltos a tiempo
fun prestamosATiempo() {
    val total = prestamos.count { it.devueltoEnTiempo }
    println("Préstamos devueltos a tiempo: $total")
    println()
}

// 6) Contar cuántos préstamos tuvieron retraso
fun prestamosConRetraso() {
    val total = prestamos.count { !it.devueltoEnTiempo }
    println("Préstamos con retraso: $total")
    println()
}

// Mostrar categoría y formato de cada libro que se prestó
fun mostrarCategoriaFormato(){
    println("==== Mostrar categoría y formato =========")
    prestamos.forEach { println("Categoría: ${it.categoria} - formato: ${it.formato}") }
    println()
}

// ---- Programa principal ----
fun main() {
    leerPrestamos()     // cargar datos en la lista global
    imprimirPrestamos() // mostrar en pantalla
    totalPrestamos()
    mostrarUsuarios()
    mostrarEdades()
    mostrarIdGenero()
    prestamosATiempo()
    prestamosConRetraso()
    mostrarCategoriaFormato()
}
