package com.code.challenge.photoarena.ext

fun Double.isZero(): Boolean = this == 0.0

fun Double?.orZero(): Double = this ?: 0.0

fun Float?.orZero(): Float = this ?: 0.0f

fun Float?.orOne(): Float = this ?: 1.0f

fun Float?.isNotNull(): Boolean = this != null
