package net.pl3x.bukkit.ridables.entity.ai.goal.dolphin;

import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.MobEffect;
import net.minecraft.server.v1_13_R2.MobEffects;
import net.minecraft.server.v1_13_R2.PathfinderGoal;
import net.pl3x.bukkit.ridables.entity.animal.RidableDolphin;

public class AIDolphinSwimWithPlayer extends PathfinderGoal {
    private final RidableDolphin dolphin;
    private final double speed;
    private EntityHuman targetPlayer;

    public AIDolphinSwimWithPlayer(RidableDolphin dolphin, double speed) {
        this.dolphin = dolphin;
        this.speed = speed;
        a(3); // setMutexBits
    }

    // shouldExecute
    @Override
    public boolean a() {
        if (dolphin.getRider() != null) {
            return false;
        }
        targetPlayer = dolphin.world.findNearbyPlayer(dolphin, 10.0D);
        return targetPlayer != null && targetPlayer.isSwimming();
    }

    // shouldContinueExecuting
    @Override
    public boolean b() {
        return dolphin.getRider() == null && targetPlayer != null && targetPlayer.isSwimming() && dolphin.h(targetPlayer) < 256.0D;
    }

    // startExecuting
    @Override
    public void c() {
        targetPlayer.addEffect(new MobEffect(MobEffects.DOLPHINS_GRACE, 100), org.bukkit.event.entity.EntityPotionEffectEvent.Cause.DOLPHIN);
    }

    // resetTask
    @Override
    public void d() {
        targetPlayer = null;
        dolphin.getNavigation().q(); // clearPath
    }

    // tick
    @Override
    public void e() {
        dolphin.getControllerLook().a(targetPlayer, (float) (dolphin.L() + 20), (float) dolphin.K()); // setLookPositionWithEntity
        if (dolphin.h(targetPlayer) < 6.25D) {
            dolphin.getNavigation().q(); // clearPath
        } else {
            dolphin.getNavigation().a(targetPlayer, speed); // tryMovingToEntityLiving
        }

        if (targetPlayer.isSwimming() && targetPlayer.world.random.nextInt(6) == 0) {
            targetPlayer.addEffect(new MobEffect(MobEffects.DOLPHINS_GRACE, 100), org.bukkit.event.entity.EntityPotionEffectEvent.Cause.DOLPHIN);
        }

    }
}
