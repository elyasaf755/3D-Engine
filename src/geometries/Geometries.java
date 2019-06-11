package geometries;

import primitives.Coordinate;
import primitives.Ray;
import primitives.Util;

import java.util.ArrayList;
import java.util.Iterator;

public class Geometries implements Intersectable {

    protected ArrayList<Geometry> _geometriesList;


    /********** Constructors ***********/

    public Geometries()
    {
        _geometriesList = new ArrayList<>();
    }

    /********** Getters ***********/

    public ArrayList<Geometry> get_GeometriesList() {
        return _geometriesList;
    }

    /************** Methods ***************/

    public void add_geometry (Geometry... geometries) {
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

        Util.removeDuplicates(result);

        return result;
    }

    public Iterator<Geometry> getGeometriesIterator(){
        return _geometriesList.iterator();
    }

    //Overrides

    @Override
    public boolean equals(Object obj) {
        //TODO: CHECK
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Geometries))
            return false;

        Geometries geometries = (Geometries) obj;
        ArrayList<Geometry> geometriesList = geometries.get_GeometriesList();

        for (int i = 0; i < _geometriesList.size(); ++i){
            if (!(_geometriesList.get(i).equals(geometriesList.get(i))))
                return false;
        }

        return true;
    }
}
