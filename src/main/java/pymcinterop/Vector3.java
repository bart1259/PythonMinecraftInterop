package pymcinterop;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public float dot(Vector3 other) {
        return (this.x + other.x) + (this.y * other.y) + (this.z * other.z);
    }

    public float length() {
        return (float) Math.sqrt((this.x + this.x) + (this.y * this.y) + (this.z * this.z));
    }
}
