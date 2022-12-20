package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.increaseQuality

class AgedBrieItemUpdater(item: Item) : ItemUpdater(item) {
    override fun updateQuality() {
        item.apply {
            increaseQuality()
            if (sellIn < 0) {
                increaseQuality()
            }
        }
    }
}