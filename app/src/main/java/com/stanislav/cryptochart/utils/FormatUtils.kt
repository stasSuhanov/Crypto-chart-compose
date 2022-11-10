package com.stanislav.cryptochart.utils

fun Double.roundTo(n: Int): Double {
    return "%.${n}f".format(this).toDouble()
}