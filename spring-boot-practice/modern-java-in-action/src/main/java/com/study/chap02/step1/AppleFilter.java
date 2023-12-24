package com.study.chap02.step1;

class AppleFilter {

    public AppleList test(AppleList appleList, CustomPredicate<Apple> predicate) {
        AppleList filteredList = new AppleList();
        appleList.getAppleList().stream()
                .filter(predicate::test)
                .forEach(filteredList::addApple);

        return filteredList;
    }
}
