package primitives;

public class Material {
    protected double _Kd;//diffuse
    protected double _Ks;//specular
    protected double _Kr;//reflection constant: 0->matt, 1->mirror.
    protected double _Kt;//refraction constant: 0->opaque, 1->translucent.
    protected int _shininess;//Shininess factor. lower values - higher shininess. range from 0 and on.


    //ADDED NEW FIELDS? UPDATE "EQUALS" AND "TOSTRING" METHODS (IF THEY EXISTS).

    public static final Material GLASS = new Material(0.3, 0.3, 0.2, 0.35, 3);


    //Constructors

    public Material(){
        //TODO: change to good values.
        _Kd = 0.3;
        _Ks = 0.3;
        _shininess = 3;
        _Kr = 0;
        _Kt = 0;
    }

    public Material(double kd, double ks, double kr, double kt, int shininess){
        _Kd = kd;
        _Ks = ks;
        _Kr = kr;
        _Kt = kt;
        _shininess = shininess;
    }

    public Material(Material material){
        _Kd = material.get_Kd();
        _Ks = material.get_Ks();
        _shininess = material.get_shininess();
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

    public int get_shininess() {
        return _shininess;
    }

    public double get_Kr() {
        return _Kr;
    }

    public double get_Kt() {
        return _Kt;
    }

    //Setters

    public void set_Kd(double Kd) {
        if (!Util.equals(Kd, 0)){
            if (Kd < 0){
                throw new IllegalArgumentException("Kd can't be negative value");
            }
            else{
                this._Kd = Kd;
            }
        }
        else{
            this._Kd = Kd;
        }
    }

    public void set_Ks(double Ks) {
        if (!Util.equals(Ks, 0)){
            if (Ks < 0){
                throw new IllegalArgumentException("Ks can't be negative value");
            }
            else{
                this._Ks = Ks;
            }
        }
        else{
            this._Ks = Ks;
        }
    }

    public void set_shininess(int _shininess) {
        this._shininess = _shininess;
    }

    public void set_Kr(double Kr) {
        if (!Util.equals(Kr, 0)){
            if (Kr < 0){
                throw new IllegalArgumentException("Kr can't be negative value");
            }
            else{
                this._Kr = Kr;
            }
        }
        else{
            this._Kr = Kr;
        }
    }

    public void set_Kt(double Kt) {
        if (!Util.equals(Kt, 0)) {
            if (Kt < 0) {
                throw new IllegalArgumentException("Kt can't be negative value");
            }
            else {
                this._Kt = Kt;
            }
        }
        else {
            this._Kt = Kt;
        }
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
                Util.equals(_shininess, material.get_shininess()) &&
                Util.equals(_Kr, material.get_Kr()) &&
                Util.equals(_Kt, material.get_Kt());
    }
}
