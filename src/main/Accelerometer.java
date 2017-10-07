package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Accelerometer {

	private static Accelerometer instance;
	private HashMap<Long, AccelerometerData> hashMap = new HashMap<>();

	protected HashMap<Long, AccelerometerData> getHashMap() {
		return this.hashMap;
	}

	protected void addHashMapEntry(Long dataEntryNumber, AccelerometerData data) {
		this.hashMap.put(dataEntryNumber, data);
	}

	private Accelerometer() {

	}

	public static Accelerometer getInstance() {
		if (instance == null) {
			instance = new Accelerometer();
		}
		return instance;
	}

	/*
	 * time smoothing constant for low-pass filter 0 <= alpha <= 1 ; a smaller
	 * value basically means more smoothing See:
	 * http://en.wikipedia.org/wiki/Low-pass_filter#Discrete-time_realization
	 */
	static final double ALPHA = 0.15f;

	protected static float[] lowPass(float[] input, float[] output) {
		if (output == null)
			return input;

		for (int i = 0; i < input.length; i++) {
			output[i] = (float) (output[i] + ALPHA * (input[i] - output[i]));
		}
		return output;
	}

	/**
	 * 
	 */
	static final float MAGNIFICATION_FACTOR = 1000f;
	static final int accelerometer_period = 10;
	static final boolean USE_LOW_PASS = false;
	static final int FORCE_CALC_DIVIDE_FACTOR = 10;

	protected static double parse_acclr_data(int[] raw_values) throws Exception {
		if (raw_values == null)
			return 0.0;
		float[] accel_input = new float[3];
		float[] accel_output = new float[3];
		float[] accel_max_values = new float[3];
		double distance_vector = 0;
		double x_dist;
		double y_dist;
		double z_dist;

		accel_input[0] = raw_values[0] / 1000f;
		accel_input[1] = raw_values[1] / 1000f;
		accel_input[2] = raw_values[2] / 1000f;

		if (USE_LOW_PASS)
			accel_output = lowPass(accel_input, accel_output);
		else
			accel_output = accel_input;

		accel_max_values[0] = accel_max_values[0] / MAGNIFICATION_FACTOR;
		accel_max_values[1] = accel_max_values[1] / MAGNIFICATION_FACTOR;
		accel_max_values[2] = accel_max_values[2] / MAGNIFICATION_FACTOR;

		accel_max_values[0] = (accel_max_values[0] < Math.abs(accel_output[0])) ? Math
				.abs(accel_output[0]) : accel_max_values[0];
		accel_max_values[1] = (accel_max_values[1] < Math.abs(accel_output[1])) ? Math
				.abs(accel_output[1]) : accel_max_values[1];
		accel_max_values[2] = (accel_max_values[2] < Math.abs(accel_output[2])) ? Math
				.abs(accel_output[2]) : accel_max_values[2];

		// The values obtained are very small so let us magnify them by a factor
		// of 10000
		accel_max_values[0] = accel_max_values[0] * MAGNIFICATION_FACTOR;
		accel_max_values[1] = accel_max_values[1] * MAGNIFICATION_FACTOR;
		accel_max_values[2] = accel_max_values[2] * MAGNIFICATION_FACTOR;

		// Using s = (1/2)*a*t^2
		double time_period = accelerometer_period / 1000f;
		x_dist = 0.5 * accel_max_values[0] * Math.pow(time_period, 2);
		y_dist = 0.5 * accel_max_values[1] * Math.pow(time_period, 2);
		z_dist = 0.5 * accel_max_values[2] * Math.pow(time_period, 2);

		// The values obtained are very small so let us magnify them by a factor
		// of 10000
		x_dist = x_dist * MAGNIFICATION_FACTOR;
		y_dist = y_dist * MAGNIFICATION_FACTOR;
		z_dist = z_dist * MAGNIFICATION_FACTOR;

		// Using pythagoras to calculate abs distance from origin. The origin
		// will have to be changed to calibrated data.
		distance_vector = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2)
				+ Math.pow(z_dist, 2));
		// Jagruth Addition end

		double pitch = Math.atan(accel_output[0]
				/ Math.sqrt(Math.pow(accel_output[1], 2)
						+ Math.pow(accel_output[2], 2)));
		double roll = Math.atan(accel_output[1]
				/ Math.sqrt(Math.pow(accel_output[0], 2)
						+ Math.pow(accel_output[2], 2)));
		// convert radians into degrees
		pitch = pitch * (180.0 / Math.PI);
		roll = -1 * roll * (180.0 / Math.PI);

		if (Sensai.DEBUG)
			showAccelerometerData(accel_output, accel_max_values, x_dist,
					y_dist, z_dist, distance_vector);

		return distance_vector;
	}

	private static void showAccelerometerData(float[] accel_data,
			float[] accel_max_values, double x_dist, double y_dist,
			double z_dist, double distance_vector) {
		// System.out.println("n\nX: " + String.format("%.3f", accel_data[0])
		// + "  max_x: " + String.format("%.3f", accel_max_values[0]));
		// System.out.println("Y: " + String.format("%.3f", accel_data[1])
		// + "  max_y: " + String.format("%.3f", accel_max_values[1]));
		// System.out.println("Z: " + String.format("%.3f", accel_data[2])
		// + "  max_z: " + String.format("%.3f", accel_max_values[2]));
		// System.out.println("x_dist: " + String.format("%.2f", x_dist)
		// + " y_dist: " + String.format("%.2f", y_dist) + " z_dist: "
		// + String.format("%.2f", z_dist));
		System.out.println("ABS_DIST: "
				+ String.format("%.3f", distance_vector));
	}

}
