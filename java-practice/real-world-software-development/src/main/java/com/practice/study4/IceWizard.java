package com.practice.study4;

class IceWizard implements Clazz {

    private static final String CLAZZ_NAME = "얼음 마법사";
    private static final int NORMAL_DAMAGE = 30;
    private static final int AGE = 10;
    private Weapon weapon;

    public IceWizard() {
        this.weapon = new IceWand();
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
