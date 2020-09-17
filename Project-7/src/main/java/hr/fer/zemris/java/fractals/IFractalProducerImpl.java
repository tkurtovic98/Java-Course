package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that is used to produce fractals based 
 * on complex polynomials 
 * 
 * @author Tomislav Kurtović
 *
 */
public class IFractalProducerImpl implements IFractalProducer {
	/**
	 * list of roots of polynomial
	 */
	private List<Complex> roots;
	/**
	 * rooted polynomial
	 */
	private static ComplexRootedPolynomial rootedPolynom ;
	/**
	 * complex polynomial
	 */
	private static ComplexPolynomial complexPolynomial;
	/**
	 * covergence treshold 
	 */
	private static double convergenceTreshold = 0.001;
	/**
	 * maximum number of iterations before stating that 
	 * the point does not diverge
	 */
	private static int maxIter = 64;
	
	/**
	 * Root treshold 
	 */
	private static double rootTreshold = 0.002;
	
	/**
	 * thread pool
	 */
	private ExecutorService pool ;
	/**
	 * List of results
	 */
	List<Future<Void>> results;
	
	/**
	 * number of processors
	 */
	private int numberOfProcessors;
	
	/**
	 * Default constructor 
	 * @param roots
	 */
	public IFractalProducerImpl(List<Complex> roots) {
		super();
		this.roots = roots;
		numberOfProcessors = Runtime.getRuntime().availableProcessors();
		pool = Executors.newFixedThreadPool(8 *numberOfProcessors, new DameonicThreadFactory());
		results = new ArrayList<>();
		getPolynom();
	}

	/**
	 * Used to instantiate {@link ComplexRootedPolynomial}
	 * and then convert it to a {@link ComplexPolynomial}
	 * 
	 */
	private void getPolynom() {
		Complex[] factors = roots.toArray(new Complex[roots.size()]);
		rootedPolynom = new ComplexRootedPolynomial(Complex.ONE,factors);
		complexPolynomial = rootedPolynom.toComplexPolynom();
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		short[]data = new short[width*height];
		
		int forMaxY = height/(8*numberOfProcessors);
		
		for(int i = 0; i < 8 * numberOfProcessors; i++) {
			int yMin = i*forMaxY;
			int yMax = (i+1)*forMaxY-1;
			if(i==forMaxY-1) {
				yMax = height-1;
			}
			Worker worker = new Worker(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
			results.add(pool.submit(worker));
		}
		for(Future<Void> job : results) {
			while(true) {
				try {
					job.get();
					break;
				} catch (InterruptedException | ExecutionException e) {
				}
			}
				
		}
		
		observer.acceptResult(data, (short)(complexPolynomial.order() + 1), requestNo);
	}

	/**
	 * Used to map pixel to complex plane
	 * @param x x value of current pixel
	 * @param y y value of current pixel
	 * @param xMin min x raster
	 * @param yMin min y raster
	 * @param xMax max x raster
	 * @param yMax max y raster
	 * @param reMin min real component
	 * @param reMax max real component
	 * @param imMin min imaginary component
	 * @param imMax max imaginary component
	 * @return new mapped complex number
	 */
	private static Complex mapToComplex(int x, int y, int xMin, int yMin, int xMax, int yMax, double reMin, double reMax, double imMin,
			double imMax) {
		
		double real =  (double)x/xMax *(reMax - reMin)+reMin;
		double im = (double)(yMax - y)/yMax *(imMax - imMin) + imMin;
		
		return new Complex(real,im);
	}
	
	/**
	 * Class that represents a worker that
	 * has to do part of a certain job and 
	 * then return his work 
	 * @author Tomislav Kurtović
	 *
	 */
	private static class Worker implements Callable<Void> {
		/**
		 * Minimum real
		 */
		double reMin;
		/**
		 * Maximum real
		 */
		double reMax;
		/**
		 * Minimum imaginary
		 */
		double imMin;
		/**
		 * Maximum real
		 */
		double imMax;
		/**
		 * Screen width
		 */
		int width;
		/**
		 * Screen height
		 */
		int height;
		/**
		 * Minimum y used in parallelization
		 */
		int yMin;
		/**
		 * Maximum y used in parallelization
		 */
		int yMax;
		/**
		 * Arra containing information about the numbers
		 */
		short[] data;
		/**
		 * Atomic bool
		 */
		AtomicBoolean cancel;
		
		/**
		 * Default constructor
		 * @param reMin minimum real 
		 * @param reMax maximum real
		 * @param imMin minimum imaginary
		 * @param imMax maximum imaginary
		 * @param width width
		 * @param height height
		 * @param yMin starting y of this worker
		 * @param yMax end y of this worker
		 * @param data data array
		 */
		
		public Worker(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public Void call() throws Exception {
			
			int offset = yMin * width;
			ComplexPolynomial derived = complexPolynomial.derive();
			
			for(int y = yMin; y <=yMax; y++) {
				if(cancel.get()) break;
				
				for(int x= 0; x <= width-1; x++) {
					
					Complex zn = mapToComplex(x,y,0,yMin,width-1,height-1,reMin,reMax,imMin,imMax);
					Complex znold;
					int iter = 0;
					do {
						Complex numerator = rootedPolynom.apply(zn);
						Complex denominator = derived.apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						iter++;
					}while(znold.sub(zn).module() > convergenceTreshold && iter < maxIter);
					short index =(short) rootedPolynom.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index+1);
				}
				
			}
			return null;
		}
	}

	/**
	 * Class that is used as an implementation of the ThreadFactory 
	 * @author Tomislav Kurtović
	 *
	 */
	private static class DameonicThreadFactory implements ThreadFactory {
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = Executors.defaultThreadFactory().newThread(r);
			thread.setDaemon(true);
			return thread;
		}
	}

}
