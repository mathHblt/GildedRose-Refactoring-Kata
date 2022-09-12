package com.gildedrose;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

class GildedRose {

    private static final int MAX_LIMIT_QUALITY = 50;
    private static final int MIN_LIMIT_QUALITY = 0;

    final Item[] items;

    /**
     * It is assumed the map was validated upfront (via bean validation or other mechanism) to avoid
     * any collision between the rules interval;
     * <p>
     * <p>
     * I decided to use a map of rules to represent the degradation of each item through times
     * to ease the addition of new items.
     * <p>
     * The data from the map could be provided through a CSV file or a database.
     * <p>
     * I thought about using dedicated update "Strategy" classes for each category of item,
     * but it would make the business highly coupled to the code which does not seem
     * suitable in a "real" world scenario.
     */
    private final Map<String, QualityMatrix> qualityMatrixMap;

    public GildedRose(Item[] items, Map<String, QualityMatrix> qualityMatrixMap) {
        this.items = items;
        this.qualityMatrixMap = qualityMatrixMap;

    }

    public void updateQuality() {
        asList(items).forEach(item -> {
            if (qualityMatrixMap.containsKey(item.name)) {

                QualityMatrix qualityMatrix = qualityMatrixMap.get(item.name);
                if (qualityMatrix.isExpire() && item.sellIn == 0) {
                    item.quality = MIN_LIMIT_QUALITY;
                } else {
                    List<Rule> rules = qualityMatrix.getRules();
                    rules.stream()
                        .filter(r -> r.isInInterval(item.sellIn))
                        .findAny().ifPresent(rule -> applyDegradation(item, rule.getDegradationValue()));
                }


                item.sellIn--;
            }
        });
    }


    private static void applyDegradation(Item item, int degradation) {
        if (degradation > 0) {
            item.quality = Math.min((item.quality + degradation), MAX_LIMIT_QUALITY);
        } else {
            item.quality = Math.max((item.quality + degradation), MIN_LIMIT_QUALITY);
        }

    }


}
