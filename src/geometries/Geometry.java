package geometries;

import primitives.*;

public abstract class Geometry implements Intersectable, ITransformable {
    protected Color _emission;
    protected Material _material;
    //ADDED NEW FIELDS? UPDATE "EQUALS" AND "TOSTRING" METHODS (IF THEY EXIST).

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

    public Geometry(Geometry geometry){
        _emission = new Color(geometry.get_emission());
        _material = new Material(geometry.get_material());
    }

    //Getters

    public Color get_emission() {
        return new Color(_emission);
    }

    public Material get_material() {
        return _material;
    }

    //Setters

    public void set_emission(Color emission) {
        this._emission = new Color(emission);
    }

    public void set_emission(java.awt.Color emission) {
        this._emission = new Color(emission);
    }

    public void set_material(Material material) {
        this._material = material;
    }

    //Methods

    public abstract Vector3D get_normal(Point3D point);

    public abstract boolean contains(Point3D point);

    public abstract boolean surfaceContains(Point3D point);

    //Overrides

    @Override
    public boolean equals(Object obj) {
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
