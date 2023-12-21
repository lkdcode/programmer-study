package com.level5;

class RuleBuilder {
    private final Condition condition;

    private RuleBuilder(Condition condition) {
        this.condition = condition;
    }

    public static RuleBuilder when(final Condition condition) {
        return new RuleBuilder(condition);
    }

    public Rule then(final Action action) {
        return new Rule(condition, action);
    }

}
