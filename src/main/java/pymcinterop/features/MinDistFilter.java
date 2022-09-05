package pymcinterop.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public class MinDistFilter extends BaseFilter {
    
    private float dist;

    public MinDistFilter(float dist) {
        this.dist = dist;
    }

    @Override
    public BlockSet applyFilter(World world, BlockSet blocks) {
        ArrayList<Vector3i> shuffledBlocks = new ArrayList<Vector3i>(blocks);
        Collections.shuffle(shuffledBlocks);

        Iterator<Vector3i> i = shuffledBlocks.iterator();
        while (i.hasNext()) {
            Vector3i block = i.next();

            Iterator<Vector3i> j = shuffledBlocks.iterator();
            while (j.hasNext()) {
                Vector3i block2 = j.next();
                if(block != block2 && block.isWithin(block2, dist)) {
                    i.remove();
                    break;
                }
            }
        }
        
        BlockSet result = new BlockSet(shuffledBlocks);
        return result;
    }
    
}
