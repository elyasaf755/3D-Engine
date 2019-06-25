package primitives;

import java.util.ArrayList;

public class Complex {
    private double _real;
    private double _imaginary;

    //Constructors

    public Complex(){
        _real = 0;
        _imaginary = 0;
    }

    public Complex(double real, double imaginary){
        _real = real;
        _imaginary = imaginary;
    }

    public Complex(Complex other){
        this._real = other.get_real();
        this._imaginary = other.get_imaginary();
    }

    //Getters

    public double get_real() {
        return _real;
    }

    public double get_imaginary() {
        return _imaginary;
    }

    //Setters

    public void set_real(double _real) {
        this._real = _real;
    }

    public void set_imaginary(double _imaginary) {
        this._imaginary = _imaginary;
    }

    public Complex add(Complex number){
        return new Complex(this._real + number.get_real(),
                       this._imaginary + number.get_imaginary()
        );
    }

    public Complex add(double scalar){
        return new Complex(this._real + scalar,
                this._imaginary
        );
    }

    public Complex sub(Complex number){
        return new Complex(this._real - number.get_real(),
                this._imaginary - number.get_imaginary()
        );
    }

    public Complex sub(double scalar){
        return new Complex(
                this._real - scalar,
                     this._imaginary
        );
    }

    public Complex mult(Complex number){
        double real = this._real*number.get_real() - this._imaginary*number.get_imaginary();
        double imaginary = this._real*number.get_imaginary() + this._imaginary*number._real;

        return new Complex(real, imaginary);
    }

    public Complex mult(double scalar){
        return new Complex(this._real*scalar, this._imaginary*scalar);
    }

    public Complex squared(){
        return this.mult(this);
    }

    public Complex conjugate(){
        return new Complex(this._real, -this._imaginary);
    }

    //Inverse - Z * (1 / Z) = 1
    public Complex reciprocal(){
        double scalar = this._real * this._real + this._imaginary*this._imaginary;

        return new Complex(this._real / scalar, -this._imaginary / scalar);
    }

    //z1 / z2 = z1 * (1 / z2)
    public Complex div(Complex number){
        return this.mult(number.reciprocal());
    }

    public Complex div(double scalar){
        return this.mult(1.0 / scalar);
    }

    //e ^ Z
    public Complex exp(){
        double re = this._real;
        double im = this._imaginary;

        double exp = Math.exp(re);

        return new Complex(Util.uscale(exp, Math.cos(im)),
                           Util.uscale(exp, Math.sin(im))
        );
    }

    public Complex sin(){
        double re = this._real;
        double im = this._imaginary;

        return new Complex(Math.sin(re) * Math.cosh(im),
                       Math.cos(re) * Math.sinh(im)
        );
    }

    public Complex cos(){
        double re = this._real;
        double im = this._imaginary;

        return new Complex(Math.cos(re) * Math.cosh(im),
                -Math.sin(re) * Math.sinh(im)
        );
    }

    //tan(Z) = sin(Z) / cos(z);
    public Complex tan(){
        return this.sin().div(this.cos());
    }

    public double lengthSquared(){
        return this._real*this._real + this._imaginary*this._imaginary;
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    //sqrt(z) = sqrt(|r|)*(z+r)/|z+r|
    public Complex sqrt(){
        double re = this._real;
        double im = this._imaginary;

        if (Util.equals(im, 0)){

            if (Util.equals(re, 0) || re > 0){
                return new Complex(Math.sqrt(re), 0);
            }

            return new Complex(0, Math.sqrt(Math.abs(re)));
        }

        double r = this.length();
        Complex temp = (this.add(r));

        return temp.div(temp.length()).mult(Math.sqrt(r));
    }

    public Complex sqrt(int index){
        double n = 0.5;

        double r = this.length();
        double rn = Math.pow(r, n);
        double angle = this.angle();
        double twoPi = 2*Math.PI;
        double theta = (angle + twoPi*index)*n;

        return Complex.cis(theta).mult(rn);
    }

    //Cubic root - z ^(1/3)
    public Complex cbrt(){
        return this.pow(1.0 / 3.0);
    }

    public Complex cbrt(int index){
        double r = this.length();
        double rn = Math.pow(r, 1.0 / 3.0);
        double twoPi = 2*Math.PI;
        double angle = this.angle();
        double theta = ((twoPi*index) + angle) / 3;

        return Complex.cis(theta).mult(rn);
    }

    //tan(angle) = imaginary / real => angle = arctan(imaginary / real)
    public double angle(){
        double re = this._real;
        double im = this._imaginary;

        double tan = im / re;

        if (re >= 0)
            return Math.atan(tan);
        else
            return Math.atan(tan)+Math.PI;
    }

    public static Complex cis(double angle){
        return new Complex(Math.cos(angle), Math.sin(angle));
    }

    public Complex pow(double n){
        double r = this.length();
        double ra = Math.pow(r, n);
        double angle = this.angle();
        double arg = n*angle;

        return Complex.cis(arg).mult(ra);
    }

    public Complex pow(double n, int index){
        double r = this.length();
        double rn = Math.pow(r, n);
        double twoPi = 2*Math.PI;
        double angle = this.angle();
        double theta = ((twoPi*index) + angle) * n;

        return Complex.cis(theta).mult(rn);
    }

    public static double[] getRealNumbers(Complex[] numbers){
        ArrayList<Complex> filtered = new ArrayList<>();

        for (Complex number : numbers){
            if (Util.equals(number.get_imaginary(), 0) && !Double.isNaN(number.get_real())){
                filtered.add(number);
            }
        }

        int size = filtered.size();
        double[] result = new double[size];

        for (int i = 0; i < size; ++i){
            result[i] = filtered.get(i).get_real();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Complex other = (Complex) obj;

        return Util.equals(this._real, other.get_real()) &&
                Util.equals(this._imaginary, other.get_imaginary());
    }

    @Override
    public String toString() {
        if (this._imaginary < 0){
            return String.valueOf(this._real) + String.valueOf(this._imaginary) + "i";
        }

        return String.valueOf(this._real) + "+" + String.valueOf(this._imaginary) + "i";
    }
}
