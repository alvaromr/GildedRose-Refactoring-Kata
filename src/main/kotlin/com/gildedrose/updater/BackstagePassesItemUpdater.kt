package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.increaseQuality

class BackstagePassesItemUpdater(item: Item) : ItemUpdater(item) {
    override fun updateQuality() {
        item.apply {
            increaseQuality()
            if (sellIn < 10) {
                increaseQuality()
            }
            if (sellIn < 5) {
                increaseQuality()
            }
            if (sellIn < 0) {
                quality = 0
            }
        }
    }
}