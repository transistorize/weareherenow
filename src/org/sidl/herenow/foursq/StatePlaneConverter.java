package org.sidl.herenow.foursq;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.log;
import static java.lang.Math.PI;

/**
 * Utility for converting latitude and longitude to the NY Long Island (#3104)
 * state-plane projection.
 *
 * Note: The original code for this was pulled from a Processing script, and originally worked
 * with just floats, but was up-converted to doubles for greater accuracy. The results are cast
 * back to floats at the end.
 */
public class StatePlaneConverter
{
  /**
   * Converts latitude, longitude to an array of the [x (East), y (North)] NYLI SP coordinates.
   * @param latitude  the latitude in degrees, decimal format
   * @param longitude  the longitude in degrees, decimal format
   * @return [x,y] state plane coordinate values (in US survey feet).
   */
  public static float[] convertTo(final float latitude, final float longitude)
  {
    final double lat = latitude;
    final double lon = longitude;
    final long a = 6378137;                     // semi-major radius of ellipsoid, meters (NAD 83)
    final double f = 0.003352810681225;         // flattening, 1/f = 298.25722
    final double theta0 = 40.16666666666666;
    final double theta1 = 40.16666666666666;
    final double theta2 = 40.66666666666666;

    double e = Math.pow(((2 * f) - (f * f)), .5);
    double m1 = calcM(theta1, e);
    double m2 = calcM(theta2, e);
    double t = calcT(lat, e);
    double t0 = calcT(theta0, e);
    double t1 = calcT(theta1, e);
    double t2 = calcT(theta2, e);
    double n = (log(m1) - log(m2)) / (log(t1) - log(t2));
    double F = (m1 / (n * Math.pow(t1, n)));
    double rho0 = a * F * Math.pow(t0, n);

    double gamma = n * (radians(lon) - radians(-74));
    double rho = a * F * (float) Math.pow(t, n);

    double N = rho0 - (rho * cos(gamma));
    N *= 3.2808399;
    double E = 300000 + rho * sin(gamma);
    E *= 3.2808399;
    float[] xy = {(float)E, (float)N};
    /*
    System.out.println("f:" + f + "\ne:" + e + "\nm1:" + m1
        + "\nm2:" + m2 + "\nt1:" + t1 + "\nt2:" + t2
        + "\nn:" + n + "\nF:" + F + "\nrho:" + rho
        + "\nOutput coordinates:" + N + " North, " + E + " East");
        */
    return xy;
  }

  private static double calcM(double theta, double e)
  {
    final double sinTheta = e * sin(radians(theta));
    return cos(radians(theta)) / Math.pow(1 - sinTheta * sinTheta, .5);
  }

  private static double calcT(double theta, double e)
  {
    final double radiansTheta = radians(theta);
    double numer = tan((PI / 4) - (radiansTheta / 2));
    
    final double sinTheta = e * sin(radiansTheta);
    double num2 = 1 - sinTheta;
    double denom2 = 1 + sinTheta;
    double denom = (float) Math.pow((num2 / denom2), e / 2);
    return numer / denom;
  }

  private static double radians(double f)
  {
    return f * DEG_TO_RAD;
  }


  private static final double DEG_TO_RAD = (double) (Math.PI / 180.0d); //number of radians in a degree

}
