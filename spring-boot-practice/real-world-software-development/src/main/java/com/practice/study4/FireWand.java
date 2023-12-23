package com.practice.study4;

class FireWand extends Weapon {

    private static final String NAME = "불 지팡이";
    private static final int DAMAGE = 300;

    @Override
    String getWeaponName() {
        return NAME;
    }

    @Override
    int getWeaponDamage() {
        return DAMAGE;
    }

}
