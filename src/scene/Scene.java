package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;
import primitives.Vector3D;

//TODO: QUESTION: Make Scene class inherit from Geometries class?
public class Scene {
    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _screenDistance;

    //Constructors

    public Scene(){
        _name = "MyScene";
        _background = new Color(java.awt.Color.WHITE);
        _ambientLight = new AmbientLight();
        _geometries = new Geometries();
        _camera = new Camera(new Vector3D(1,0,0));
        _screenDistance = 100;
    }

    public Scene(String name){
        this();
        _name = name;
    }

    //Getters

    public String get_name() {
        return _name;
    }

    public Color get_background() {
        return _background;
    }

    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    public Geometries get_geometries() {
        return _geometries;
    }

    public Camera get_camera() {
        return _camera;
    }

    public double get_screenDistance() {
        return _screenDistance;
    }

    //Setters

    public void set_background(Color _background) {
        this._background = _background;
    }

    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void set_camera(Camera _camera, double screenDistance) {
        //TODO: Warning! camera copy by reference! (no copy constructor).
        this._camera = _camera;
        this._screenDistance = screenDistance;
    }

    //Methods

    public void addGeometris(Intersectable... geometries){
        _geometries.add_geometry(geometries);
    }
}
