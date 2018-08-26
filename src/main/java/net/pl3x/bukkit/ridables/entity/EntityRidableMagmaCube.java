package net.pl3x.bukkit.ridables.entity;

import net.minecraft.server.v1_13_R2.DamageSource;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EnumDifficulty;
import net.minecraft.server.v1_13_R2.FluidType;
import net.minecraft.server.v1_13_R2.GeneratorAccess;
import net.minecraft.server.v1_13_R2.GenericAttributes;
import net.minecraft.server.v1_13_R2.IWorldReader;
import net.minecraft.server.v1_13_R2.LootTables;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.ParticleParam;
import net.minecraft.server.v1_13_R2.Particles;
import net.minecraft.server.v1_13_R2.SoundEffect;
import net.minecraft.server.v1_13_R2.SoundEffects;
import net.minecraft.server.v1_13_R2.Tag;
import net.minecraft.server.v1_13_R2.TagsFluid;
import net.minecraft.server.v1_13_R2.World;
import net.pl3x.bukkit.ridables.configuration.Config;

public class EntityRidableMagmaCube extends EntityRidableSlime implements RidableEntity {
    public EntityRidableMagmaCube(World world) {
        super(EntityTypes.MAGMA_CUBE, world);
        this.fireProof = true;
    }

    public RidableType getType() {
        return RidableType.MAGMA_CUBE;
    }

    public float getSpeed() {
        return (float) getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue() * Config.MAGMA_CUBE_SPEED;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    // canSpawn
    public boolean a(GeneratorAccess generatoraccess) {
        return generatoraccess.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    // isNotColliding
    public boolean a(IWorldReader iworldreader) {
        return iworldreader.a_(this, this.getBoundingBox()) && iworldreader.getCubes(this, this.getBoundingBox()) && !iworldreader.containsLiquid(this.getBoundingBox());
    }

    public void setSize(int i, boolean flag) {
        super.setSize(i, flag);
        this.getAttributeInstance(GenericAttributes.h).setValue((double) (i * 3));
    }

    // getBrightness
    public float az() {
        return 1.0F;
    }

    // flame particles
    protected ParticleParam l() {
        return Particles.y;
    }

    // getLootTable
    protected MinecraftKey getDefaultLootTable() {
        return this.dy() ? LootTables.a : LootTables.ap;
    }

    public boolean isBurning() {
        return false;
    }

    // getJumpDelay
    protected int dr() {
        return super.dr() * 4;
    }

    // alterSquishAmount
    protected void ds() {
        this.a *= 0.9F;
    }

    // jump
    protected void cH() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.impulse = true;
    }

    // handleFluidJump
    protected void c(Tag<FluidType> tag) {
        if (tag == TagsFluid.LAVA) {
            this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
            this.impulse = true;
        } else {
            super.c(tag);
        }

    }

    // fall
    public void c(float f, float f1) {
    }

    // canDamagePlayer
    protected boolean dt() {
        return this.cP();
    }

    // getAttackStrength
    protected int du() {
        return super.du() + 2;
    }

    // getHurtSound
    protected SoundEffect d(DamageSource damagesource) {
        return this.dz() ? SoundEffects.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_HURT;
    }

    // getDeathSound
    protected SoundEffect cs() {
        return this.dz() ? SoundEffects.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_DEATH;
    }

    // getSquishSound
    protected SoundEffect dv() {
        return this.dz() ? SoundEffects.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_SQUISH;
    }

    // getJumpSound
    protected SoundEffect dw() {
        return SoundEffects.ENTITY_MAGMA_CUBE_JUMP;
    }
}
