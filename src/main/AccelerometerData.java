package main;

public class AccelerometerData {
	private long timestamp;
	private String sensorsVal;

	protected String getSensorsVal() {
		return sensorsVal;
	}

	protected void setSensorsVal(String sensorsVal) {
		this.sensorsVal = sensorsVal;
	}

	protected long getTimestamp() {
		return timestamp;
	}

	protected void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	private static final String START_SYMBOL = "[";
	private static final String END_SYMBOL = "]";
	private static final String X_indicator = "X=";
	private static final String Y_indicator = "Y=";
	private static final String Z_indicator = "Z=";
	private static final String X_separator = ",";
	private static final String Y_separator = ":";

	protected static int[] parseInput(String acclString) throws Exception {
		// eg input string [X=960,Y=144-Z=368]
		if (acclString == null || acclString.isEmpty())
			return null;
		else
			acclString = acclString.trim();
		int[] senvals = new int[3];
		if (acclString.startsWith(START_SYMBOL)
				&& acclString.endsWith(END_SYMBOL)) {
			String x_int_substr = acclString.substring(
					acclString.indexOf(X_indicator) + 2,
					acclString.indexOf(X_separator));
			String y_int_substr = acclString.substring(
					acclString.indexOf(Y_indicator) + 2,
					acclString.indexOf(Y_separator));
			String z_int_substr = acclString.substring(
					acclString.indexOf(Z_indicator) + 2,
					acclString.indexOf(END_SYMBOL));
//			System.out.println(x_int_substr + ":" + y_int_substr + ":"
//					+ z_int_substr);
			senvals[0] = Integer.parseInt(x_int_substr);
			senvals[1] = Integer.parseInt(y_int_substr);
			senvals[2] = Integer.parseInt(z_int_substr);
		} else
			return null;
		return senvals;
	}
}
