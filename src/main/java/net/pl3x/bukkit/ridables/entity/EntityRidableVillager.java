package net.pl3x.bukkit.ridables.entity;

import net.minecraft.server.v1_13_R1.ControllerMove;
import net.minecraft.server.v1_13_R1.Entity;
import net.minecraft.server.v1_13_R1.EntityHuman;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.EntityVillager;
import net.minecraft.server.v1_13_R1.EnumHand;
import net.minecraft.server.v1_13_R1.GenericAttributes;
import net.minecraft.server.v1_13_R1.World;
import net.pl3x.bukkit.ridables.configuration.Config;
import net.pl3x.bukkit.ridables.entity.controller.ControllerWASD;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class EntityRidableVillager extends EntityVillager implements RidableEntity {
    private ControllerMove aiController;
    private ControllerWASD wasdController;

    public EntityRidableVillager(World world) {
        super(world);
        aiController = moveController;
        wasdController = new ControllerWASD(this);
    }

    public boolean isActionableItem(ItemStack itemstack) {
        return false;
    }

    protected void mobTick() {
        EntityPlayer rider = getRider();
        if (rider != null) {
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
        return Config.VILLAGER_JUMP_POWER;
    }

    public float getSpeed() {
        return (float) getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue() * Config.VILLAGER_SPEED * 0.35F;
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
        }
    }

    public void useWASDController() {
        if (moveController != wasdController) {
            moveController = wasdController;
        }
    }

    public boolean onSpacebar() {
        return false;
    }

    public boolean onClick(org.bukkit.entity.Entity entity, EnumHand hand) {
        return false;
    }

    public boolean onClick(Block block, EnumHand hand) {
        return false;
    }

    public boolean onClick(EnumHand hand) {
        return false;
    }

    // processInteract
    public boolean a(EntityHuman entityhuman, EnumHand enumhand) {
        return getRider() == null && super.a(entityhuman, enumhand);
    }
}