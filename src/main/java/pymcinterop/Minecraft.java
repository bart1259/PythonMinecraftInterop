package pymcinterop;

import org.slf4j.Logger;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.block.Block;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import pymcinterop.features.IsBlockFilter;
import pymcinterop.solids.Solid;
import pymcinterop.texturing.BaseTexturer;
import pymcinterop.texturing.UniformTexturer;

public class Minecraft {
    
    private Logger logger;
    public static CommandContext<ServerCommandSource> context;

    public Minecraft() {

    }

    public void setBlock(int x, int y, int z, String blockname) {
        BlockPos pos = new BlockPos(x, y, z);
        // Block block = BlockApiLookup.get(lookupId, apiClass, contextClass)
        // BlockState state = new BlockState();
        Block block = Registry.BLOCK.get(new Identifier(blockname));
        context.getSource().getWorld().setBlockState(pos, block.getDefaultState());
    }

    public void clearBlocks(int x, int y, int z, int w, int h, int d) {
        Block block = Registry.BLOCK.get(new Identifier("minecraft:air"));
        World world = context.getSource().getWorld();

        BlockPos pos1 = new BlockPos(x, y, z);
        BlockPos pos2 = new BlockPos(x + w, y + h, z + d);
        for (BlockPos pos : BlockPos.iterate(pos1, pos2)) {
            world.setBlockState(pos, block.getDefaultState());
        }
    }

    
    public void fillSolid(Solid solid, Object texturer) {
        BlockSet blocks = solid.getBlocks();
        setBlocks(blocks, texturer);
    }
    
    public void setBlocks(BlockSet blocks, Object texturer) {
        BaseTexturer selectedTexturer = BaseTexturer.getTexurer(texturer);
        selectedTexturer.textureBlocks(context.getSource().getWorld(), blocks);
    }

    public void replaceBlocks(BlockSet blocks, String replaced, Object texturer) {
        BaseTexturer selectedTexturer = BaseTexturer.getTexurer(texturer);
        blocks = blocks.filter(new IsBlockFilter(replaced));
        selectedTexturer.textureBlocks(context.getSource().getWorld(), blocks);
    }

    public void sendToChat(String message) {
        context.getSource().sendMessage(Text.literal(message));
    }

}
