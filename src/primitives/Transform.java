package primitives;

//TODO: Make sure vectors representing a point can be TRS, but vectors representing a displacement can only be RS.
public class Transform {
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
        this._translation = new Vector3D(x, y, z);
    }

    public void setRotation(Vector3D rotation) {
        this._rotation = new Vector3D(rotation);
    }

    public void setRotation(double x, double y, double z) {
        this._rotation = new Vector3D(x, y, z);
    }

    public void setScale(Vector3D scale) {
        this._rotation = new Vector3D(_rotation);
    }

    public void setScale(double x, double y, double z) {
        this._scale = new Vector3D(x, y, z);
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
}
