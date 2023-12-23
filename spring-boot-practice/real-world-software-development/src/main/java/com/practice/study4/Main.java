package com.practice.study4;

class Main {

    public static void main(String[] args) {
        Clazz clazz = new Warrior();

        clazz.attack(e -> new ClazzInformation(e.getClazzName(), e.getNormalDamage()));


        // 출력 : '전사'(이)가 칼(으)로 공격합니다.
        // 출력 : 데미지 : '전사' 기본공격력('10') * 칼 공격력(20) => 200

        System.out.println();
        clazz.swapWeapon(new Bow());
        clazz.attack(e -> new ClazzInformation(e.getClazzName(), e.getNormalDamage()));
        //
        // 출력 : '전사'(이)가 활(으)로 공격합니다.
        // 출력 : 데미지 : 전사 기본공격력(10) * 활 공격력(100) => 1000

        System.out.println();
        clazz = new Wizard();
        clazz.attack(e -> new ClazzInformation(e.getClazzName(), e.getNormalDamage()));

        // 출력 : '마법사'(이)가 불지팡이(으)로 공격합니다.
        // 출력 : 데미지 : 마법사 기본공격력(5) * 불지팡이 공격력(50) => 250
        System.out.println();
        clazz = new IceWizard();
        clazz.attack(e -> new ClazzInformation(e.getClazzName(), e.getNormalDamage()));
        // 출력 : '마법사'(이)가 얼음지팡이(으)로 공격합니다.
        // 출력 : 데미지 : '마법사' 기본공격력('5') * 얼음지팡이 공격력(60) => 300

    }

}
