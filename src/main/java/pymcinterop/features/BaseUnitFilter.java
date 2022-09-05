package pymcinterop.features;

import java.util.Iterator;

import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public abstract class BaseUnitFilter extends BaseFilter {

    @Override
    public BlockSet applyFilter(World world, BlockSet blockPoses) {
        BlockSet result = new BlockSet(blockPoses);

        Iterator<Vector3i> i = result.iterator();
        while (i.hasNext()) {
            Vector3i block = i.next();
            if(!this.applyFilterToBlock(world, block)) {
                i.remove();
            }
        }

        return result;
    }

    public abstract boolean applyFilterToBlock(World world, Vector3i blockPos);
    
}
