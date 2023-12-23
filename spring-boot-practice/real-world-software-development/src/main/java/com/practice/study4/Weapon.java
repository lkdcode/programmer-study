package com.practice.study4;

abstract class Weapon {

    public void attack(ClazzInformation information, final Behavior behavior) {
        behavior.run(information, getWeaponName(), getWeaponDamage());
    }

    abstract String getWeaponName();

    abstract int getWeaponDamage();

}
