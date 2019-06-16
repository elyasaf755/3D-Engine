package primitives;

public class Material {
    protected double _Kd;
    protected double _Ks;
    protected int _nShininess;//Shininess factor. higher values - higher shininess. range from 0 and on.
    protected double _Kr;//reflection constant: 0->matt, 1->mirror.
    protected double _Kt;//refraction constant: 0->opaque, 1->translucent.

    //ADDED NEW FIELDS? UPDATE "EQUALS" AND "TOSTRING" METHODS (IF THEY EXISTS).


    //Constructors

    public Material(){
        //TODO: change to good values.
        _Kd = 0.3;
        _Ks = 0.3;
        _nShininess = 3;
        _Kr = 0;
        _Kt = 0;
    }

    public Material(Material material){
        _Kd = material.get_Kd();
        _Ks = material.get_Ks();
        _nShininess = material.get_nShininess();
        _Kr = material.get_Kr();
        _Kt = material.get_Kt();
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

    public double get_Kr() {
        return _Kr;
    }

    public double get_Kt() {
        return _Kt;
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

    public void set_Kr(double _Kr) {
        this._Kr = _Kr;
    }

    public void set_Ktd(double _Kt) {
        this._Kt = _Kt;
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
                Util.equals(_nShininess, material.get_nShininess()) &&
                Util.equals(_Kr, material.get_Kr()) &&
                Util.equals(_Kt, material.get_Kt());
    }
}
