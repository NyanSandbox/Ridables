package net.pl3x.bukkit.ridables.entity;

import net.minecraft.server.v1_13_R1.ControllerLook;
import net.minecraft.server.v1_13_R1.ControllerMove;
import net.minecraft.server.v1_13_R1.Entity;
import net.minecraft.server.v1_13_R1.EntityBat;
import net.minecraft.server.v1_13_R1.EntityLiving;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.GenericAttributes;
import net.minecraft.server.v1_13_R1.World;
import net.pl3x.bukkit.ridables.configuration.Config;
import net.pl3x.bukkit.ridables.entity.controller.BlankLookController;
import net.pl3x.bukkit.ridables.entity.controller.ControllerWASDFlyingWithSpacebar;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class EntityRidableBat extends EntityBat implements RidableEntity {
    private ControllerMove aiController;
    private ControllerWASDFlyingWithSpacebar wasdController;
    private ControllerLook defaultLookController;
    private BlankLookController blankLookController;

    public EntityRidableBat(World world) {
        super(world);
        aiController = moveController;
        wasdController = new ControllerWASDFlyingWithSpacebar(this);
        defaultLookController = lookController;
        blankLookController = new BlankLookController(this);
    }

    public boolean isActionableItem(ItemStack itemstack) {
        return false;
    }

    public boolean aY() {
        return true; // dont eject passengers when in water
    }

    protected void mobTick() {
        EntityPlayer rider = getRider();
        if (rider != null) {
            super.setGoalTarget(null, null, false);
            setRotation(rider.yaw, rider.pitch);
            useWASDController();
            motY += bi > 0 ? 0.07F * Config.BAT_VERTICAL : 0.04704F - Config.BAT_GRAVITY;
            return;
        }
        super.mobTick();
    }

    public void setRotation(float newYaw, float newPitch) {
        setYawPitch(lastYaw = yaw = newYaw, pitch = newPitch * 0.5F);
        aS = aQ = yaw;
    }

    public float getSpeed() {
        return (float) getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue() * Config.BAT_SPEED;
    }

    public EntityPlayer getRider() {
        if (passengers != null && !passengers.isEmpty()) {
            Entity entity = passengers.get(0);
            if (entity instanceof EntityPlayer) {
                return (EntityPlayer) entity;
            }
        }
        return null;
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

    public void setGoalTarget(@Nullable EntityLiving entityliving) {
        setGoalTarget(entityliving, EntityTargetEvent.TargetReason.UNKNOWN, true);
    }

    public boolean setGoalTarget(EntityLiving entityliving, EntityTargetEvent.TargetReason reason, boolean fireEvent) {
        return getRider() != null && super.setGoalTarget(entityliving, reason, fireEvent);
    }
}
