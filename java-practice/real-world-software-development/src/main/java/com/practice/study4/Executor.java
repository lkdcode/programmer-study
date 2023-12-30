package com.practice.study4;

class Executor implements Behavior {
    @Override
    public void run(ClazzInformation information, String name, int damage) {
        final String clazzName = information.clazzName();
        final int clazzNormalDamage = information.clazzNormalDamage();

        System.out.println(clazzName + "(이)가 " + name + "(으)로 공격합니다.");
        System.out.println("데미지 : "
                + clazzName
                + " 기본공격력(" + clazzNormalDamage + ") * "
                + name + " 공격력(" + damage
                + ") => " + (clazzNormalDamage * damage));
    }
}
