package com.study.chap02.step2;

class Main {
    public static void main(String[] args) {
        AppleList appleList = new AppleList(
                new Apple(new Weight(150), Color.RED)
                , new Apple(new Weight(150), Color.BLACK)
                , new Apple(new Weight(124), Color.BLACK)
                , new Apple(new Weight(2357), Color.YELLOW)
                , new Apple(new Weight(1247), Color.BLUE)
                , new Apple(new Weight(184), Color.PURPLE)
                , new Apple(new Weight(812), Color.WHITE)
                , new Apple(new Weight(94), Color.WHITE)
                , new Apple(new Weight(512), Color.RED)
        );

        AppleIterator appleIterator = new AppleIterator(appleList.getAppleList());

        AppleRule appleRule = new AppleRule(
                apple -> apple.getColor() == Color.RED
                , System.out::println
        );

        AppleEngine appleEngine = new AppleEngine(appleIterator, appleRule);

        appleEngine.run();
    }
}
