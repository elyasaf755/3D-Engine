package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;

import java.util.ArrayList;
import java.util.Iterator;

public class Geometries implements Intersectable {

    protected ArrayList<Intersectable> _geometriesList;


    /********** Constructors ***********/

    public Geometries()
    {
        _geometriesList = new ArrayList<Intersectable>();
    }

    /********** Getters ***********/

    public ArrayList<Intersectable> get_GeometriesList() {
        return _geometriesList;
    }

    /************** Operations ***************/

    public void add_geometry (Intersectable... geometries) {
        for (Intersectable geometry: geometries) {
            _geometriesList.add(geometry);
        }
    }

    /*************** Overrides *****************/

    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        ArrayList<Point3D> result = new ArrayList<Point3D>();

        for (Intersectable geometry : _geometriesList){
            ArrayList<Point3D> intersectionPoints = geometry.findIntersections(ray);
            result.addAll(intersectionPoints);
        }
        /*
        Iterator<Intersectable> iter = _geometriesList.iterator();
        while(iter.hasNext())
        {
            ArrayList<Point3D> intersectionPoints = iter.next().findIntersections(ray);
            result.addAll(intersectionPoints);
        }
         */

        Util.removeDuplicates(result);

        return result;
    }
}
