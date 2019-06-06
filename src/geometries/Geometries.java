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
        ArrayList<Point3D> intersectionsList=new ArrayList<Point3D>();
        ArrayList<Point3D> intersectionsGeometryList;
        Iterator<Intersectable> iter= _geometriesList.iterator();
        while(iter.hasNext())
        {
            intersectionsGeometryList=iter.next().findIntersections(ray);
            intersectionsList.addAll(intersectionsGeometryList);
        }

        Util.removeDuplicates(intersectionsList);

        return intersectionsList;
    }
}
