package com.tung7.ex.pattern.factory.method.sth;

import com.tung7.ex.pattern.factory.method.WeaponType;

/**
 * Created by tung on 17-12-24.
 */
public class OrcWeapon implements Weapon {
    private String name;

    public OrcWeapon(WeaponType type) {
        name = type.name();
    }
}
