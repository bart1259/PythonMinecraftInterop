package pymcinterop;

public class Matrix4 {

    public int ROWS = 4;
    public int COLS = 4;
    public float[] elements;

    public Matrix4() {
        elements = new float[ROWS*COLS];
        loadIdentity();
    }

    public void loadIdentity() {
        for (int i = 0; i < ROWS * COLS; i++) {
            this.elements[i] = 0;
            if (i % 5 ==0) {
                this.elements[i] = 1;
            }
        }
    }

    public void writeFromArray(float[] elements) {
        if (elements.length != 16) {
            throw new IllegalArgumentException("Elements array must have lenght of 16");
        }

        for (int i = 0; i < ROWS * COLS; i++) {
            this.elements[i] = elements[i];
        }
    }

    public void rotate(float angle, String axis) {
        loadIdentity();

        final float RAD2DEG = 0.0174533f;

        if(axis.toLowerCase().equals("x")) {
            elements[5]  = (float) Math.cos(angle * RAD2DEG);
            elements[6]  = (float)-Math.sin(angle * RAD2DEG);
            elements[9]  = (float) Math.sin(angle * RAD2DEG);
            elements[10] = (float) Math.cos(angle * RAD2DEG);
        } else if(axis.toLowerCase().equals("y")) {
            elements[0]  = (float) Math.cos(angle * RAD2DEG);
            elements[2]  = (float) Math.sin(angle * RAD2DEG);
            elements[8]  = (float)-Math.sin(angle * RAD2DEG);
            elements[10] = (float) Math.cos(angle * RAD2DEG);
        } else if(axis.toLowerCase().equals("z")) {   
            elements[0]  = (float) Math.cos(angle * RAD2DEG);
            elements[1]  = (float)-Math.sin(angle * RAD2DEG);
            elements[4]  = (float) Math.sin(angle * RAD2DEG);
            elements[5]  = (float) Math.cos(angle * RAD2DEG);
        }
    }

    public void multiply(Matrix4 other) {
        float[] newValues = new float[COLS * ROWS];

        for (int i = 0; i < ROWS * COLS; i++) {
            int col = i % 4;
            int row = i / 4;

            newValues[i] = 0;
            for (int j = 0; j < ROWS; j++) {
                newValues[i] += elements[(row * 4) + j] * other.elements[(j * 4) + col];
            }
        }

        writeFromArray(newValues);
    }

    public Vector3 multiply(Vector3 other) {
        float x = (other.x * elements[0]) + (other.y * elements[1]) + (other.z * elements[2]) + (1 * elements[3]);
        float y = (other.x * elements[4]) + (other.y * elements[5]) + (other.z * elements[6]) + (1 * elements[7]);
        float z = (other.x * elements[8]) + (other.y * elements[9]) + (other.z * elements[10]) + (1 * elements[11]);

        return new Vector3(x, y, z);
    }

    public Matrix4 clone() {
        Matrix4 cloned = new Matrix4();
        cloned.writeFromArray(this.elements);
        return cloned;
    }
}
