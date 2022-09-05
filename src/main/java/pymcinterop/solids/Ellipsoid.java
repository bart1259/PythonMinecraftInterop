package pymcinterop.solids;

import pymcinterop.Vector3;

public class Ellipsoid extends Solid {

    public float a;
    public float b;
    public float c;

    public Ellipsoid(float r) {
        this.a = r;
        this.b = r;
        this.c = r;
    }

    public Ellipsoid(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean isInsideUnitSolid(float x, float y, float z) {
        return ((x / a) * (x / a)) + ((y / b) * (y / b)) + ((z / c) * (z / c)) <= 1;
    }

    @Override
    public Region getBounds() {
        Region ret = new Region();

        float max = Math.max(a, Math.max(b, c));
        ret.dimensions = new Vector3(max * 2, max * 2, max * 2);
        ret.corner = new Vector3(this.x, this.y, this.z).subtract(new Vector3(max, max, max));

        return ret;
    }
    
}
