package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import geometries.IGeometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Geometries {

    protected ArrayList<IGeometry> _geometriesList;


    /********** Constructors ***********/

    public Geometries()
    {
        _geometriesList = new ArrayList<IGeometry>();
    }

    /********** Getters ***********/

    public ArrayList<IGeometry> get_GeometriesList() {
        return _geometriesList;
    }

    /************** Operations ***************/

    public void add_geometry (IGeometry... geometries) {
        for (IGeometry geometry: geometries) {
            _geometriesList.add(geometry);
        }
    }

    /*************** Overrides *****************/

    //@Override
    public Map<IGeometry, ArrayList<Point3D>> getSceneRayIntersections(Ray ray) {
        Map<IGeometry, ArrayList<Point3D>> intersectionPoints = new HashMap<IGeometry, ArrayList<Point3D>>();;

        for (IGeometry geometry : _geometriesList){
            ArrayList<Point3D> geometryIntersectionPoints = geometry.findIntersections(ray);
            intersectionPoints.put(geometry , geometryIntersectionPoints);
        }
        /*
        Iterator<Intersectable> iter = _geometriesList.iterator();
        while(iter.hasNext())
        {
            ArrayList<Point3D> intersectionPoints = iter.next().findIntersections(ray);
            result.addAll(intersectionPoints);
        }
         */

        //Util.removeDuplicates(result);todo: to implement for map

        return intersectionPoints;
    }
}
