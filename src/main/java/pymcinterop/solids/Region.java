package pymcinterop.solids;

import net.minecraft.util.math.BlockPos;
import pymcinterop.BlockSet;
import pymcinterop.Vector3;
import pymcinterop.Vector3i;

public class Region {
    public Vector3 corner;
    public Vector3 dimensions;

    public Region() {
        this(0,0,0,0,0,0);
    }

    public Region(Region other) {
        this(other.corner.x, other.corner.y, other.corner.z, other.dimensions.x, other.dimensions.y, other.dimensions.z);
    }

    public Region(float x, float y, float z, float w, float h, float d) {
        this.corner = new Vector3(x, y, z);
        this.dimensions = new Vector3(w, h, d);
    }

    public Region enlarge(float strength) {
        Region result = new Region(this);

        result.corner.x -= strength;
        result.corner.y -= strength;
        result.corner.z -= strength;

        result.dimensions.x += strength * 2;
        result.dimensions.y += strength * 2;
        result.dimensions.z += strength * 2;

        return result;
    }

    public BlockSet getBlocks() {
        BlockSet blocks = new BlockSet();
        BlockPos pos1 = new BlockPos(this.corner.x, this.corner.y, this.corner.z);
        BlockPos pos2 = new BlockPos(this.corner.x + this.dimensions.x, this.corner.y + this.dimensions.y, this.corner.z + this.dimensions.z);
        for (BlockPos pos : BlockPos.iterate(pos1, pos2)) {
            blocks.add(new Vector3i(pos.getX(), pos.getY(), pos.getZ()));
        }
        return blocks;
    }
}
