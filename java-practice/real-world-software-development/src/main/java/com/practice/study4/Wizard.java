package com.practice.study4;

class Wizard implements Clazz {

    private static final String CLAZZ_NAME = "불 마법사";
    private static final int NORMAL_DAMAGE = 30;
    private static final int AGE = 23;
    private Weapon weapon;

    public Wizard() {
        this.weapon = new FireWand();
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
