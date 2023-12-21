package com.level5;

class Diagnosis {

    private final Facts facts;
    private final ConditionalAction conditionalAction;
    private final boolean isPositive;

    public Diagnosis(Facts facts, ConditionalAction conditionalAction, boolean isPositive) {
        this.facts = facts;
        this.conditionalAction = conditionalAction;
        this.isPositive = isPositive;
    }

    public Facts getFacts() {
        return facts;
    }

    public ConditionalAction getConditionalAction() {
        return conditionalAction;
    }

    public boolean isPositive() {
        return isPositive;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "facts=" + facts +
                ", conditionalAction=" + conditionalAction +
                ", conditionResult=" + isPositive +
                '}';
    }
}
