package primitives;

public interface ITransformable {
    void translate(double x, double y, double z);
    void rotate(double x, double y, double z);
    void scale(double x, double y, double z);
    void scale(double scalar);
    void transform(Transform _transform);
    void transform(Vector3D translation, Vector3D rotation, Vector3D scale);

}
