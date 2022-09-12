package com.gildedrose;

import java.util.List;

public class QualityMatrix {

    private boolean expire = false;

    private final List<Rule> rules;


    public QualityMatrix(boolean expire, List<Rule> rules) {
        this.expire = expire;
        this.rules = rules;
    }

    public QualityMatrix(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean isExpire() {
        return expire;
    }


    public List<Rule> getRules() {
        return rules;
    }

}
