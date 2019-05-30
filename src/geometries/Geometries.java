package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Geometries implements Intersectable {

    protected ArrayList<Intersectable> geometriesList;


    /********** Constructors ***********/

    public Geometries()
    {
        geometriesList= new ArrayList<Intersectable>();
    }

    /********** Getters ***********/

    public ArrayList<Intersectable> get_GeometriesList() {
        return geometriesList;
    }

    /************** Operations ***************/

    public void add_geometry (Intersectable... geometries) {
        for (Intersectable geometry: geometries) {
            geometriesList.add(geometry);
        }
    }

    /*************** Overrides *****************/

    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {
        ArrayList<Point3D> intersectionsList=new ArrayList<Point3D>();
        ArrayList<Point3D> intersectionsGeometryList;
        Iterator<Intersectable> iter=geometriesList.iterator();
        while(iter.hasNext())
        {
            intersectionsGeometryList=iter.next().findIntersections(ray);
            intersectionsList.addAll(intersectionsGeometryList);
        }

        Util.removeDuplicates(intersectionsList);

        return intersectionsList;
    }
}
