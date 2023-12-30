package com.level5;


class Rule {

    private final Condition condition;
    private final Action action;

    public Rule(Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
    }

    public void perform(final Facts facts) {
        if (condition.evaluate(facts)) {
            action.execute(facts);
        }
    }

}
