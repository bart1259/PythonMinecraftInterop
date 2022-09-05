package pymcinterop.texturing;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public class UniformTexturer extends BaseTexturer {

    private Block uniformBlock;

    public UniformTexturer(String blockName) {
        uniformBlock = Registry.BLOCK.get(new Identifier(blockName));
    }

    @Override
    public void textureBlocks(World world, BlockSet blocks) {
        for (Vector3i block : blocks) {
            world.setBlockState(new BlockPos(block.x, block.y, block.z), uniformBlock.getDefaultState());
        }   
    }
    
}
