package primitives;

import java.util.ArrayList;

public class Util {
    // It is binary, equivalent to ~1/1,000,000,000,000 in decimal (12 digits)
    private static final int ACCURACY = -40;

    // double store format (bit level): seee eeee eeee (1.)mmmm � mmmm
    // 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
    // the number is m+2^e where 1<=m<2
    // NB: exponent is stored "normalized" (i.e. always positive by adding 1023)
    private static int getExp(double num) {
        // 1. doubleToRawLongBits: "convert" the stored number to set of bits
        // 2. Shift all 52 bits to the right (removing mantissa)
        // 3. Zero the sign of number bit by mask 0x7FF
        // 4. "De-normalize" the exponent by subtracting 1023
        return (int) ((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
    }

    public static double usubtract(double lhs, double rhs) {
        int lhsExp = getExp(lhs);
        int rhsExp = getExp(rhs);

        // if other is too small relatively to our coordinate
        // return the original coordinate
        if (rhsExp - lhsExp < ACCURACY) return lhs;

        // if our coordinate is too small relatively to other
        // return negative of other coordinate
        if (lhsExp - rhsExp < ACCURACY) return -rhs;

        double result = lhs - rhs;
        int resultExp = getExp(result);
        // if the result is relatively small - tell that it is zero
        return resultExp - lhsExp < ACCURACY ? 0.0 : result;
    }

    public static double uadd(double lhs, double rhs) {
        int lhsExp = getExp(lhs);
        int rhsExp = getExp(rhs);

        // if other is too small relatively to our coordinate
        // return the original coordinate
        if (rhsExp - lhsExp < ACCURACY) return lhs;

        // if our coordinate is too small relatively to other
        // return other coordinate
        if (lhsExp - rhsExp < ACCURACY) return rhs;

        double result = lhs + rhs;
        int resultExp = getExp(result);
        // if the result is relatively small - tell that it is zero
        return resultExp - lhsExp < ACCURACY ? 0.0 : result;
    }

    public static double uscale(double lhs, double factor) {
        double deltaExp = getExp(factor - 1);
        return deltaExp < ACCURACY ? lhs : lhs * factor;
    }

    public static double udiv(double numerator, double denominator) {
        return alignZero(numerator / denominator);
    }

    public static boolean isZero(double number) {
        return getExp(number) < ACCURACY;
    }

    public static boolean isOne(double number) {
        return getExp(number - 1) < ACCURACY;
    }

    public static double alignZero(double number) {
        return getExp(number) < ACCURACY ? 0.0 : number;
    }

    public static boolean isNegative(double num) {
        if (isZero(num) == true) {
            return false;
        }
        if (num / Math.abs(num) == -1) {
            return true;
        }

        return false;
    }

    public static <T> void removeDuplicates(ArrayList<T> list) {
        if (list.size() == 0 || list.size() == 1)
            return;

        ArrayList<T> newList = new ArrayList<T>();

        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        list.clear();

        for (T element : newList) {
            list.add(element);
        }
    }

    public static double squared(double number) {
        return uscale(number, number);
    }

    public static double[] quadraticRoots(double a, double b, double c) {
        double[] result = new double[2];

        //b^2-4*a*c
        double dt = Util.usubtract(Util.squared(b), Util.uscale(4, Util.uscale(a, c)));

        //root1 = (-b-sqrt(dt))*[(2a)^(-1)]
        result[0] = Util.uscale(Util.usubtract(-b, Math.sqrt(dt)), Math.pow(Util.uscale(2, a), -1));

        //root2 = (-b+sqrt(dt))*[(2a)^(-1)]
        result[1] = Util.uscale(Util.uadd(-b, Math.sqrt(dt)), Math.pow(Util.uscale(2, a), -1));

        if (result[0] == result[1]) {
            double[] res = new double[1];
            return res;
        }

        return result;
    }

    //solves ax^2 + bx + c = 0
    public static Complex[] quadraticRoots(Complex a, Complex b, Complex c){
        Complex zero = new Complex();
        if (a.equals(zero))
        {
            //No solutions for x
            if (b.equals(zero))
            {
                return new Complex[]{};
            }
            else//bx + c = 0 => x = -c / b => 1 solution
            {
                Complex[] roots = {c.div(b).mult(-1)};

                return roots;   // there is a single solution.
            }
        }
        else
        {
            Complex delta = b.squared().sub(a.mult(c).mult(4));

            if (delta.equals(zero))//x1,2 = -b / 2a  => 1 solution
            {
                Complex[] roots = {b.div(a.mult(2)).mult(-1)};

                return roots;
            }
            else//2 solutions
            {
                // There are two distinct real roots.
                Complex numinator = delta.sqrt();
                Complex denom = a.mult(2);

                Complex[] roots = {
                        b.mult(-1).add(numinator).div(denom),
                        b.mult(-1).sub(numinator).div(denom),
                };

                return roots;
            }
        }
    }

    public static double[] cubicRoots(double a, double b, double c, double d){

        double f = ((3*c / a) - (b*b)/(a*a)) / 3;
        double g = ((2*b*b*b) / (a*a*a) - ((9*b*c) / (a*a)) + ((27*d) / a)) / 27;
        double h = ((g*g) / 4) + ((f*f*f) / 27);

        if (Util.equals(f, 0) && Util.equals(g, 0) && Util.equals(h, 0)){
            double[] result = {
                    Math.pow(d / a, 1.0 / 3.0)*(-1),
                    Math.pow(d / a, 1.0 / 3.0)*(-1),
                    Math.pow(d / a, 1.0 / 3.0)*(-1)
            };
        }
        else if (h > 0){
            double R = -(g / 2) + Math.sqrt(h);
            double S = Math.pow(R, 1D / 3.0);
            double T = -(g / 2) - Math.sqrt(h);
            double U = Math.pow(T, 1D / 3.0);

            double[] result = {
                    (S + U) - (b / (3*a))
            };

            return result;
        }
        else if (Util.equals(h, 0) || h < 0){
            double i = Math.sqrt((g*g) / 4 - h);
            double j = Math.pow(i, 1.0 / 3.0);
            double k = Math.acos(-(g / (2*i)));
            double L = j * (-1);
            double M = Math.cos(k / 3);
            double N = (Math.sqrt(3)*Math.sin(k / 3));
            double P = (b / (3*a))*(-1);

            double[] result = {
                    2*j*Math.cos(k / 3) - (b / (3*a)),
                    L*(M + N) + P,
                    L*(M - N) + P
            };

            return result;
        }

        return new double[]{};
    }

    public static Complex[] cubicRoots(Complex a, Complex b, Complex c, Complex d){
        Complex zero = new Complex(0,0);

        if (a.equals(zero))//bx^2 + cx + d = 0 ==> quadratic equation
        {
            return quadraticRoots(b, c, d);
        }

        b = b.div(a);
        c = c.div(a);
        d = d.div(a);

        Complex S = b.div(3);
        Complex D = c.div(3).sub(S.squared());
        Complex E = S.pow(3).add(d.sub(S.mult(c).div(2)));
        Complex Froot = E.squared().add(D.pow(3));
        Complex F = Froot.mult(-1).sub(E);

        if (F.equals(zero))
        {
            F = Froot.sub(E);
        }

        Complex[] roots = new Complex[3];

        for (int i=0; i < 3; ++i)
        {
            Complex G = F.cbrt(i);
            roots[i] = G.sub(D.div(G)).sub(S);
        }

        return roots;
    }

    public static double[] quarticRoots(double a, double b, double c, double d, double e) {
        if (a != 0){
            b = b / a;
            c = c / a;
            d = d / a;
            e = e / a;
            a = a / a;
        }

        double f = c - (3*b*b / 8);
        double g = d + (b*b*b / 8) - (b*c / 2);
        double h = e - (3*b*b*b*b / 256) + (b*b*c/16) - (b*d / 4);

        double A = 1;
        double B = f / 2;
        double C = ((f*f - 4*h) / 16);
        double D = -g*g / 64;

        double[] cubicRoots = Util.cubicRoots(A, B, C, D);

        double p = Double.NaN;
        double q = Double.NaN;

        for (double root : cubicRoots){
            if (!Util.equals(root, 0)){
                if (Double.isNaN(p)){
                    p = Math.sqrt(root);
                }
                else if (Double.isNaN(q)) {
                    q = Math.sqrt(root);
                }
            }
        }

        double r = -g / (8*p*q);
        double s = b / (4*a);

        double[] result = {
                p + q + r - s,
                p - q - r - s,
                -p + q - r - s,
                -p - q + r -s
        };

        return result;
    }

    public static double[] realQuarticRoots(double a, double b, double c, double d, double e){
        return Complex.getRealNumbers(
                Util.quarticRoots(
                        new Complex(a, 0),
                        new Complex(b, 0),
                        new Complex(c, 0),
                        new Complex(d, 0),
                        new Complex(e, 0)
                        )
        );
    }

    public static Complex[] quarticRoots(Complex a, Complex b, Complex c, Complex d, Complex e) {
        Complex zero = new Complex();
        if (a.equals(zero))
        {
            return cubicRoots(b, c, d, e);
        }

        // See "Summary of Ferrari's Method" in http://en.wikipedia.org/wiki/Quartic_function

        b = b.div(a);
        c = c.div(a);
        d = d.div(a);
        e = e.div(a);

        Complex b2 = b.squared();
        Complex b3 = b.mult(b2);
        Complex b4 = b2.squared();

        Complex alpha = b2.mult(-3.0 / 8.0).add(c);//f
        Complex beta  = b3.div(8).sub(b.mult(c).div(2)).add(d);//g
        Complex gamma = b4.mult(-3.0 / 256).add(b2.mult(c).div(16)).sub(b.mult(d).div(4)).add(e);//h

        Complex alpha2 = alpha.squared();
        Complex t = b.div(-4);

        if (beta.equals(zero))
        {
            Complex rad = (alpha2.sub(gamma.mult(4))).sqrt();
            Complex r1 = (rad.sub(alpha).div(2)).sqrt();
            Complex r2 = ((alpha.mult(-1).sub(rad)).div(2)).sqrt();

            Complex[] roots = {
                    t.add(r1),
                    t.sub(r1),
                    t.add(r2),
                    t.sub(r2)
            };

            return roots;
        }
        else
        {
            Complex alpha3 = alpha.mult(alpha2);
            Complex P = (alpha2.div(12).add(gamma)).mult(-1);
            Complex Q = alpha3.div(-108).add(alpha.mult(gamma).div(3)).sub(beta.squared().div(8));
            Complex R = Q.div(-2).add((Q.squared().div(4).add(P.pow(3).div(27))).sqrt());
            Complex U = R.cbrt(0);
            Complex y = alpha.mult(-5.0 / 6.0).add(U);

            if (U.equals(zero))
            {
                y = y.sub(Q.cbrt(0));
            }
            else
            {
                y = y.sub(P.div(U.mult(3)));
            }

            Complex W = (alpha.add(y.mult(2))).sqrt();

            Complex r1 = ((alpha.mult(3).add(y.mult(2)).add(beta.mult(2).div(W))).mult(-1)).sqrt();
            Complex r2 = ((alpha.mult(3).add(y.mult(2)).sub(beta.mult(2).div(W))).mult(-1)).sqrt();

            Complex[] roots = {
                    t.add((W.sub(r1)).div(2)),
                    t.add((W.add(r1)).div(2)),
                    t.add((W.mult(-1).sub(r2)).div(2)),
                    t.add((W.mult(-1).add(r2)).div(2)),
            };

            return roots;
        }
    }

    public static int sign(double num){
        return num < 0 ? -1 : num > 0? 1 : 0;
    }

    public static boolean equals(double lhs, double rhs) {
        return new Coordinate(lhs).subtract(new Coordinate(rhs)).getCoord() == 0.0;
    }
}
