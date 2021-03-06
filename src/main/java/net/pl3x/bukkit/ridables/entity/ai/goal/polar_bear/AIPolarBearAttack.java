package net.pl3x.bukkit.ridables.entity.ai.goal.polar_bear;

import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.PathfinderGoalMeleeAttack;
import net.pl3x.bukkit.ridables.entity.animal.RidablePolarBear;

public class AIPolarBearAttack extends PathfinderGoalMeleeAttack {
    private final RidablePolarBear bear;

    public AIPolarBearAttack(RidablePolarBear bear) {
        super(bear, 1.25D, true);
        this.bear = bear;
    }

    // shouldExecute
    @Override
    public boolean a() {
        return bear.getRider() == null && super.a();
    }

    // shouldContinueExecuting
    @Override
    public boolean b() {
        return bear.getRider() == null && super.b();
    }

    // checkAndPerformAttack
    @Override
    protected void a(EntityLiving target, double distance) {
        double reach = a(target); // getAttackReachSq
        if (distance <= reach && b <= 0) { // attackTick
            b = 20; // attackTick
            bear.B(target); // attackEntityAsMob
            bear.s(false); // setStanding
        } else if (distance <= reach * 2.0D) {
            if (b <= 0) { // attackTick
                bear.s(false); // setStanding
                b = 20; // attackTick
            }
            if (b <= 10) { // attackTick
                bear.s(true); // setStanding
                bear.playWarningSound();
            }
        } else {
            b = 20; // attackTick
            bear.s(false); // setStanding
        }
    }

    // resetTask
    @Override
    public void d() {
        bear.s(false); // setStanding
        super.d();
    }

    // getAttackReachSq
    @Override
    protected double a(EntityLiving target) {
        return (double) (4.0F + target.width);
    }
}
