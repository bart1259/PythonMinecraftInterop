package pymcinterop.solids;

import pymcinterop.Vector3;

public class Torus extends Solid {

    public float a;
    public float c;
    public Character axis;

    public Torus(float c, float a, String axis) {
        this.a = a;
        this.c = c;
        this.axis = axis.toLowerCase().toCharArray()[0];
    }

    @Override
    public boolean isInsideUnitSolid(float x, float y, float z) {
        if(axis == 'z') {
            return Math.pow(c - Math.sqrt((x * x) + (y * y)), 2) + (z * z) <= a * a;
        } else if (axis == 'x') {
            return Math.pow(c - Math.sqrt((y * y) + (z * z)), 2) + (x * x) <= a * a;
        } else if (axis == 'y') {
            return Math.pow(c - Math.sqrt((x * x) + (z * z)), 2) + (y * y) <= a * a;
        }
        return false;
    }

    @Override
    public Region getBounds() {
        Region ret = new Region();

        float max = a + c;
        ret.dimensions = new Vector3(max * 2, max * 2, max * 2);
        ret.corner = new Vector3(this.x, this.y, this.z).subtract(new Vector3(max, max, max));

        return ret;
    }
    
}
