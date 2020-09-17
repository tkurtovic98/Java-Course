package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class is the same as {@link RayCasterParallel} but it
 * has a nice animation to it
 * @author Tomislav Kurtović
 *
 */
public class RayCasterParallel2 {
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}

	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			private Point3D yAxis;
			private Point3D xAxis;
			private Scene scene;
			private short[] red;
			private short[] green;
			private short[] blue;
			private Point3D screenCorner;
			private double horizontal;
			private double vertical;
			private int width;
			private int height;
			private Point3D eye;
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean bool) {
				System.out.println("Započinjem izračune...");
				this.eye = eye;
				this.horizontal = horizontal;
				this.vertical = vertical;
				this.height = height;
				this.width = width;
				
				red = new short[width * height];
				green = new short[width * height];
				blue = new short[width * height];

				yAxis = getYAxis(eye, view, viewUp);
				xAxis = getXAxis(eye, view, viewUp);

				screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.))
						.add(yAxis.scalarMultiply((vertical) / 2.));

				scene = RayTracerViewer.createPredefinedScene2();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Worker(0, height));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Used to calculate xAxis
			 * @param eye eye position 
			 * @param view view
			 * @param viewUp view up vector
			 * @return xAxis ( i vector) 
			 */
			private Point3D getXAxis(Point3D eye, Point3D view, Point3D viewUp) {
				Point3D OG = view.sub(eye).normalize();
				return OG.vectorProduct(getYAxis(eye, view, viewUp)).normalize();
			}

			/**
			 * Used to calculate yAxis
			 * @param eye eye position 
			 * @param view view
			 * @param viewUp view up vector
			 * @return yAxis ( j vector) 
			 */
			private Point3D getYAxis(Point3D eye, Point3D view, Point3D viewUp) {
				Point3D OG = view.sub(eye).normalize();
				Point3D j = viewUp.normalize().sub(OG.scalarMultiply(OG.scalarProduct(viewUp.normalize())));
				return j.modifyNormalize();
			}
			/**
			 * Class that represents the worker of the thread pool
			 * @author Tomislav Kurtović
			 *
			 */
			 class Worker extends RecursiveAction {
				private static final long serialVersionUID = 1L;

				private int yMin;
				private int yMax;
				private int treshold = 16;
				
				public Worker(int yMin, int yMax) {
					this.yMin = yMin;
					this.yMax = yMax;
				}
				
				@Override
				protected void compute() {
					int diff = yMax - yMin;
					if(diff <= treshold) {
						computeDirect();
						return;
					}
					int halfed = diff/2;
					invokeAll(new Worker(yMin, yMin + halfed), new Worker(yMin+ halfed, yMax));
				}

				private void computeDirect() {
					short[] rgb = new short[3];
					int offset = yMin * width;

					Point3D iMultiplied = xAxis.scalarMultiply((double) horizontal / (width - 1));
					Point3D jMultiplied = yAxis.scalarMultiply((double) vertical / (height - 1));

					for (int y = yMin; y < yMax; y++) {
						for (int x = 0; x < width; x++) {
							Point3D screenPoint = screenCorner.add(iMultiplied.scalarMultiply(x))
									.sub(jMultiplied.scalarMultiply(y));
							Ray ray = Ray.fromPoints(eye, screenPoint);

							tracer(scene, ray, rgb);
							red[offset] = rgb[0] > 255 ? 255 : rgb[0];
							green[offset] = rgb[1] > 255 ? 255 : rgb[1];
							blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
							
							offset++;
						}
					}
				}
			}
		};
	}

	/**
	 * Used to color objects on the screen 
	 * @param scene view of objects 
	 * @param ray ray from the eye to the pixel
	 * @param rgb color array
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		List<LightSource> lights = scene.getLights();
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		Point3D normal = closest.getNormal();
		for (LightSource light : lights) {
			Ray lightRay = Ray.fromPoints(light.getPoint(), closest.getPoint());
			RayIntersection lightClosest = findClosestIntersection(scene, lightRay);

			if(lightClosest == null) {
				continue;
			}
			
			if ( lightClosest.getPoint().sub(light.getPoint()).norm() + 0.001 < 
					closest.getPoint().sub(light.getPoint()).norm()) {
				continue;
			}
			
			Point3D r = lightRay.direction.sub(normal.scalarMultiply(2*(lightRay.direction.scalarProduct(normal))));
			
			rgb[0] += light.getR()*(closest.getKdr()*(Math.max(lightRay.direction.scalarProduct(normal),0)) + closest.getKrr() *
					Math.pow(ray.direction.scalarProduct(r),closest.getKrn()));
			rgb[1] += light.getG()*(closest.getKdg()*(Math.max(lightRay.direction.scalarProduct(normal),0)) + closest.getKrg() *
					Math.pow(ray.direction.scalarProduct(r),closest.getKrn()));
			rgb[2] += light.getB()*(closest.getKdb()*(Math.max(lightRay.direction.scalarProduct(normal),0)) + closest.getKrb() *
					Math.pow(ray.direction.scalarProduct(r),closest.getKrn()));
		}
	}

	/**
	 * Used to calculate intersection points of ray from eye 
	 * and objects in the scene
	 * @param scene scene with objects 
	 * @param ray line from eye to pixel 
	 * @return new {@link RayIntersection} object if an intersection exists, null otherwise
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> graphicalObjects = scene.getObjects();
		RayIntersection closest = null;

		for (GraphicalObject object : graphicalObjects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if (intersection == null) {
				continue;
			}
			if(closest == null) {
				closest = intersection;
				continue;
			}
			
			if(intersection.getDistance() < closest.getDistance()) {
				closest = intersection;
			}
			
		}
		return closest;
	}
}