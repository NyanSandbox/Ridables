package net.pl3x.bukkit.ridables.entity.ai.goal.zombie.drowned;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.IWorldReader;
import net.minecraft.server.v1_13_R2.PathfinderGoalGotoTarget;
import net.pl3x.bukkit.ridables.entity.monster.zombie.RidableDrowned;

public class AIDrownedGoToBeach extends PathfinderGoalGotoTarget {
    private final RidableDrowned drowned;

    public AIDrownedGoToBeach(RidableDrowned drowned, double speed) {
        super(drowned, speed, 8, 2);
        this.drowned = drowned;
    }

    // shouldExecute
    @Override
    public boolean a() {
        return drowned.getRider() == null && super.a() && !drowned.world.L() && drowned.isInWater() && drowned.locY >= (double) (drowned.world.getSeaLevel() - 3);
    }

    // shouldContinueExecuting
    @Override
    public boolean b() {
        return drowned.getRider() == null && super.b();
    }

    // shouldMoveTo
    @Override
    protected boolean a(IWorldReader world, BlockPosition pos) {
        BlockPosition posUp = pos.up();
        return world.isEmpty(posUp) && world.isEmpty(posUp.up()) && world.getType(pos).q();
    }

    // startExecuting
    @Override
    public void c() {
        drowned.setSwimmingUp(false);
        drowned.setGroundNavigation();
        super.c();
    }

    // resetTask
    @Override
    public void d() {
        super.d();
    }
}
