package com.level5;

interface ConditionalAction {

    void perform(Facts facts);

    boolean evaluate(Facts facts);

}
