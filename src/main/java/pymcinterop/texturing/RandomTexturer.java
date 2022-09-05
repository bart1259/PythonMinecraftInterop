package pymcinterop.texturing;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

import org.python.core.PyList;
import org.python.core.PyTuple;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import pymcinterop.BlockSet;
import pymcinterop.Vector3i;

public class RandomTexturer extends BaseTexturer {

    private Random random;
    private float sum = 0;
    private ArrayList<AbstractMap.SimpleEntry<Float, Block>> blockProbablities;

    public RandomTexturer(Object values) {
        random = new Random();
        blockProbablities = new ArrayList<>();

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
                
                Float weight = (float)tuple.__getitem__(1).asDouble();
                String blockName = tuple.__getitem__(0).asString();
                Block block = Registry.BLOCK.get(new Identifier(blockName));

                sum += weight;

                blockProbablities.add(new AbstractMap.SimpleEntry<Float, Block>(sum, block));
            }
        } else {
            throw new IllegalArgumentException("An ARRAY of tuples must be passed in");
        }
    }

    @Override
    public void textureBlocks(World world, BlockSet blocks) {
        for (Vector3i block : blocks) {
             // Determine texture
             float randFloat = random.nextFloat() * sum;
             for (int j = 0; j < blockProbablities.size(); j++) {
                 if(randFloat < blockProbablities.get(j).getKey()) {
                     world.setBlockState(new BlockPos(block.x, block.y, block.z), blockProbablities.get(j).getValue().getDefaultState());
                     break;
                 }
             }       
        } 
    }
    
}
