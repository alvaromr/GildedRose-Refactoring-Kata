package com.gildedrose

import com.gildedrose.updater.*

class GildedRose(private var items: Array<Item>) {
    fun updateQuality() {
        items.forEach(::update)
    }

    private fun update(item: Item) {
        item.updater.update()
    }

    private val Item.updater
        get() = when (name) {
            "Sulfuras, Hand of Ragnaros" ->
                SulfurasItemUpdater(this)

            "Aged Brie" ->
                AgedBrieItemUpdater(this)

            "Backstage passes to a TAFKAL80ETC concert" ->
                BackstagePassesItemUpdater(this)

            "Conjured Mana Cake" ->
                ConjuredItemUpdater(this)

            else ->
                ItemUpdater(this)
        }

}