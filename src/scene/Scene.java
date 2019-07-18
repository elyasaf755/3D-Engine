package scene;


import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Geometry;
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

    /**
     * default constructor
     */
    private Scene(){
        _name = "MyScene";
        _background = new Color(java.awt.Color.WHITE);
        _ambientLight = new AmbientLight();
        _geometries = new Geometries();
        _camera = new Camera(new Vector3D(1,0,0), new Vector3D(0,0,1));
        _screenDistance = 100;
        _lights = new ArrayList<>();
    }

    /**
     * constructor
     * @param name of scene
     */
    public Scene(String name){
        this();
        _name = name;
    }

    //Getters

    /**
     * getter
     * @return name of the scene
     */
    public String get_name() {
        return _name;
    }

    /**
     * getter
     * @return color of background
     */
    public Color get_background() {
        return _background;
    }

    /**
     * getter
     * @return Ambient Light of scene
     */
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    /**
     * getter
     * @return  all the geometries in the scene
     */
    public Geometries get_geometries() {
        return _geometries;
    }

    /**
     * getter
     * @return camera
     */
    public Camera get_camera() {
        return _camera;
    }

    /**
     * getter
     * @return screen distance from the camera
     */
    public double get_screenDistance() {
        return _screenDistance;
    }

    /**
     * getter
     * @return ArrayList of all light sources in the scene
     */
    public ArrayList<LightSource> get_lights() {
        return _lights;
    }

    //Setters

    /**
     * setter
     * @param _background color
     */
    public void set_background(Color _background) {
        this._background = _background;
    }

    /**
     * setter
     * @param _ambientLight color
     */
    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * setter
     * @param _camera copy by reference! (no copy constructor)
     * @param screenDistance from camera
     */
    public void set_camera(Camera _camera, double screenDistance) {
        //TODO: Warning! camera copy by reference! (no copy constructor).
        this._camera = _camera;
        this._screenDistance = screenDistance;
    }

    /**
     * setter
     * @param distance screen distance from the camera
     */
    public void set_distance(double distance){
        _screenDistance = distance;
    }

    //Methods

    /**
     * add some of geometries to the scene
     * @param geometries  as'geometry', or more than one
     */
    public void addGeometries(Geometry... geometries){
        _geometries.add_geometries(geometries);
    }

    /**
     * add some of geometries to the scene
     * @param geometries as 'geometries'
     */
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

    /**
     * getter
     * @return iterator of light resources
     */
    public Iterator<LightSource> getLightsIterator(){
        return _lights.iterator();
    }
}
