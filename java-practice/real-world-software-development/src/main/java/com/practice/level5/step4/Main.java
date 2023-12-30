package com.practice.level5.step4;

class Main {
    public static void main(String[] args) {
        People people1 = new People("김기자", 87, "제주도");
        People people2 = new People("홍길동", 25, "서울시");
        People people3 = new People("이순신", 37, "강원도");
        People people4 = new People("이꺽정", 59, "강원도");
        People people5 = new People("박수박", 44, "경상도");
        People people6 = new People("사오정", 29, "경기도");
        People people7 = new People("손오공", 41, "경기도");
        People people8 = new People("저팔계", 57, "서울시");
        People people9 = new People("삼겹살", 24, "전라도");
        People people10 = new People("마수리", 39, "강화도"); // 팩트

        PeopleList peopleList = new PeopleList(people1, people2, people3, people4, people5, people6, people7, people8, people9, people10);

        CustomPredicateList<People> customPredicateList = new CustomPredicateList<>();

        customPredicateList.addCustomPredicate(people -> people.getAddress().equals("강원도"));
        customPredicateList.addCustomPredicate(people -> people.getAge() >= 20);
        customPredicateList.addCustomPredicate(people -> people.getName().startsWith("이"));

        CustomConsumerList<People> customConsumerList = new CustomConsumerList<>();

        customConsumerList.addCustomConsumer(people -> System.out.println("이름 : " + people.getName()));
        customConsumerList.addCustomConsumer(people -> System.out.println("나이 : " + people.getAge()));
        customConsumerList.addCustomConsumer(people -> System.out.println("주소 : " + people.getAddress() + "\n"));

        peopleList.stream()
                .filter(people -> customPredicateList.getCustomPredicateList().stream().allMatch(predicate -> predicate.valid(people)))
                .forEach(people -> customConsumerList.getConsumerList().forEach(consumer -> consumer.execute(people)));
    }
}
