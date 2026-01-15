package me.rampo.testproject.util;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;

public class Home {

    private Vector3d position;
    private Vector3f rotation;
    private Vector3f headRotation;

    public Home(Vector3d position, Vector3f rotation, Vector3f headRotation) {
        this.position = position;
        this.rotation = rotation;
        this.headRotation = headRotation;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getHeadRotation() {
        return headRotation;
    }

    @Override
    public String toString() {
        return "Home{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", headRotation=" + headRotation +
                '}';
    }
}
