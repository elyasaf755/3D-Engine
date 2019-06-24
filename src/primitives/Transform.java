package primitives;

import sun.security.ssl.Debug;

import java.time.Duration;
import java.time.Instant;

//TODO: Make sure Points can be TRS, but Vectors can only be RS but bot translated.
public class Transform {
    private static double _zNear;
    private static double _zFar;
    private static double _width;//screen width
    private static double _height;//screen height
    private static double _fov;//field of view

    private Vector3D _translation;
    private Vector3D _rotation;
    private Vector3D _scale;

    //Constructors

    public Transform(){
        _translation = new Vector3D(Vector3D.ZERO);
        _rotation = new Vector3D(Vector3D.ZERO);
        _scale = new Vector3D(1,1,1);
    }

    public Transform(Vector3D translation, Vector3D rotation, Vector3D scale){
        _translation = new Vector3D(translation);
        _rotation = new Vector3D(rotation);
        _scale = new Vector3D(scale);
    }

    public Transform(Transform _transform){
        _translation = _transform.getTranslation();
        _rotation = _transform.getRotation();
        _scale = _transform.getScale();

    }

    //Getters

    public Vector3D getTranslation() {
        return new Vector3D(_translation);
    }

    public Vector3D getRotation() {
        return new Vector3D(_rotation);
    }

    public Vector3D getScale() {
        return new Vector3D(_scale);
    }

    //Setters

    public void setTranslation(Vector3D translation) {
        this._translation = new Vector3D(translation);
    }

    public void setTranslation(double x, double y, double z) {
        if (Util.equals(x, 0) && Util.equals(y, 0) && Util.equals(z, 0))
            this._translation = new Vector3D(Vector3D.ZERO);
        else
            this._translation = new Vector3D(x, y, z);
    }

    public void setRotation(Vector3D rotation) {
        this._rotation = new Vector3D(rotation);
    }

    public void setRotation(double x, double y, double z) {
        if (Util.equals(x, 0) && Util.equals(y, 0) && Util.equals(z, 0)){
            this._rotation = new Vector3D(Vector3D.ZERO);
            return;
        }

        this._rotation = new Vector3D(x, y, z);
    }

    public void setScale(Vector3D scale) {
        this._rotation = new Vector3D(_rotation);
    }

    public void setScale(double x, double y, double z) {
        this._scale = new Vector3D(x, y, z);
    }

    public void setProjection(double fov, double width, double height, double zFar, double zNear){
        Transform._fov = fov;
        Transform._width = width;
        Transform._height = height;
        Transform._zFar = zFar;
        Transform._zNear = zNear;
    }
    //Methods

    public Matrix4 getTransformation(){
        Matrix4 translationMatrix = new Matrix4().initTranslation(
                this._translation.getPoint().getX().getCoord(),
                this._translation.getPoint().getY().getCoord(),
                this._translation.getPoint().getZ().getCoord()
        );

        Matrix4 rotationMatrix = new Matrix4().initRotation(
                _rotation.getPoint().getX().getCoord(),
                _rotation.getPoint().getY().getCoord(),
                _rotation.getPoint().getZ().getCoord()
        );

        Matrix4 scaleMatrix = new Matrix4().initScale(
                _scale.getPoint().getX().getCoord(),
                _scale.getPoint().getY().getCoord(),
                _scale.getPoint().getZ().getCoord()
        );

        return translationMatrix.mult(rotationMatrix.mult(scaleMatrix));
    }

    public static Matrix3 getRodriguesRotation(Vector3D source, Vector3D destination){//TODO: TIME: 0.77 sec (tender test 8).
        if (source.equals(destination)){
            return new Matrix3(Matrix3.IDENTITY);
        }

        Vector3D v = new Vector3D(source);
        Vector3D u = new Vector3D(destination);
        Vector3D k = v.crossProduct(u).normalized();

        double angle = u.angleBetween_rad(v);

        Matrix3 I = new Matrix3(Matrix3.IDENTITY);

        double k1 = k.getPoint().getX().getCoord();
        double k2 = k.getPoint().getY().getCoord();
        double k3 = k.getPoint().getZ().getCoord();

        double[][] tempM = {
                {0, -k3, k2},
                {k3, 0, -k1},
                {-k2, k1, 0}
        };

        Matrix3 K = new Matrix3(tempM);

        Matrix3 R = I.add(K.scale(Math.sin(angle)));

        return R.add(K.mult(K).scale(1-Math.cos(angle)));
    }

    public static Matrix3 getHouseholderMatrix(Vector3D source, Vector3D destination){
        Matrix3 I = new Matrix3(Matrix3.IDENTITY);

        Vector3D v = source.subtract(destination).normalized();

        return I.sub(v.outterProduct(v).scale(2));
    }

    public static Vector3D rotatedVectorAround(Vector3D v, Vector3D axis, double angle){
        Vector3D k = axis.normalized();
        Vector3D nV = v.normalized();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        Vector3D vCos;

        if (Util.equals(cos, 0)){
            vCos = new Vector3D(Vector3D.ZERO);
        }
        else{
            vCos = v.scale(cos);
        }

        Vector3D cross;

        if (Util.equals(sin, 0)){
            cross = new Vector3D(Vector3D.ZERO);
        }
        else{
            if (k.equals(nV)){
                cross = new Vector3D(Vector3D.ZERO);
            }
            else{
                cross = k.crossProduct(v).scale(sin);
            }
        }

        Vector3D K = new Vector3D(k);

        if (Util.equals(1, cos)){
            K = new Vector3D(Vector3D.ZERO);
        }
        else{
            double dot = k.dotProduct(v);

            if (Util.equals(dot, 0)){
                K = new Vector3D(Vector3D.ZERO);
            }
            else{
                K = k.scale(Util.uscale(dot, 1 - cos));
            }
        }

        return vCos.add(cross).add(K);

    }

    public Matrix4 getProjectedTransformation(){

        Matrix4 transformationMatrix = getTransformation();
        Matrix4 projectionMatrix = new Matrix4().initProjection(_fov, _width, _height, _zNear, _zFar);

        return projectionMatrix.mult(transformationMatrix);
    }
}
