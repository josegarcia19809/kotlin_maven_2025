package org.example.archivos_json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File


fun main() {
    // Crear una lista de objetos Persona
    val personas = listOf(
        Persona("Laura", 28, "laura@example.com"),
        Persona("Carlos", 35, "carlos@example.com"),
        Persona("Ana", 22, "ana@example.com")
    )

    // Crear instancia de Gson con formato bonito (pretty print)
    val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    // Convertir la lista a JSON
    val jsonString = gson.toJson(personas)

    // Mostrar el JSON generado en consola
    println(jsonString)

    // Guardar el JSON en un archivo
    val archivo = File("personas_generadas.json")
    archivo.writeText(jsonString)

    println("\n✅ Archivo JSON generado correctamente en: ${archivo.path}")
}
