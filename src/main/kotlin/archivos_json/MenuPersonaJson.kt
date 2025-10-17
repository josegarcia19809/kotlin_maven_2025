package org.example.archivos_json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.NumberFormatException


object PersonaRepository {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val ruta: String = "personas.json"

    fun leerTodas(): MutableList<Persona> {
        val archivo = File(ruta)
        if (!archivo.exists()) {
            // Si no existe, crear archivo vacío con lista vacía
            archivo.parentFile?.mkdirs()
            archivo.writeText("[]")
            return mutableListOf()
        }
        val contenido = archivo.readText()
        val tipo = object : TypeToken<List<Persona>>() {}.type
        return gson.fromJson<List<Persona>>(contenido, tipo)?.toMutableList() ?: mutableListOf()
    }

    fun guardarTodas(personas: List<Persona>) {
        val archivo = File(ruta)
        archivo.writeText(gson.toJson(personas))
    }
}

class App {
    private var personas: MutableList<Persona> = mutableListOf()
    private var cambiosPendientes: Boolean = false

    fun run() {
        personas = PersonaRepository.leerTodas()
        loopMenu()
    }

    private fun loopMenu() {
        while (true) {
            println("\n=== MENÚ PERSONAS ===")
            println("1) Listar personas")
            println("2) Agregar persona")
            println("3) Eliminar persona por índice")
            println("4) Buscar persona por nombre")
            println("5) Guardar cambios")
            println("6) Salir (guardar y salir)")
            println("7) Salir sin guardar")
            print("Selecciona una opción: ")

            when (readLine()?.trim()) {
                "1" -> listar()
                "2" -> agregar()
                "3" -> eliminarPorIndice()
                "4" -> buscarPorNombre()
                "5" -> guardar()
                "6" -> {
                    guardar()
                    println("Saliendo... adiós.")
                    return
                }
                "7" -> {
                    if (cambiosPendientes) {
                        print("Hay cambios no guardados. ¿Seguro que quieres salir sin guardar? (s/n): ")
                        val r = readLine()?.trim()
                        if (r?.lowercase() == "s") {
                            println("Saliendo sin guardar...")
                            return
                        }
                    } else {
                        println("Saliendo...")
                        return
                    }
                }
                else -> println("Opción no válida, intenta de nuevo.")
            }
        }
    }

    private fun listar() {
        if (personas.isEmpty()) {
            println("No hay personas para mostrar.")
            return
        }
        println("\nLista de personas:")
        personas.forEachIndexed { i, p ->
            println("[$i] Nombre: ${p.nombre}, Edad: ${p.edad}, Correo: ${p.correo}")
        }
    }

    private fun agregar() {
        print("Nombre: ")
        val nombre = readLine()?.trim()
        if (nombre.isNullOrEmpty()) {
            println("Nombre inválido.")
            return
        }

        print("Edad: ")
        val edadStr = readLine()?.trim()
        val edad = try {
            edadStr?.toInt() ?: run {
                println("Edad inválida."); return
            }
        } catch (e: NumberFormatException) {
            println("Edad inválida.")
            return
        }

        print("Correo: ")
        val correo = readLine()?.trim()
        if (correo.isNullOrEmpty()) {
            println("Correo inválido.")
            return
        }

        val nueva = Persona(nombre = nombre, edad = edad, correo = correo)
        personas.add(nueva)
        cambiosPendientes = true
        println("Persona agregada correctamente.")
    }

    private fun eliminarPorIndice() {
        if (personas.isEmpty()) {
            println("La lista está vacía.")
            return
        }
        listar()
        print("Ingresa el índice a eliminar: ")
        val idxStr = readLine()?.trim()
        val idx = try {
            idxStr?.toInt() ?: run { println("Índice inválido."); return }
        } catch (e: NumberFormatException) {
            println("Índice inválido."); return
        }

        if (idx < 0 || idx >= personas.size) {
            println("Índice fuera de rango.")
            return
        }

        val eliminado = personas.removeAt(idx)
        cambiosPendientes = true
        println("Eliminado: ${eliminado.nombre}")
    }

    private fun buscarPorNombre() {
        print("Ingresa nombre o parte del nombre a buscar: ")
        val texto = readLine()?.trim()
        if (texto.isNullOrEmpty()) {
            println("Texto inválido.")
            return
        }
        val resultados = personas.filter { it.nombre.contains(texto, ignoreCase = true) }
        if (resultados.isEmpty()) {
            println("No se encontraron coincidencias.")
            return
        }
        println("Resultados:")
        resultados.forEachIndexed { i, p ->
            println("${i + 1}) Nombre: ${p.nombre}, Edad: ${p.edad}, Correo: ${p.correo}")
        }
    }

    private fun guardar() {
        PersonaRepository.guardarTodas(personas)
        cambiosPendientes = false
        println("Cambios guardados correctamente.")
    }
}

// función main mínima para arrancar la app
fun main() {
    val app = App()
    app.run()
}
