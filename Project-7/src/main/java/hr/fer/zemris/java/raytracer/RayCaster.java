package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class used to show the ray caster 
 * and how it renders images on the
 * screen by coloring screen pixels 
 * @author Tomislav Kurtović
 *
 */
public class RayCaster {
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Used to generate a Raytracer in order to show objects and colors on the screen
	 * @return new RayTracer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean bool) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D yAxis = getYAxis(eye, view, viewUp);
				Point3D xAxis = getXAxis(eye, view, viewUp);

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.))
						.add(yAxis.scalarMultiply((vertical) / 2.));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;

				Point3D iMultiplied = xAxis.scalarMultiply((double) horizontal / (width - 1));
				Point3D jMultiplied = yAxis.scalarMultiply((double) vertical / (height - 1));

				for (int y = 0; y < height; y++) {
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
