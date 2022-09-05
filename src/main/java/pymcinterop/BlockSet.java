package pymcinterop;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.world.World;
import pymcinterop.features.BaseFilter;

public class BlockSet extends HashSet<Vector3i>{

    public BlockSet(Collection<Vector3i> blocks) {
        this();
        addAll(blocks);
    }

    public BlockSet() {

    }

    public BlockSet union(BlockSet other) {
        BlockSet result = new BlockSet(this);
        result.addAll(other);
        return result;
    }

    public BlockSet intersect(BlockSet other) {
        BlockSet result = new BlockSet();

        for (Vector3i block : this) {
            if(other.contains(block)) {
                result.add(block);
            }
        }

        return result;
    }

    public BlockSet filter(BaseFilter filter) {
        World world = Minecraft.context.getSource().getWorld();
        return filter.applyFilter(world, this);
    }

    public BlockSet translate(int x, int y, int z) {
        BlockSet result = new BlockSet();
        for (Vector3i block : this) {
            result.add(block.add(x,y,z));
        }
        return result;
    }
}
