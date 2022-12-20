package com.gildedrose

open class Item(
    var name: String,
    var sellIn: Int,
    var quality: Int
) {
    override fun toString() = "$name, $sellIn, $quality"
}

fun Item.decreaseSellIn() {
    sellIn -= 1
}

fun Item.decreaseQuality() {
    if (quality > 0) {
        quality -= 1
    }
}

fun Item.increaseQuality() {
    if (quality < 50) {
        quality += 1
    }
}