package pymcinterop.features;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import pymcinterop.Vector3i;

public class IsBlockFilter extends BaseUnitFilter {

    private Block block;

    public IsBlockFilter(String blockName) {
        block = Registry.BLOCK.get(new Identifier(blockName));
    }

    @Override
    public boolean applyFilterToBlock(World world, Vector3i blockPos) {
        return world.getBlockState(new BlockPos(blockPos.x, blockPos.y, blockPos.z)).getBlock() == block;
    }
    
}
