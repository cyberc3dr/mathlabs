package ru.cyberc3dr.mathlabs

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object Lab1 {

    private val epsilon = 0.001   // Точность вычислений (критерий остановки)

    private val h = 0.01          // Шаг для локализации корня
    private val x0 = 0.0          // Начало отрезка поиска
    private val x1 = 1.0          // Конец отрезка поиска (используется в локализации)

    // Определение функции f(x)
    fun f(x: Double) = acos(x) - sqrt(1 - 0.3 * x.pow(3))

    // Производная f'(x) для метода Ньютона
    fun fp(x: Double) = -1 / sqrt(1 - x.pow(2)) + (0.45 * x.pow(2)) / sqrt(1 - 0.3 * x.pow(3))

    // Эквивалентная функция x = fi(x) для метода простых итераций
    fun fi(x: Double) = cos(sqrt(1 - 0.3 * x.pow(3)))

    // Производная fi'(x) для проверки условия сходимости метода простых итераций
    fun fip(x: Double) = -sin(sqrt(1 - 0.3 * x.pow(3))) * (0.45 * x.pow(2)) / sqrt(1 - 0.3 * x.pow(3))

    @JvmStatic
    fun main(args: Array<String>) {
        println("Выполним шаговый метод локализации корня.")
        println("Интервал поиска: [$x0, $x1], шаг: $h")

        var a = x0
        var b = a + h

        // Локализуем корень: ищем смену знака f(a)*f(b) <= 0
        while(f(a) * f(b) > 0) {
            a = b
            b += h
        }

        println("Получаем интервал: [${"%.2f".format(a)}, ${"%.2f".format(b)}]")

        bisectionMethod(a, b)
        newtonMethod(b)
        simpleIterationMethod(a, b)
        secantMethodRight(a, b)
        secantMethodLeft(a, b)
    }

    // Метод половинного деления
    fun bisectionMethod(start: Double, end: Double) {
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
    fun newtonMethod(end: Double) {
        println("\nМетод 2: Ньютона")

        var x = end
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
    fun simpleIterationMethod(start: Double, end: Double) {
        println("\nМетод 3: Простых итераций")

        println("Проверим условие сходимости")

        // Проверка условия сходимости
        if(abs(fip(start)) < 1 && abs(fip(end)) < 1) {
            println("Условие сходимости выполняется, можно применять метод простых итераций.")
        } else {
            println("Условие сходимости не выполняется.")
            return
        }

        var x = start
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

    // Метод секущих (фиксированный правый конец)
    fun secantMethodRight(start: Double, end: Double) {
        println("\nМетод 4: Хорд (секущих) - фиксированный правый конец")

        var x = start
        var xnext = x - f(x) / (f(x) - f(end)) * (x - end)
        var iterations = 0

        while(abs(xnext - x) > epsilon) {
            iterations++
            println("Итерация $iterations: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")

            x = xnext
            xnext = x - f(x) / (f(x) - f(end)) * (x - end)
        }

        println("Итерация ${iterations + 1}: x=${"%.6f".format(xnext)}, f(x)=${"%.6f".format(f(xnext))}")
        println("Приближенное значение корня: ${"%.3f".format(xnext)}")
        println("Кол-во приближений: ${iterations + 1}")
    }

    // Метод секущих (фиксированный левый конец)
    fun secantMethodLeft(start: Double, end: Double) {
        println("\nМетод 4: Хорд (секущих) - фиксированный левый конец")

        var x = start
        var xnext = x - f(x) / (f(x) - f(end)) * (x - end)
        var iterations = 0

        while(abs(xnext - x) > epsilon) {
            iterations++
            println("Итерация $iterations: x=${"%.6f".format(x)}, f(x)=${"%.6f".format(f(x))}")

            x = xnext
            xnext = x - f(x) / (f(x) - f(end)) * (x - end)
        }

        println("Итерация ${iterations + 1}: x=${"%.6f".format(xnext)}, f(x)=${"%.6f".format(f(xnext))}")
        println("Приближенное значение корня: ${"%.3f".format(xnext)}")
        println("Кол-во приближений: ${iterations + 1}")
    }
}