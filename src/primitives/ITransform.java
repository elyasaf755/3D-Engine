package primitives;

public interface ITransform {
    void translate(double x, double y, double z);
    void rotate(double x, double y, double z);
    void scale(double x, double y, double z);//TODO: Add "void scale(double facotr)" for cases when x = y = z = factor?
    void transform(Transform _transform);
    void transform(Vector3D translation, Vector3D rotation, Vector3D scale);

}