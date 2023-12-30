package com.practice.study4;

class IceWand extends Weapon {

    private static final String NAME = "얼음 지팡이";
    private static final int DAMAGE = 500;

    @Override
    String getWeaponName() {
        return NAME;
    }

    @Override
    int getWeaponDamage() {
        return DAMAGE;
    }

}
