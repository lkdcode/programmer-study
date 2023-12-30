package com.practice.study4;

class Bow extends Weapon {

    private static final String NAME = "활";
    private static final int DAMAGE = 100;

    @Override
    String getWeaponName() {
        return NAME;
    }

    @Override
    int getWeaponDamage() {
        return DAMAGE;
    }

}
