package com.natiqhaciyef.coffeshop.util

fun String.setFirstIndexUpper(): String{
    return "${this[0].uppercase()}${this.removeRange(0..0)}"
}