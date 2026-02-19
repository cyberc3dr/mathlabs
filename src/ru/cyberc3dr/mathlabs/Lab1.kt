package ru.cyberc3dr.mathlabs

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

object Lab1 {

    private val epsilon = 0.001 // Точность вычислений (критерий остановки)

    private val h = 0.1          // Шаг для локализации корня
    private val x0 = 0.0         // Начало отрезка поиска
    private val x1 = 1.0         // Конец отрезка поиска (используется в локализации)

    // Определение функции f(x)
    fun f(x: Double) = acos(x) - sqrt(1 - 0.3 * x.pow(3))

    // Производная f'(x) для метода Ньютона
    fun fp(x: Double) = -1 / sqrt(1 - x.pow(2)) + (0.45 * x.pow(2)) / sqrt(1 - 0.3 * x.pow(3))

    // Эквивалентная функция x = fi(x) для метода простых итераций
    fun fi(x: Double) = cos(sqrt(1 - 0.3 * x.pow(3)))

    @JvmStatic
    fun main(args: Array<String>) {
        println("Выполним шаговый метод локализации корня.")

        var a = x0
        var b = a + h

        // Локализуем корень: ищем смену знака f(a)*f(b) <= 0
        while(f(a) * f(b) > 0) {
            a = b
            b += h
        }

        println("Получаем интервал: [$a, $b]")

        method1(a, b)
        method2(a, b)
        method3(a, b)
        method4(a, b)
    }

    // Метод половинного деления (бисекции)
    fun method1(start: Double, end: Double) {
        println("\nМетод 1: половинное деление")

        var a = start
        var b = end

        var x = (a + b) / 2
        var iterations = 0

        while(abs(f(x)) > epsilon) {
            iterations++
            println("Итерация $iterations: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")

            // Сужаем отрезок, сохраняя смену знака
            if(f(a) * f(x) < 0) {
                b = x
            } else {
                a = x
            }

            x = (a + b) / 2
        }

        println("Итерация ${iterations + 1}: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")
        println("Приближенное значение корня: ${"%.3f".format(x)}")
        println("Кол-во приближений: ${iterations + 1}")
    }

    // Метод Ньютона
    fun method2(a: Double, b: Double) {
        println("\nМетод 2: Ньютона")

        var x = b
        var xnext = x - f(x) / fp(x)
        var iterations = 0

        while(abs(xnext - x) > epsilon) {
            iterations++
            println("Итерация $iterations: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")

            x = xnext
            xnext = x - f(x) / fp(x)
        }

        println("Итерация ${iterations + 1}: x=${"%.6f".format(xnext)}, f(x)=${"%.6f".format(f(xnext))}")
        println("Приближенное значение корня: ${"%.3f".format(xnext)}")
        println("Кол-во приближений: ${iterations + 1}")
    }

    // Метод простых итераций
    fun method3(a: Double, b: Double) {
        println("\nМетод 3: простых итераций")

        var x = a
        var xnext = fi(x)
        var iterations = 0

        while(abs(xnext - x) > epsilon) {
            iterations++
            println("Итерация $iterations: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")

            x = xnext
            xnext = fi(x)
        }

        println("Итерация ${iterations + 1}: x=${"%.6f".format(xnext)}, f(x)=${"%.6f".format(f(xnext))}")
        println("Приближенное значение корня: ${"%.3f".format(xnext)}")
        println("Кол-во приближений: ${iterations + 1}")
    }

    // Метод секущих (хорд)
    fun method4(a: Double, b: Double) {
        println("\nМетод 4: секущих")

        var x = a
        var xnext = x - f(x) / (f(x) - f(b)) * (x - b)
        var iterations = 0

        while(abs(xnext - x) > epsilon) {
            iterations++
            println("Итерация $iterations: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")

            x = xnext
            xnext = x - f(x) / (f(x) - f(b)) * (x - b)
        }

        println("Итерация ${iterations + 1}: x=${"%.6f".format(xnext)}, f(x)=${"%.6f".format(f(xnext))}")
        println("Приближенное значение корня: ${"%.3f".format(xnext)}")
        println("Кол-во приближений: ${iterations + 1}")
    }
}