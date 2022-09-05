package pymcinterop.solids;

import pymcinterop.Vector3;

public class Cuboid extends Solid {
    
    public float w;
    public float h;
    public float d;

    public Cuboid(float x) {
        this.w = x;
        this.h = x;
        this.d = x;
    }

    public Cuboid(float w, float h, float d) {
        this.w = w;
        this.h = h;
        this.d = d;
    }

    @Override
    public boolean isInsideUnitSolid(float x, float y, float z) {
        return (x >= -this.w/2 && x <= this.w/2) && (y >= -this.h/2 && y <= this.h/2) && (z >= -this.d/2 && z <= this.d/2);
    }

    @Override
    public Region getBounds() {
        Region ret = new Region();

        float sqrt2 = (float)Math.sqrt(2);
        float max = Math.max(w, Math.max(h, d)) * sqrt2;
        ret.dimensions = new Vector3(max, max, max);
        ret.corner = new Vector3(this.x, this.y, this.z).subtract(new Vector3(max / 2, max / 2, max / 2));

        return ret;
    }
}
