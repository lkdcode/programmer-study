package com.practice.study4;

class Warrior implements Clazz {

    private static final String CLAZZ_NAME = "전사";
    private static final int NORMAL_DAMAGE = 10;
    private static final int AGE = 30;
    private Weapon weapon;

    public Warrior() {
        this.weapon = new Sword();
    }

    @Override
    public void attack(MakeClazzInformation makeClazzInformation) {
        this.weapon.attack(makeClazzInformation.make(this), new Executor());
    }

    @Override
    public void swapWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public String getClazzName() {
        return CLAZZ_NAME;
    }

    @Override
    public int getNormalDamage() {
        return NORMAL_DAMAGE;
    }

    @Override
    public int getAge() {
        return AGE;
    }

}
