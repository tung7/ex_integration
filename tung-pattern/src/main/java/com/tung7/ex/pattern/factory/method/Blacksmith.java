package com.tung7.ex.pattern.factory.method;

import com.tung7.ex.pattern.factory.method.sth.Weapon;

/**
 * Created by tung on 17-12-24.
 */
public interface Blacksmith {
    Weapon manufactureWeapon(WeaponType weaponType);
}