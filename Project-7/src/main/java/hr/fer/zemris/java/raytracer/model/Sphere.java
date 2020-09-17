package hr.fer.zemris.java.raytracer.model;

/**
 * Class that represents a sphere object in the 
 * scene
 * @author Tomislav Kurtović
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * center of sphere
	 */
	private Point3D center;
	/**
	 * radius of sphere
	 */
	private double radius;
	/**
	 * diffusion coeff for red
	 */
	private double kdr;
	/**
	 * diffusion coeff for green
	 */
	private double kdg;
	/**
	 * diffusion coeff for blue
	 */
	private double kdb;
	/**
	 * reflection coeff for red
	 */
	private double krr;
	/**
	 * reflection coeff for green
	 */
	private double krg;
	/**
	 * reflection coeff for green
	 */
	private double krb;
	/**
	 * reflection coeff for blue
	 */
	private double krn;
	
	/**
	 * Constructor of Sphere object
	 * @param center
	 * @param radius
	 * @param kdr diffusion coeff for red
	 * @param kdg diffusion coeff for green
	 * @param kdb diffusion coeff for blue
	 * @param krr reflection coeff for red
	 * @param krg reflection coeff for green
	 * @param krb reflection coeff for blue
	 * @param krn smsoothnes factor
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	

	/**
	 * {@inheritDoc}
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D difference = center.sub(ray.start);
		
		double t = difference.scalarProduct(ray.direction);
		double d = difference.scalarProduct(difference) - Math.pow(t, 2);
		
		if(d < 0) {
			return null;
		}

		if(d > radius * radius) {
			return null;
		}
		
		double pointBetweenIntersections = Math.sqrt(Math.pow(radius,2) - d);
		
		double tMin = t - pointBetweenIntersections;
		double tMax = t + pointBetweenIntersections;
		
		if(tMin > tMax) {
			double distance = tMin;
			tMin = tMax;
			tMax = distance;
		}
		
		if(tMin < 0) {
			tMin = tMax;
			if(tMin < 0) {
				return null;
			}
		}
		
		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(tMin));
		return new RayIntersectionExt(intersection, tMin, true,
				kdr, kdg, kdb, krr, krg, krb, krn, center);
	}
}
