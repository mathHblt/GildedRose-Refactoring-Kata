package com.gildedrose;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.asList;

public class TexttestFixture {


    private static Map<String, QualityMatrix> initQualityMatrixMap() {

        Map<String, QualityMatrix> rulesMap = new LinkedHashMap<>();

        rulesMap.put("+5 Dexterity Vest", new QualityMatrix(asList(
            new Rule(null, 0, -1),
            new Rule(0, null, -2)
        )));
        rulesMap.put("Aged Brie", new QualityMatrix(Collections.singletonList(
            new Rule(null, null, 1)
        )));
        rulesMap.put("Elixir of the Mongoose", new QualityMatrix(asList(
            new Rule(null, 0, -1),
            new Rule(0, null, -2))));
        rulesMap.put("Backstage passes to a TAFKAL80ETC concert",
            new QualityMatrix(true, asList(
                new Rule(null, 10, 1),
                new Rule(10, 5, 2),
                new Rule(5, null, 3)
            )));

        rulesMap.put("Conjured Mana Cake", new QualityMatrix(asList(
            new Rule(null, 0, -2),
            new Rule(0, null, -4))));


        return rulesMap;
    }

    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        Item[] items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};

        GildedRose app = new GildedRose(items, initQualityMatrixMap());

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
