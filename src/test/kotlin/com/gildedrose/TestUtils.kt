package com.gildedrose

fun IntRange.randomize() = shuffled().last()
fun Int.randomize() = (0..this).randomize()

val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
val randomName get() = List(10) { charPool.random() }.joinToString("")
val randomSellIn get() = 999.randomize()
val randomQuality get() = 50.randomize()

fun createTestItem(
    name: String = randomName,
    sellIn: Int = randomSellIn,
    quality: Int = randomQuality
): Item = Item(
    name = name,
    sellIn = sellIn,
    quality = quality
)

fun GildedRose.advanceTimeBy(days: Int) = repeat(days) {
    updateQuality()
}

fun GildedRose(item: Item) = GildedRose(arrayOf(item))