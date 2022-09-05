package pymcinterop.solids;

import pymcinterop.utils.FastNoise;
import pymcinterop.utils.FastNoise.Interp;

public class PerlinNoiseDisplace {
    public static Solid displace(Solid solid, float noiseScale, float strength, int seed, String interpolation) {

        Interp interpolationStrategy = Interp.Linear;
        switch (interpolation.toLowerCase()) {
            case "linear":
                interpolationStrategy = Interp.Linear;                
                break;
            case "hermite":
                interpolationStrategy = Interp.Hermite;
                break;
            case "quintic":
                interpolationStrategy = Interp.Quintic;
                break;
        }

        FastNoise generator1 = new FastNoise(seed);
        generator1.SetFrequency(1);
        generator1.SetInterp(interpolationStrategy);
        FastNoise generator2 = new FastNoise(seed * 2);
        generator2.SetFrequency(1);
        generator2.SetInterp(interpolationStrategy);
        FastNoise generator3 = new FastNoise(seed * 3);
        generator3.SetFrequency(1);
        generator3.SetInterp(interpolationStrategy);

        return new Solid() {

            // Only return true if the block is on a boundary
            @Override
            public boolean isInsideUnitSolid(float x, float y, float z) {
                float nx = (float)generator1.GetPerlin(x / noiseScale, y / noiseScale, z / noiseScale) * strength;
                float ny = (float)generator2.GetPerlin(x / noiseScale, y / noiseScale, z / noiseScale) * strength;
                float nz = (float)generator3.GetPerlin(x / noiseScale, y / noiseScale, z / noiseScale) * strength;

                return solid.isInsideSolid(x - nx, y - ny, z - nz);
            }

            @Override
            public Region getBounds() {
                return solid.getBounds().enlarge(strength);
            }
            
        };

    }
}
