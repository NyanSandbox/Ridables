package net.pl3x.bukkit.ridables.entity;

import net.minecraft.server.v1_13_R2.ControllerLook;
import net.minecraft.server.v1_13_R2.ControllerMove;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EntityPolarBear;
import net.minecraft.server.v1_13_R2.GenericAttributes;
import net.minecraft.server.v1_13_R2.SoundEffects;
import net.minecraft.server.v1_13_R2.World;
import net.pl3x.bukkit.ridables.Ridables;
import net.pl3x.bukkit.ridables.configuration.Config;
import net.pl3x.bukkit.ridables.entity.controller.BlankLookController;
import net.pl3x.bukkit.ridables.entity.controller.ControllerWASD;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityRidablePolarBear extends EntityPolarBear implements RidableEntity {
    private ControllerMove aiController;
    private ControllerWASD wasdController;
    private ControllerLook defaultLookController;
    private BlankLookController blankLookController;
    private EntityPlayer rider;

    public EntityRidablePolarBear(World world) {
        super(world);
        aiController = moveController;
        wasdController = new ControllerWASD(this);
        defaultLookController = lookController;
        blankLookController = new BlankLookController(this);
        Q = Config.POLAR_BEAR_STEP_HEIGHT;
    }

    public RidableType getType() {
        return RidableType.POLAR_BEAR;
    }

    // canBeRiddenInWater
    public boolean aY() {
        return true;
    }

    protected void mobTick() {
        EntityPlayer rider = updateRider();
        if (rider != null) {
            Q = Config.POLAR_BEAR_STEP_HEIGHT;
            setGoalTarget(null, null, false);
            setRotation(rider.yaw, rider.pitch);
            useWASDController();
        }
        super.mobTick();
    }

    // getJumpUpwardsMotion
    protected float cG() {
        return super.cG() * getJumpPower() * 2.2F;
    }

    public void setRotation(float newYaw, float newPitch) {
        setYawPitch(lastYaw = yaw = newYaw, pitch = newPitch * 0.5F);
        aS = aQ = yaw;
    }

    public float getJumpPower() {
        return isStanding() ? 0 : Config.POLAR_BEAR_JUMP_POWER;
    }

    public float getSpeed() {
        return isStanding() ? 0 : (float) getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue() * Config.POLAR_BEAR_SPEED;
    }

    public EntityPlayer getRider() {
        return rider;
    }

    public EntityPlayer updateRider() {
        if (passengers == null || passengers.isEmpty()) {
            rider = null;
        } else {
            Entity entity = passengers.get(0);
            rider = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;
        }
        return rider;
    }

    public void useAIController() {
        if (moveController != aiController) {
            moveController = aiController;
            lookController = defaultLookController;
        }
    }

    public void useWASDController() {
        if (moveController != wasdController) {
            moveController = wasdController;
            lookController = blankLookController;
        }
    }

    public boolean onSpacebar() {
        if (Config.POLAR_BEAR_STAND && !isStanding()) {
            EntityPlayer rider = getRider();
            if (rider != null && rider.bj == 0 && rider.bh == 0) {
                setStanding(true);
                a(SoundEffects.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.0F);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        setStanding(false);
                    }
                }.runTaskLater(Ridables.getInstance(), 20);
                return true;
            }
        }
        return false;
    }

    private boolean isStanding() {
        return dz();
    }

    private void setStanding(boolean standing) {
        s(standing);
    }
}
