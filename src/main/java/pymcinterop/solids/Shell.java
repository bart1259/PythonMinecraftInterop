package pymcinterop.solids;

public class Shell {
    
    public static Solid shell(Solid solid, int thickness) {

        return new Solid() {

            // Only return true if the block is on a boundary
            @Override
            public boolean isInsideUnitSolid(float x, float y, float z) {
                if(solid.isInsideSolid(x, y, z)) {
                    for (int i = 0; i < thickness; i++) {
                        if ((solid.isInsideSolid(x + i, y, z) != solid.isInsideSolid(x - i, y, z))
                         || (solid.isInsideSolid(x, y + i, z) != solid.isInsideSolid(x , y - i, z))
                         || (solid.isInsideSolid(x, y, z + i) != solid.isInsideSolid(x , y, z - i))) {
                            return true;
                        }
                    }
                    return false;
                }
                return false;
            }

            @Override
            public Region getBounds() {
                return solid.getBounds();
            }
            
        };

    }

}
