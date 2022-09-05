package pymcinterop.texturing;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import org.python.core.PyList;
import org.python.core.PyTuple;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

/**
 * [("minecraft:grass_block", 1), ("minecraft:dirt", 2), ("minecraft:stone", 0), ("minecraft:obsidian", -2), ("minecraft:bedorck", -1)]
 */
public class DepthTexturer extends BaseTexturer {

    private ArrayList<AbstractMap.SimpleEntry<Integer, Block>> blockDepths;

    public DepthTexturer(Object values) {
        blockDepths = new ArrayList<>();

        if(values instanceof PyList) {
            PyList tupleArray = (PyList)values;

            for (int i = 0; i < tupleArray.__len__(); i++) {
                if(!(tupleArray.__getitem__(i) instanceof PyTuple)) {
                    throw new IllegalArgumentException("Must be an array of TUPLES");
                }
                PyTuple tuple = (PyTuple)tupleArray.__getitem__(i);

                if(tuple.__len__() != 2) {
                    throw new IllegalArgumentException("2 values must be passed in each tuple");
                }
                
                Integer depth = (int)tuple.__getitem__(1).asInt();
                String blockName = tuple.__getitem__(0).asString();
                Block block = Registry.BLOCK.get(new Identifier(blockName));

                blockDepths.add(new AbstractMap.SimpleEntry<Integer, Block>(depth, block));
            }
        } else {
            throw new IllegalArgumentException("An ARRAY of tuples must be passed in");
        }
    }
    
    @Override
    public void textureBlocks(World world, BlockSet blocks) {
        // Sort all blocks by their y depth
        HashMap<AbstractMap.SimpleEntry<Integer, Integer>, ArrayList<Vector3i>> xzMap = new HashMap<>();
        for (Vector3i block : blocks) {
            AbstractMap.SimpleEntry<Integer,Integer> xz = new SimpleEntry<>(block.x, block.z);
            if(xzMap.containsKey(xz)) {
                xzMap.get(xz).add(block);
            } else {
                xzMap.put(xz, new ArrayList<>());
                xzMap.get(xz).add(block);
            }
        }

        // Sort each XZ strip by height (highest to lowest)
        for (ArrayList<Vector3i> strips : xzMap.values()) {
            strips.sort(new Comparator<Vector3i>() {
                @Override
                public int compare(Vector3i arg0, Vector3i arg1) {
                    return arg1.y - arg0.y;
                }
            });
        }

        for (ArrayList<Vector3i> strips : xzMap.values()) {

            int blocksLeftInSection = 0;
            int sectionIndex = 0;
            Block block = null;
            Vector3i lastBlockIndex = null;
            boolean reachedInfinite = false;
            for (int i = 0; i < strips.size(); i++) {
                Vector3i blockIndex = strips.get(i);
                int blocksJumped = (lastBlockIndex == null) ? 1 : lastBlockIndex.y - blockIndex.y;

                // Skip over possible air blocks
                for (int j = 0; j < blocksJumped; j++) {
                    //Determine texture
                    if(blocksLeftInSection <= 0 && reachedInfinite == false) {
                        blocksLeftInSection = blockDepths.get(sectionIndex).getKey();
                        block = blockDepths.get(sectionIndex).getValue();

                        reachedInfinite = (blocksLeftInSection == 0); // If zero is passed in keep going forever
                        sectionIndex++;
                    }
                    blocksLeftInSection--;
                }
                world.setBlockState(new BlockPos(blockIndex.x, blockIndex.y, blockIndex.z), block.getDefaultState());
                lastBlockIndex = blockIndex;
            }

            // Texture from bottom
            if (reachedInfinite) {
                lastBlockIndex = null;
                Block infBlock = block;
                sectionIndex = blockDepths.size() - 1;
                blocksLeftInSection = -blockDepths.get(sectionIndex).getKey();
                block = blockDepths.get(sectionIndex).getValue();
                boolean finished = false;
                for (int i = strips.size() - 1; i >= 0; i--) {
                    Vector3i blockIndex = strips.get(i);
                    BlockState currentBlock = world.getBlockState(new BlockPos(blockIndex.x, blockIndex.y, blockIndex.z));
                    if(currentBlock.getBlock() != infBlock || finished == true) {
                        break;
                    }

                    int blocksJumped = (lastBlockIndex == null) ? 1 : blockIndex.y - lastBlockIndex.y;
    
                    // Skip over possible air blocks
                    for (int j = 0; j < blocksJumped; j++) {
                        //Determine texture
                        if(blocksLeftInSection <= 0 && sectionIndex >= 0) {
                            blocksLeftInSection = -blockDepths.get(sectionIndex).getKey();
                            block = blockDepths.get(sectionIndex).getValue();
    
                            finished = (blocksLeftInSection == 0); // If zero is passed in keep going forever
                            sectionIndex--;
                        }
                        blocksLeftInSection--;
                    }
                    world.setBlockState(new BlockPos(blockIndex.x, blockIndex.y, blockIndex.z), block.getDefaultState());
                    lastBlockIndex = blockIndex;
                }
            }
        }

    }
}
