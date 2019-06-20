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
            double S = Math.pow(R, 1.0 / 3.0);
            double T = -(g / 2) - Math.sqrt(h);
            double U = Math.pow(T, 1.0 / 3.0);

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


    public static boolean equals(double lhs, double rhs) {
        return new Coordinate(lhs).subtract(new Coordinate(rhs)).getCoord() == 0.0;
    }
}
