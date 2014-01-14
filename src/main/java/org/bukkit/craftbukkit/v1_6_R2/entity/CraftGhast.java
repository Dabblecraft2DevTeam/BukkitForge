package org.bukkit.craftbukkit.v1_6_R2.entity;

import net.minecraft.entity.monster.EntityGhast;

import org.bukkit.craftbukkit.v1_6_R2.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
//import org.bukkit.craftbukkit.v1_6_R2.CraftServer;

public class CraftGhast extends CraftFlying implements Ghast {

    public CraftGhast(CraftServer server, EntityGhast entity) {
        super(server, entity);
    }

    @Override
    public EntityGhast getHandle() {
        return (EntityGhast) entity;
    }

    @Override
    public String toString() {
        return "CraftGhast";
    }

    public EntityType getType() {
        return EntityType.GHAST;
    }
}
