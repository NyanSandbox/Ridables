package net.pl3x.bukkit.ridables.entity.controller;

import net.minecraft.server.v1_13_R1.EntityInsentient;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.pl3x.bukkit.ridables.configuration.Config;
import net.pl3x.bukkit.ridables.util.ReflectionUtil;

public class ControllerWASDFlyingWithSpacebar extends ControllerWASD {
    public ControllerWASDFlyingWithSpacebar(EntityInsentient entity) {
        super(entity);
    }

    // onUpdate
    public void a() {
        EntityPlayer rider = ridable.getRider();
        if (rider == null) {
            ridable.useAIController();
            return;
        }

        // do not target anything while being ridden
        a.setGoalTarget(null, null, false);

        // eject rider if in water or lava
        if (a.isInWater() || a.ax()) {
            a.ejectPassengers();
            rider.stopRiding();
            return;
        }

        // rotation
        ridable.setRotation(rider.yaw, rider.pitch);

        // controls
        float forward = rider.bj * 0.5F;
        float strafe = rider.bh * 0.25F;
        float vertical = 0;
        if (forward <= 0.0F) {
            forward *= 0.5F;
        }

        float speed = ridable.getSpeed();

        a.setNoGravity(ReflectionUtil.isJumping(rider));
        if (a.isNoGravity()) {
            vertical = speed;
        }

        if (a.locY >= Config.FLYING_MAX_Y) {
            a.motY = -0.2;
            vertical = -speed;
            forward = 0;
            strafe = 0;
        }

        a.o(speed);
        a.s(vertical);

        speed = (a.onGround ? 0.5F : 2) * speed;

        a.t(strafe * speed);
        a.r(forward * speed);

        f = a.bj;
        g = a.bh;

        if (a.motY > 0.2) {
            a.motY = 0.2;
        }
    }
}