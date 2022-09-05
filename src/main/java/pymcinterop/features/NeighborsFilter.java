package pymcinterop.features;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import pymcinterop.Vector3i;

public class NeighborsFilter extends BaseUnitFilter {

    private Block block;
    private boolean lessMode;
    private int limit;

    public NeighborsFilter(String blockName, int limit, String mode) {
        this.block = Registry.BLOCK.get(new Identifier(blockName));
        this.limit = limit;
        this.lessMode = mode.toLowerCase().equals("less");
    }

    @Override
    public boolean applyFilterToBlock(World world, Vector3i blockPos) {
        int count = 0;
        count += world.getBlockState(new BlockPos(blockPos.x + 1, blockPos.y, blockPos.z)).getBlock() == block ? 1 : 0;
        count += world.getBlockState(new BlockPos(blockPos.x - 1, blockPos.y, blockPos.z)).getBlock() == block ? 1 : 0;
        count += world.getBlockState(new BlockPos(blockPos.x, blockPos.y + 1, blockPos.z)).getBlock() == block ? 1 : 0;
        count += world.getBlockState(new BlockPos(blockPos.x, blockPos.y - 1, blockPos.z)).getBlock() == block ? 1 : 0;
        count += world.getBlockState(new BlockPos(blockPos.x, blockPos.y, blockPos.z + 1)).getBlock() == block ? 1 : 0;
        count += world.getBlockState(new BlockPos(blockPos.x, blockPos.y, blockPos.z - 1)).getBlock() == block ? 1 : 0;

        return (lessMode) ? count <= limit : count >= limit;
    }
    
}
