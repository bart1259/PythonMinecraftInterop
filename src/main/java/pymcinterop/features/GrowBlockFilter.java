package pymcinterop.features;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public class GrowBlockFilter extends BaseFilter{

    private int min;
    private int max;

    private Random random;

    public GrowBlockFilter(int min, int max) {
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
                switch (random.nextInt(8)) {
                    case 0:
                        break;
                    case 1:
                    randomBlock.z--;
                        break;
                    case 2:
                    randomBlock.x--;
                        break;
                    case 3:
                    randomBlock.x--;
                    randomBlock.z--;
                        break;
                    case 4:
                    randomBlock.y--;
                        break;
                    case 5:
                    randomBlock.y--;
                    randomBlock.z--;
                        break;
                    case 6:
                    randomBlock.y--;
                    randomBlock.x--;
                        break;
                    case 7:
                    randomBlock.y--;
                    randomBlock.x--;
                    randomBlock.z--;
                }

                for (int x = 0; x < 2; x++) {
                    for (int y = 0; y < 2; y++) {
                        for (int z = 0; z < 2; z++) {
                            Vector3i cubeBlock = randomBlock.add(x, y, z);
                            if(!clusterBlocks.contains(cubeBlock)) {
                                clusterBlocks.add(cubeBlock);
                                blocksToAdd--;
                            }
                        }
                    }
                }
            }

            if(blocksToAdd < 0) {
                int index = random.nextInt(clusterBlocks.size());
                clusterBlocks.remove(clusterBlocks.get(index));
            }

            result.addAll(clusterBlocks);
        }

        return result;
    }

}
