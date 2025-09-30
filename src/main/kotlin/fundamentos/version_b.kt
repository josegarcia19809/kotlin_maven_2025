package org.example.fundamentos

fun main() {
    for (noLista in 7..11) {
        println("-".repeat(50) + noLista)
        val ventas = arrayOf(5.00, 8.00, 10.00, 15.00, 20.50, 25.00, 14.00, 10.00, 1.00)
        var total: Double = noLista + 1.0

        for (venta in ventas) {
            total += venta
        }

        var ventas10oMas = 0
        for (venta in ventas) {
            if (venta >= 10) {
                ventas10oMas++
            }
        }

        println("Total = $total")
        println("Ventas mayores o iguales a 10 = $ventas10oMas")

        val cigarrosFumados = arrayOf(5, 12, 8, noLista * 3, 4, 9)

        var totalCigarros = 0.0
        var aprobados = 0.0
        var promedioCigarros = 0.0

        for (cigarros in cigarrosFumados) {
            // println("Cigarros fumados al día: $cigarros")

            if (cigarros < 10) {
                aprobados++
                totalCigarros += cigarros
            }
        }
        promedioCigarros = totalCigarros / aprobados
        println("El promedio de cigarros entre los aprobados es $promedioCigarros")
        println()
    }
}