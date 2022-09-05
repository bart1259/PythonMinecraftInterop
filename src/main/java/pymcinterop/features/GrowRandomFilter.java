package pymcinterop.features;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public class GrowRandomFilter extends BaseFilter{

    private int min;
    private int max;

    private Random random;

    public GrowRandomFilter(int min, int max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    @Override
    public BlockSet applyFilter(World world, BlockSet blockPoses) {
        BlockSet result = new BlockSet();


        for (Vector3i block : blockPoses) {
            ArrayList<Vector3i> clusterBlocks = new ArrayList<Vector3i>();
            clusterBlocks.add(block);

            int blocksToAdd = random.nextInt(this.min, this.max + 1);
            while(blocksToAdd > 0) {
                // Choose random block
                int index = random.nextInt(clusterBlocks.size());

                Vector3i randomBlock = new Vector3i(clusterBlocks.get(index));
                switch (random.nextInt(6)) {
                    case 0:
                    randomBlock.x++;
                        break;
                    case 1:
                    randomBlock.x--;
                        break;
                    case 2:
                    randomBlock.y++;
                        break;
                    case 3:
                    randomBlock.y--;
                        break;
                    case 4:
                    randomBlock.z++;
                        break;
                    case 5:
                    randomBlock.z--;
                        break;
                }
                
                if(clusterBlocks.contains(randomBlock)) {
                    continue;
                } else {
                    clusterBlocks.add(randomBlock);
                }

                blocksToAdd--;
            }

            result.addAll(clusterBlocks);
        }

        return result;
    }
    
}
