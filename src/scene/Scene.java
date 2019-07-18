package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;
import primitives.Vector3D;

import java.util.ArrayList;
import java.util.Iterator;

public class Scene {
    private String _name;
    private Color _background;//color of background
    private AmbientLight _ambientLight;//light of the whole scene (without a source)
    private Geometries _geometries;//list of geometries in the scene
    private Camera _camera;
    private double _screenDistance;// from the camera
    ArrayList<LightSource> _lights;// light sources in the scene

    //Constructors

    private Scene(){
        _name = "MyScene";
        _background = new Color(java.awt.Color.WHITE);
        _ambientLight = new AmbientLight();
        _geometries = new Geometries();
        _camera = new Camera(new Vector3D(1,0,0), new Vector3D(0,0,1));
        _screenDistance = 100;
        _lights = new ArrayList<>();
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

    public ArrayList<LightSource> get_lights() {
        return _lights;
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

    public void set_distance(double distance){
        _screenDistance = distance;
    }

    //Methods

    /**
     * add some of geometries to the scene
     * @param geometries first function get it as'geometry', or more than one, to add. and the second- as (list of) 'geometries'
     */
    public void addGeometries(Geometry... geometries){
        _geometries.add_geometries(geometries);
    }

    public void addGeometries(Geometries geometries) {
        _geometries.add_geometries(geometries);
    }

    /**
     * add light sources
     * @param lights sources
     */
    public void addLights(LightSource... lights){
        for (LightSource light : lights){
            _lights.add(light);
        }
    }

    public void addLights(ArrayList<LightSource> lights){
        for (LightSource light : lights){
            _lights.add(light);
        }
    }

    public Iterator<LightSource> getLightsIterator(){
        return _lights.iterator();
    }
}
