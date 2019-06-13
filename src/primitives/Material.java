package primitives;

public class Material {
    protected double _Kd;
    protected double _Ks;
    protected int _nShininess;//Shininess factor. higher values - higher shininess. range from 0 and on.
    //ADDED NEW FIELDS? UPDATE "EQUALS" AND "TOSTRING" METHODS (IF THEY EXISTS).


    //Constructors

    public Material(){
        //TODO: change to good values.
        _Kd = 0.3;
        _Ks = 0.3;
        _nShininess = 3;
    }

    public Material(Material material){
        _Kd = material.get_Kd();
        _Ks = material.get_Ks();
        _nShininess = material.get_nShininess();
    }

    //Getters

    public double get_Kd() {
        return _Kd;
    }

    public double get_Ks() {
        return _Ks;
    }

    public int get_nShininess() {
        return _nShininess;
    }

    //Setters

    public void set_Kd(double _Kd) {
        this._Kd = _Kd;
    }

    public void set_Ks(double _Ks) {
        this._Ks = _Ks;
    }

    public void set_nShininess(int _nShininess) {
        this._nShininess = _nShininess;
    }

    //Overrides


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Material))
            return false;

        Material material = (Material) obj;

        return  Util.equals(_Kd, material.get_Kd()) &&
                Util.equals(_Ks, material.get_Ks()) &&
                Util.equals(_nShininess, material.get_nShininess());
    }
}
