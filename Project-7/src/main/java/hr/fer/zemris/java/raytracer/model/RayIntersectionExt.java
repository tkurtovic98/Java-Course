package hr.fer.zemris.java.raytracer.model;

/**
 * Class that represents a {@link RayIntersection} 
 * of ray and some object in the scene
 * @author Tomislav Kurtović
 *
 */
public class RayIntersectionExt extends RayIntersection {
	/**
	 * point of intersection
	 */
	Point3D point;
	/**
	 * center of intersected sphere
	 */
	Point3D centerOfSphere;
	/**
	 * distance from ray start to intersection point
	 */
	double distance;
	/**
	 * specifies whether the intersection is an inner or outer one
	 */
	boolean outer;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	public RayIntersectionExt(Point3D point, double distance, boolean outer, double kdr, double kdg, double kdb,
			double krr, double krg, double krb, double krn,Point3D centerOfSphere) {
		super(point,distance,outer);
		this.point = point;
		this.distance = distance;
		this.outer = outer;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
		this.centerOfSphere = centerOfSphere;
	}

	protected RayIntersectionExt(Point3D point, double distance, boolean outer) {
		super(point, distance, outer);
	}

	/**
	 * Gets kdb of surface
	 */
	@Override
	public double getKdb() {
		return kdb;
	}

	/**
	 * Gets kdg of surface
	 */
	@Override
	public double getKdg() {
		return kdg;
	}

	/**
	 * Gets kdr of surface
	 */
	@Override
	public double getKdr() {
		return kdr;
	}

	/**
	 * Gets krb of surface
	 */
	@Override
	public double getKrb() {
		return krb;
	}

	/**
	 * Gets krg of surface
	 */
	@Override
	public double getKrg() {
		return krg;
	}
	
	/**
	 * Gets krn of surface
	 */
	@Override
	public double getKrn() {
		return krn;
	}
	
	/**
	 * Gets krr of surface
	 */
	@Override
	public double getKrr() {
		return krr;
	}
	/**
	 * Returns normal
	 */
	@Override
	public Point3D getNormal() {
		return centerOfSphere.sub(point).normalize();
	}
}
