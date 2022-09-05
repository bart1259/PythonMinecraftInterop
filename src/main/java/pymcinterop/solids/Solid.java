package pymcinterop.solids;

import java.util.function.Consumer;

import net.minecraft.util.math.BlockPos;
import pymcinterop.BlockSet;
import pymcinterop.Matrix4;
import pymcinterop.Vector3;
import pymcinterop.Vector3i;


public abstract class Solid {

    public float x;
    public float y;
    public float z;

    private Matrix4 transform;

    public Solid() {
        this(0, 0, 0);
    }

    public Solid(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        transform = new Matrix4();
        transform.loadIdentity();
    }

    public Solid rotate(float rx, float ry, float rz) {

        Matrix4 xRot = new Matrix4();
        xRot.rotate(-rx, "x");
        Matrix4 yRot = new Matrix4();
        yRot.rotate( ry, "y");
        Matrix4 zRot = new Matrix4();
        zRot.rotate(-rz, "z");

        Matrix4 rot = new Matrix4();
        rot.multiply(xRot);
        rot.multiply(yRot);
        rot.multiply(zRot);

        transform.multiply(rot);

        return this;
    }

    public boolean isInsideSolid(float x, float y, float z) {
        // Inverse Translation
        x -= this.x;
        y -= this.y;
        z -= this.z;

        Vector3 pt = transform.multiply(new Vector3(x,y,z));

        return isInsideUnitSolid(pt.x, pt.y, pt.z);
    }

    public BlockSet getBlocks() {
        BlockSet result = new BlockSet();

        Region bounds = getBounds();
        BlockSet blocks = bounds.getBlocks();
        blocks.parallelStream().forEach(new Consumer<Vector3i>() {
            @Override
            public void accept(Vector3i vec) {
                if(isInsideSolid(vec.x, vec.y, vec.z)) {
                    result.add(vec);
                }
            }
        });
        
        // BlockPos pos1 = new BlockPos(bounds.corner.x, bounds.corner.y, bounds.corner.z);
        // BlockPos pos2 = new BlockPos(bounds.corner.x + bounds.dimensions.x, bounds.corner.y + bounds.dimensions.y, bounds.corner.z + bounds.dimensions.z);
        // for (BlockPos pos : BlockPos.iterate(pos1, pos2)) {
        //     if(isInsideSolid(pos.getX(), pos.getY(), pos.getZ())) {
        //         result.add(new Vector3i(pos.getX(), pos.getY(), pos.getZ()));
        //     }
        // }
        return result;
    }

    public abstract boolean isInsideUnitSolid(float x, float y, float z);
    public abstract Region getBounds();
}
