package pymcinterop.features;

import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public abstract class BaseFilter {
    
    public abstract BlockSet applyFilter(World world, BlockSet blockPoses);

}
