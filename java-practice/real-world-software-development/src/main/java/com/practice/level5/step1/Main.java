package com.practice.level5.step1;

import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String[] args) {
        People people1 = new People("김기자", 87, "제주도");
        People people2 = new People("홍길동", 25, "서울시");
        People people3 = new People("이순신", 17, "강원도");
        People people4 = new People("임꺽정", 9, "강원도");
        People people5 = new People("박수박", 44, "경상도");
        People people6 = new People("사오정", 29, "경기도");
        People people7 = new People("손오공", 41, "경기도");
        People people8 = new People("저팔계", 57, "서울시");
        People people9 = new People("삼겹살", 24, "전라도");
        People people10 = new People("마수리", 39, "강화도"); // 팩트

        List<People> peopleList = new ArrayList<>();
        peopleList.add(people1);
        peopleList.add(people2);
        peopleList.add(people3);
        peopleList.add(people4);
        peopleList.add(people5);
        peopleList.add(people6);
        peopleList.add(people7);
        peopleList.add(people8);
        peopleList.add(people9);
        peopleList.add(people10);

        peopleList.stream()
                .filter(people -> people.getAge() >= 20) // 조건
                .forEach(people -> System.out.println("저는 성인 입니다. : " + people.getName())); // 액션
    }

}
