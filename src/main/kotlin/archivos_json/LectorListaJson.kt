package org.example.archivos_json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class Persona(
    val nombre: String,
    val edad: Int,
    val correo: String
)

fun main() {
    // Leer el contenido del archivo JSON
    val contenido = File("personas.json").readText()

    // Crear una instancia de Gson
    val gson = Gson()

    // Tipo de lista a deserializar (List<Persona>)
    val tipoLista = object : TypeToken<List<Persona>>() {}.type

    // Convertir el JSON en una lista de objetos Persona
    val personas: List<Persona> = gson.fromJson(contenido, tipoLista)

    // Mostrar los datos
    for (persona in personas) {
        println("Nombre: ${persona.nombre}")
        println("Edad: ${persona.edad}")
        println("Correo: ${persona.correo}")
        println("-------------")
    }
}
