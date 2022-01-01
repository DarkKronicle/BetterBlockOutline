package io.github.darkkronicle.betterblockoutline.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.Quaternion;

@AllArgsConstructor
@EqualsAndHashCode
public class Vector3f {

    public final float x;
    public final float y;
    public final float z;

    public Vector3f(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    public Vector3f(Vector3d vector) {
        this(vector.x, vector.y, vector.z);
    }

    public Vector3d to3d() {
        return new Vector3d(this.x, this.y, this.z);
    }

    public Vector3f rotate(Quaternion rotation) {
        Quaternion quaternion = new Quaternion(rotation);
        quaternion.hamiltonProduct(new Quaternion(this.x, this.y, this.z, 0.0f));
        Quaternion quaternion2 = new Quaternion(rotation);
        quaternion2.conjugate();
        quaternion.hamiltonProduct(quaternion2);
        return new Vector3f(quaternion.getX(), quaternion.getY(), quaternion.getZ());
    }

}
