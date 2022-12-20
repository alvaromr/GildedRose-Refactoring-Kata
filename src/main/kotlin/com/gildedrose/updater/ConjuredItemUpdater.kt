package com.gildedrose.updater

import com.gildedrose.Item

class ConjuredItemUpdater(item: Item) : ItemUpdater(item) {
    override fun updateQuality() {
        repeat(2) {
            super.updateQuality()
        }
    }
}