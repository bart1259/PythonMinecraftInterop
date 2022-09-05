package pymcinterop.texturing;

import net.minecraft.world.World;
import pymcinterop.BlockSet;

public abstract class BaseTexturer {
    public static BaseTexturer getTexurer(Object obj) {
        BaseTexturer selectedTexturer;

        if(obj instanceof String) {
            selectedTexturer = new UniformTexturer((String)obj);
        } else if (obj instanceof BaseTexturer){
            selectedTexturer = (BaseTexturer)obj;
        } else {
            throw new IllegalArgumentException("Texturer must be of type String or Textuerer");
        }

        return selectedTexturer;
    }

    public abstract void textureBlocks(World world, BlockSet blocks);
}
