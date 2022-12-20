package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.decreaseQuality
import com.gildedrose.decreaseSellIn

open class ItemUpdater(val item: Item) {
    fun update() {
        updateSellIn()
        updateQuality()
    }

    protected open fun updateSellIn() {
        item.decreaseSellIn()
    }

    protected open fun updateQuality() {
        item.apply {
            decreaseQuality()
            if (sellIn < 0) {
                decreaseQuality()
            }
        }
    }
}