package org.example.fundamentos

fun main() {
    for (noLista in 1..6) {
        println("-".repeat(50) + noLista)
        val calificaciones = arrayOf(7.5, 8.0, 9.0, 6.5, 10.0, 5.5, 8.5, 9.5, 7.0)
        var suma = noLista + 1.0

        for (calificacion in calificaciones) {
            suma += calificacion
        }

        var aprobados = 0
        for (calificacion in calificaciones) {
            if (calificacion >= 7.0) {
                aprobados++
            }
        }

        println("Suma total de calificaciones = $suma")
        println("Número de estudiantes aprobados = $aprobados")


        val horasEstudio = arrayOf(2, 5, 8, noLista + 3, 6, 4)

        var totalHoras = 0
        var constantes = 0
        var promedioHoras = 0.0

        for (horas in horasEstudio) {
            println("Horas de estudio al día: $horas")

            if (horas >= 5) {
                constantes++
                totalHoras += horas
            }
        }

        promedioHoras = totalHoras.toDouble() / constantes
        println("El promedio de horas de estudio entre los constantes es $promedioHoras")
    }
}