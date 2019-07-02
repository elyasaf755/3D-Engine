package prefabs;

import geometries.Cuboid;
import geometries.Geometry;
import geometries.SetUnion;
import geometries.Tube;
import primitives.*;

import java.util.ArrayList;

public class Table extends Geometry {

    SetUnion _table;//LHS = Plate, RHS = LEGS

    //Constructors

    public Table(){
        //LEG    R: 220 G: 168 B: 119
        Tube leg1 = new Tube(5,new Ray(new Vector3D(0,1,0)), 60, new Color(java.awt.Color.BLUE));
        leg1.set_emission(new Color(133,94,66));
        leg1.set_material(new Material(0.4,0.4,0.4,0,2));
        leg1.translate(-60,0,-32);

        Tube leg2 = new Tube(leg1);
        leg2.translate(120,0,0);

        Tube leg3 = new Tube(leg2);
        leg3.translate(0,0,50);

        Tube leg4 = new Tube(leg3);
        leg4.translate(-120,0,0);

        SetUnion tableLegs = new SetUnion(leg1, leg2);
        tableLegs = new SetUnion(tableLegs, leg3);
        tableLegs = new SetUnion(tableLegs, leg4);


        Cuboid tablePlate = new Cuboid(80,5,40, new Ray(new Vector3D(0,0,1)));
        tablePlate.set_emission(new Color(133,94,66));
        tablePlate.scale(2);

        _table = new SetUnion(tablePlate, tableLegs);
        _table.rotate(180,0,0);
        _table.translate(0,30,0);

        //TODO: TEST
        updateAABB();
    }

    //Setters

    @Override
    public void set_emission(Color emission) {
        _table.set_emission(emission);
    }

    @Override
    public void set_emission(java.awt.Color emission) {
        _table.set_emission(emission);
    }

    @Override
    public void set_material(Material material) {
        _table.set_material(material);
    }

    public void set_legsColor(Color emission){
        _table.get_rhs().set_emission(emission);
    }

    public void set_legsMaterial(Material material){
        _table.get_rhs().set_material(material);
    }

    public void set_legsColor(java.awt.Color emission){
        _table.get_rhs().set_emission(emission);
    }

    public void set_plateColor(Color emission){
        _table.get_lhs().set_emission(emission);
    }

    public void set_plateColor(java.awt.Color emission){
        _table.get_lhs().set_emission(emission);
    }

    public void set_plateMaterial(Material material){
            _table.get_lhs().set_material(material);
    }

    //Methods

    @Override
    public Vector3D get_normal(Point3D point) {
        return _table.get_normal(point);
    }

    @Override
    public boolean contains(Point3D point) {
        return _table.contains(point);
    }

    @Override
    public boolean surfaceContains(Point3D point) {
        return _table.surfaceContains(point);
    }

    @Override
    public void updateAABB() {
        _table.updateAABB();

        set_min(_table.get_min());
        set_max(_table.get_max());
    }

    @Override
    public ArrayList<GeoPoint> findIntersections(Ray ray) {
       /* //TODO: TEST
        if (!intersects(ray)){
            return new ArrayList<>();
        }*/

        return _table.findIntersections(ray);
    }

    @Override
    public void translate(double x, double y, double z) {
        _table.translate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void rotate(double x, double y, double z) {
        _table.rotate(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double x, double y, double z) {
        _table.scale(x, y, z);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void scale(double scalar) {
        _table.scale(scalar);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Transform _transform) {
        _table.transform(_transform);

        //TODO: TEST
        updateAABB();
    }

    @Override
    public void transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        _table.transform(translation, rotation, scale);

        //TODO: TEST
        updateAABB();
    }
}
