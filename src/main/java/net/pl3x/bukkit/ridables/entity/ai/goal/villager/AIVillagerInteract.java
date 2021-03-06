package net.pl3x.bukkit.ridables.entity.ai.goal.villager;

import net.minecraft.server.v1_13_R2.PathfinderGoalInteractVillagers;
import net.pl3x.bukkit.ridables.entity.npc.RidableVillager;

public class AIVillagerInteract extends PathfinderGoalInteractVillagers {
    private final RidableVillager villager;

    public AIVillagerInteract(RidableVillager villager) {
        super(villager);
        this.villager = villager;
    }

    // shouldExecute
    @Override
    public boolean a() {
        return villager.getRider() == null && super.a();
    }

    // shouldContinueExecuting
    @Override
    public boolean b() {
        return villager.getRider() == null && super.b();
    }
}
