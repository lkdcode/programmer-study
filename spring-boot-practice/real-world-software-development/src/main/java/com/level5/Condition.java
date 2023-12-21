package com.level5;

@FunctionalInterface
interface Condition {

    boolean evaluate(Facts facts);

}
