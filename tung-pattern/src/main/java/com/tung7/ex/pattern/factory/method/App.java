package com.tung7.ex.pattern.factory.method;

import com.tung7.ex.pattern.factory.method.sth.Weapon;

/**
 * Created by tung on 17-12-24.
 */
public class App {

    public static void main(String[] args) {
        Blacksmith blacksmith = new ElfBlacksmith();
        Weapon weaponAxe = blacksmith.manufactureWeapon(WeaponType.AXE);
        Weapon weaponSpear = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    }
}
