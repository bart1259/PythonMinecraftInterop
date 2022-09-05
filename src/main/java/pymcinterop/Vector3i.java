package pymcinterop;

public class Vector3i {

    public int x;
    public int y;
    public int z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(Vector3i other) {
        this(other.x, other.y, other.z);
    }

    public Vector3i add(int x, int y, int z) {
        return new Vector3i(this.x + x, this.y + y, this.z + z);
    }

    public Vector3i add(Vector3i other) {
        return add(other.x, other.y, other.z);
    }

    public boolean isWithin(Vector3i other, float dist) {
        return ((other.x - this.x) * (other.x - this.x)) + ((other.y - this.y) * (other.y - this.y)) + ((other.z - this.z) * (other.z - this.z)) < dist * dist;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Vector3i) {
            Vector3i vec = (Vector3i)other;
            return vec.x == this.x && vec.y == this.y && vec.z == this.z;
        }
        return false;
    }
}
