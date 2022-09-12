package com.gildedrose;

public class Rule {

    private final Integer highLimit;
    private final Integer lowLimit;
    private final Integer degradationValue;

    public Rule(Integer highLimit, Integer lowLimit, Integer degradationValue) {
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.degradationValue = degradationValue;
    }


    boolean isInInterval(int sellIn) {
        if (highLimit == null) {
            if (lowLimit == null) {
                return true;
            }
            return sellIn > lowLimit;
        }
        if (lowLimit == null) {
            return highLimit >= sellIn;
        }
        return highLimit >= sellIn && sellIn > lowLimit;

    }

    public Integer getDegradationValue() {
        return degradationValue;
    }


}
