package world;

public class Camera {
    public static int x, y;

    public static int clamp(int current, int min, int max) {
        if (current < min)
            return min;
        if (current > max)
            return max;
        return current;
    }
}
