package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector3D;

public abstract class Geometry implements Intersectable {
    private Color _emission;
    private Material _material;
    //ADDED NEW FIELDS? UPDATE "EQUALS" AND "TOSTRING" METHODS (IF THEY EXIST).

    //TODO: Implement class

    //Constructors

    public Geometry(){
        _emission = new Color(java.awt.Color.WHITE);
        _material = new Material();
    }

    public Geometry(Color emission){
        _emission = new Color(emission);
        _material = new Material();
    }

    public Geometry(Material material){
        _emission = new Color(java.awt.Color.WHITE);
        _material = new Material(material);
    }

    public Geometry(Color emission, Material material){
        _emission = new Color(emission);
        _material = new Material(material);
    }

    //Getters

    public Color get_emission() {
        return _emission;
    }

    public Material get_material() {
        return _material;
    }

    //Setters

    public void set_emission(Color _emission) {
        this._emission = new Color(_emission);
    }

    public void set_emission(java.awt.Color _emission) {
        this._emission = new Color(_emission);
    }

    public void set_material(Material _material) {
        this._material = _material;
    }

    //Methods

    public abstract Vector3D get_normal(Point3D point3D);

    //Overrides

    @Override
    public boolean equals(Object obj) {
        //TODO: CHECK
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Geometry))
            return false;

        Geometry geometry = (Geometry) obj;

        return _emission.equals(geometry.get_emission()) &&
                _material.equals(geometry.get_material());
    }
}
