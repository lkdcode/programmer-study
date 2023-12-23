package com.practice.study4;

class Sword extends Weapon {

    private static final String NAME = "칼";
    private static final int DAMAGE = 10;

    @Override
    String getWeaponName() {
        return NAME;
    }

    @Override
    int getWeaponDamage() {
        return DAMAGE;
    }

}
