package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Geometries implements Intersectable, ITransformable, Iterable<Geometry>, Iterator<Geometry> {

    private int index = -1;

    protected ArrayList<Geometry> _geometriesList;


    /********* Constructors ***********/

    public Geometries()
    {
        _geometriesList = new ArrayList<>();
    }

    public Geometries(Geometry... geometries){
        _geometriesList = new ArrayList<>();

        for (Geometry geometry : geometries){
            _geometriesList.add(geometry);
        }
    }

    public Geometries(ArrayList<Geometry> geometries){
        _geometriesList = new ArrayList<>();

        _geometriesList.addAll(geometries);
    }

    public Geometries(Geometries geometries){
        _geometriesList = new ArrayList<>();

        for (Geometry geometry : geometries.get_GeometriesList()){
            _geometriesList.add(geometry);
        }
    }

    /********* Getters ***********/

    public ArrayList<Geometry> get_GeometriesList() {
        ArrayList<Geometry> result = new ArrayList<>();

        result.addAll(_geometriesList);

        return result;
    }

    //Setters

    public void set_geometriesList(ArrayList<Geometry> geometriesList) {
        _geometriesList.clear();

        for (Geometry geometry : geometriesList){
            _geometriesList.add(geometry);
        }
    }

    public void set_geometriesList(Geometry... geometries) {
        _geometriesList.clear();

        for (Geometry geometry : geometries){
            _geometriesList.add(geometry);
        }
    }

    public void set_geometriesList(Geometries geometries) {
        _geometriesList.clear();

        for (Geometry geometry : geometries.get_GeometriesList()){
            _geometriesList.add(geometry);
        }
    }

    /************* Methods ***************/

    public void add_geometries(Geometry... geometries) {
        for (Geometry geometry: geometries) {
            _geometriesList.add(geometry);
        }
    }

    public void add_geometries(Geometries geometries) {
        for (Geometry geometry: geometries) {
            _geometriesList.add(geometry);
        }
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
        ArrayList<GeoPoint> result = new ArrayList<>();;

        for (Geometry geometry : _geometriesList){
            result.addAll(geometry.findIntersections(ray));
        }

        return result;
    }

    @Override
    public void translate(double x, double y, double z) {
        for (Geometry geometry : _geometriesList){
            geometry.translate(x, y, z);
        }
    }

    @Override
    public void rotate(double x, double y, double z) {
        for (Geometry geometry : _geometriesList){
            geometry.rotate(x, y, z);
        }
    }

    @Override
    public void scale(double x, double y, double z) {
        for (Geometry geometry : _geometriesList){
            geometry.scale(x, y, z);
        }
    }

    @Override
    public void transform(Transform _transform) {
        for (Geometry geometry : _geometriesList){
            geometry.transform(_transform);
        }
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        for (Geometry geometry : _geometriesList){
            geometry.transform(translation, rotation, scale);
        }
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Geometries))
            return false;

        Geometries geometries = (Geometries) obj;
        ArrayList<Geometry> geometriesList = geometries.get_GeometriesList();

        if (geometriesList.size() != _geometriesList.size())
            return false;

        for (int i = 0; i < _geometriesList.size(); ++i){
            if (!(_geometriesList.get(i).equals(geometriesList.get(i))))
                return false;
        }

        return true;
    }

    @Override
    public Iterator<Geometry> iterator() {
        return _geometriesList.iterator();
    }

    @Override
    public boolean hasNext() {
        return index < _geometriesList.size();
    }

    @Override
    public Geometry next() {
        if (this.hasNext())
            return _geometriesList.get(++index);

        index = -1;
        throw new NoSuchElementException();
    }
}
