package net.pl3x.bukkit.ridables.entity.ai.goal.turtle;

import com.destroystokyo.paper.event.entity.TurtleGoHomeEvent;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.PathfinderGoal;
import net.minecraft.server.v1_13_R2.RandomPositionGenerator;
import net.minecraft.server.v1_13_R2.Vec3D;
import net.pl3x.bukkit.ridables.entity.animal.RidableTurtle;
import org.bukkit.entity.Turtle;

public class AITurtleGoHome extends PathfinderGoal {
    private final RidableTurtle turtle;
    private final double speed;
    private boolean stop;
    private int timer;

    public AITurtleGoHome(RidableTurtle turtle, double speed) {
        this.turtle = turtle;
        this.speed = speed;
    }

    // shouldExecute
    @Override
    public boolean a() {
        if (turtle.getRider() != null) {
            return false;
        }
        if (turtle.isBaby()) {
            return false;
        }
        if (!turtle.hasEgg()) {
            return false;
        }
        if (turtle.getRandom().nextInt(700) != 0) {
            return false;
        }
        if (turtle.c(turtle.getHome()) < 4096.0D) { // getDistanceSq
            return false;
        }
        return new TurtleGoHomeEvent((Turtle) turtle.getBukkitEntity()).callEvent();
    }

    // shouldContinueExecuting
    @Override
    public boolean b() {
        if (turtle.getRider() != null) {
            return false;
        }
        if (stop) {
            return false;
        }
        if (timer > 600) {
            return false;
        }
        return turtle.c(turtle.getHome()) >= 49.0D; // getDistanceSq
    }

    // startExecuting
    @Override
    public void c() {
        turtle.setGoingHome(true);
        stop = false;
        timer = 0;
    }

    // resetTask
    @Override
    public void d() {
        turtle.setGoingHome(false);
    }

    // tick
    @Override
    public void e() {
        BlockPosition pos = turtle.getHome();
        boolean closeToHome = turtle.c(pos) <= 256.0D; // getDistanceSq
        if (closeToHome) {
            ++timer;
        }
        if (turtle.getNavigation().p()) {
            Vec3D vec3d = RandomPositionGenerator.a(turtle, 16, 3, new Vec3D(pos.getX(), pos.getY(), pos.getZ()), Math.PI / 10D);
            if (vec3d == null) {
                vec3d = RandomPositionGenerator.a(turtle, 8, 7, new Vec3D(pos.getX(), pos.getY(), pos.getZ())); // findRandomTargetBlockTowards
            }
            if (vec3d != null && !closeToHome && turtle.world.getType(new BlockPosition(vec3d)).getBlock() != Blocks.WATER) {
                vec3d = RandomPositionGenerator.a(turtle, 16, 5, new Vec3D(pos.getX(), pos.getY(), pos.getZ())); // findRandomTargetBlockTowards
            }
            if (vec3d == null) {
                stop = true;
                return;
            }
            turtle.getNavigation().a(vec3d.x, vec3d.y, vec3d.z, speed);
        }
    }
}
