package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {


    @Test
    void quality_not_below_min_quality_limit() {
        String itemName = "Conjured Mana Cake";
        Item[] items = new Item[]{
            new Item(itemName, 2, 1),

        };

        Map<String, QualityMatrix> map = new LinkedHashMap<>();

        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(null, 0, -2)
            )));


        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        Item item = app.items[0];
        assertEquals(itemName, item.name);
        assertEquals(1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void quality_not_above_max_quality_limit() {

        String itemName = "NotAboveMaxLimit";
        Item[] items = new Item[]{
            new Item(itemName, 5, 49),

        };

        Map<String, QualityMatrix> map = new LinkedHashMap<>();

        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(null, 0, 3)
            )));


        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, 4, 50, app.items[0]);


    }

    @Test
    void no_update_quality_or_sellIn_without_rule() {
        Item[] items = new Item[]{
            new Item("Sulfuras, Hand of Ragnaros", 5, 80),
        };
        Map<String, QualityMatrix> map = new LinkedHashMap<>();

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem("Sulfuras, Hand of Ragnaros", 5, 80, app.items[0]);
    }

    @Test
    void sellIn_in_upper_null_lower_null() {
        String itemName = "NoUpperNoLowerLimit";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(null, null, -1)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 100, 46),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, 99, 45, app.items[0]);
    }

    @Test
    void sellIn_in_upper_null_lower_not_null() {
        String itemName = "NoUpperLimitLowerLimit";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(null, 3, -3)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 5, 10),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, 4, 7, app.items[0]);
    }

    @Test
    void sellIn_in_upper_lower__null() {
        String itemName = "UpperLimitNoLowerLimit";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(10, null, -3)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 5, 10),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, 4, 7, app.items[0]);
    }

    @Test
    void sellIn_not_in_limits() {
        String itemName = "NotInLimits";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(null, 3, -3)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 3, 10),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, 2, 10, app.items[0]);
    }

    @Test
    void sellIn_in_limits() {
        String itemName = "InLimits";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            Collections.singletonList(new Rule(10, 3, -1)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 5, 10),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, 4, 9, app.items[0]);
    }


    @Test
    void expired_item() {
        String itemName = "ExpiredItem";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            true,
            Collections.singletonList(new Rule(10, 3, -1)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 0, 50),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();

        validateItem(itemName, -1, 0, app.items[0]);
    }

    @Test
    void multiple_rules_applied() {
        String itemName = "MultipleRules";

        Map<String, QualityMatrix> map = new LinkedHashMap<>();
        map.put(itemName, new QualityMatrix(
            true,
            asList(
                new Rule(9, 8, -1),
                new Rule(8, 7, -2)
            )));

        Item[] items = new Item[]{
            new Item(itemName, 9, 50),
        };

        GildedRose app = new GildedRose(items, map);
        app.updateQuality();
        app.updateQuality();

        validateItem(itemName, 7, 47, app.items[0]);
    }


    private void validateItem(String name, int sellIn, int quality, Item item) {
        assertEquals(name, item.name);
        assertEquals(sellIn, item.sellIn);
        assertEquals(quality, item.quality);
    }
}
