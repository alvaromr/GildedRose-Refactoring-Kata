package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

internal class GildedRoseTest {
    @Test
    fun `ordinary item sellIn decreases by one`() {
        val item = createTestItem()
        val app = GildedRose(item)
        val initialSellIn = item.sellIn
        val days = 100.randomize()
        app.advanceTimeBy(days)
        assertEquals(initialSellIn - days, item.sellIn)
    }

    @Test
    fun `Sulfuras sellIn never decreases`() {
        val item = createTestItem("Sulfuras, Hand of Ragnaros")
        val app = GildedRose(item)
        val initialSellIn = item.sellIn
        val days = 100.randomize()
        app.advanceTimeBy(days)
        assertEquals(initialSellIn, item.sellIn)
    }

    @Test
    fun `ordinary item quality decreases by one before its sellIn`() {
        val initialSellIn = 10
        val item = createTestItem(
            sellIn = initialSellIn,
            quality = 10 * initialSellIn
        )
        val app = GildedRose(item)
        val initialQuality = item.quality
        val days = initialSellIn.randomize()
        app.advanceTimeBy(days)
        assertEquals(initialQuality - days, item.quality)
    }

    @Test
    fun `ordinary item quality decreases by two after its sellIn`() {
        val initialSellIn = 10
        val item = createTestItem(
            sellIn = initialSellIn,
            quality = 10 * initialSellIn
        )
        val app = GildedRose(item)
        val initialQuality = item.quality
        app.advanceTimeBy(initialSellIn)
        val qualityAfterSellIn = item.quality
        assertEquals(initialQuality - initialSellIn, qualityAfterSellIn)
        val days = initialSellIn.randomize()
        app.advanceTimeBy(days)
        assertEquals(qualityAfterSellIn - 2 * days, item.quality)
    }

    @Test
    fun `the quality of an item is never negative`() {
        val item = createTestItem(
            quality = 50
        )
        val app = GildedRose(item)
        val days = (item.quality * 1000)
        app.advanceTimeBy(days)
        assertTrue(0 >= item.quality)
    }

    @Test
    fun `Sulfuras quality never decreases`() {
        val item = createTestItem(
            name = "Sulfuras, Hand of Ragnaros",
            quality = 80
        )
        val app = GildedRose(item)
        val initialQuality = item.quality
        val days = 100.randomize()
        app.advanceTimeBy(days)
        assertEquals(initialQuality, item.quality)
    }

    @Test
    fun `Aged Brie quality increases by one before sellIn`() {
        val initialSellIn = 3
        val initialQuality = 50 / 2
        val item = createTestItem(
            name = "Aged Brie",
            sellIn = initialSellIn,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(initialSellIn)
        assertEquals(initialQuality + initialSellIn, item.quality)
    }

    @Test
    fun `Aged Brie quality increases by two after sellIn`() {
        val initialSellIn = 3
        val initialQuality = 0
        val item = createTestItem(
            name = "Aged Brie",
            sellIn = initialSellIn,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(initialSellIn)
        val qualityAfterSellIn = item.quality
        assertEquals(initialQuality + initialSellIn, qualityAfterSellIn)
        app.advanceTimeBy(initialSellIn)
        assertEquals(qualityAfterSellIn + 2 * initialSellIn, item.quality)
    }

    @Test
    fun `Aged Brie quality never reaches max quality`() {
        val initialSellIn = 3
        val initialQuality = 50 - initialSellIn
        val item = createTestItem(
            name = "Aged Brie",
            sellIn = initialSellIn,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(50)
        assertEquals(50, item.quality)
    }

    @Test
    fun `Backstage passes quality increases by one before first threshold`() {
        val days = 2
        val initialSellIn = 10 + 5 + days
        val initialQuality = 7
        val item = createTestItem(
            name = "Backstage passes to a TAFKAL80ETC concert",
            sellIn = initialSellIn,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(days)
        assertEquals(initialQuality + days, item.quality)
    }

    @Test
    fun `Backstage passes quality increases by two after first threshold and before second`() {
        val days = 2
        val initialSellIn = 5 + days
        val initialQuality = 7
        val item = createTestItem(
            name = "Backstage passes to a TAFKAL80ETC concert",
            sellIn = initialSellIn,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(days)
        assertEquals(initialQuality + 2 * days, item.quality)
    }

    @Test
    fun `Backstage passes quality increases by three after second threshold and before sellIn`() {
        val days = 2
        val initialQuality = 7
        val item = createTestItem(
            name = "Backstage passes to a TAFKAL80ETC concert",
            sellIn = days,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(days)
        assertEquals(initialQuality + 3 * days, item.quality)
    }

    @Test
    fun `Backstage passes quality resets to zero after sellIn`() {
        val days = 2
        val initialQuality = 7
        val item = createTestItem(
            name = "Backstage passes to a TAFKAL80ETC concert",
            sellIn = days,
            quality = initialQuality
        )
        val app = GildedRose(item)
        app.advanceTimeBy(days + 1)
        assertEquals(0, item.quality)
    }

    @ParameterizedTest(name = "[{index}] {arguments}")
    @CsvFileSource(
        resources = [FixturesFile],
        useHeadersInDisplayName = true,
        lineSeparator = NewLineDelimiter,
        delimiter = Delimiter
    )
    fun `test fixtures`(
        days: Int,
        name: String,
        initialSellIn: Int,
        initialQuality: Int,
        finalSellIn: Int,
        finalQuality: Int
    ) {
        val item = createTestItem(name, initialSellIn, initialQuality)
        val app = GildedRose(item)
        app.advanceTimeBy(days)

        assertEquals(finalSellIn, item.sellIn)
        assertEquals(finalQuality, item.quality)
    }

    //TODO: add missing tests for Conjured Items

    @Test
    fun `conjured item quality decreases by two before its sellIn`() {
        val initialSellIn = 10
        val item = createTestItem(
            name = "Conjured Mana Cake",
            sellIn = initialSellIn,
            quality = 10 * initialSellIn
        )
        val app = GildedRose(item)
        val initialQuality = item.quality
        val days = initialSellIn.randomize()
        app.advanceTimeBy(days)
        assertEquals(initialQuality - 2 * days, item.quality)
    }

    @Test
    fun `conjured item quality decreases by four after its sellIn`() {
        val initialSellIn = 1
        val item = createTestItem(
            name = "Conjured Mana Cake",
            sellIn = initialSellIn,
            quality = 40 * initialSellIn
        )
        val app = GildedRose(item)
        val initialQuality = item.quality
        app.advanceTimeBy(initialSellIn)
        val qualityAfterSellIn = item.quality
        assertEquals(initialQuality - 2 * initialSellIn, qualityAfterSellIn)
        val days = initialSellIn.randomize()
        app.advanceTimeBy(days)
        assertEquals(qualityAfterSellIn - 4 * days, item.quality)
    }
}