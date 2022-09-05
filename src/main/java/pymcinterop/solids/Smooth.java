package pymcinterop.solids;

public class Smooth {
    public static Solid smooth(Solid solid) {

        return new Solid() {

            // Only return true if the block is on a boundary
            @Override
            public boolean isInsideUnitSolid(float x, float y, float z) {
                int neighborCount = 0;
                neighborCount += (solid.isInsideSolid(x + 1, y, z) ? 1 : 0);
                neighborCount += (solid.isInsideSolid(x - 1, y, z) ? 1 : 0);
                neighborCount += (solid.isInsideSolid(x, y + 1, z) ? 1 : 0);
                neighborCount += (solid.isInsideSolid(x, y - 1, z) ? 1 : 0);
                neighborCount += (solid.isInsideSolid(x, y, z + 1) ? 1 : 0);
                neighborCount += (solid.isInsideSolid(x, y, z - 1) ? 1 : 0);

                if(solid.isInsideSolid(x, y, z)) {
                    return neighborCount >= 3;
                } else {
                    return neighborCount >= 4;
                }
            }

            @Override
            public Region getBounds() {
                return solid.getBounds();
            }
            
        };

    }
}
