package pymcinterop.features;

import java.util.Random;

import net.minecraft.world.World;
import pymcinterop.Vector3i;

public class RandomDropFilter extends BaseUnitFilter {
    
    private float chance;
    private Random random;

    public RandomDropFilter(float chance) {
        this.chance = chance;
        this.random = new Random();
    }

    @Override
    public boolean applyFilterToBlock(World world, Vector3i blockPos) {
        return random.nextDouble() >= chance;
    }
    
}
