package com.tung7.ex.pattern.factory.method;

import com.tung7.ex.pattern.factory.method.sth.ElfWeapon;
import com.tung7.ex.pattern.factory.method.sth.Weapon;

/**
 * Created by tung on 17-12-24.
 */
public class ElfBlacksmith implements Blacksmith {
    @Override
    public Weapon manufactureWeapon(WeaponType weaponType) {
        return new ElfWeapon(weaponType);
    }
}
