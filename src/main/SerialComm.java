package main;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialComm {
	protected static int baud_rate = 9600;
	protected static int data_bits = 8;
	protected static int stop_bits = 1;
	protected static String parity = "None";
	private CommPort commPort;
	private Thread inputStreamThread;
	private Thread outputStreamThread;

	protected static int getBaud_rate() {
		return baud_rate;
	}

	protected static void setBaud_rate(int baud_rate) {
		SerialComm.baud_rate = baud_rate;
	}

	protected static int getData_bits() {
		return data_bits;
	}

	protected static void setData_bits(int data_bits) {
		SerialComm.data_bits = data_bits;
	}

	protected static int getStop_bits() {
		return stop_bits;
	}

	protected static void setStop_bits(int stop_bits) {
		SerialComm.stop_bits = stop_bits;
	}

	protected static String getParity() {
		return parity;
	}

	protected static void setParity(String parity) {
		SerialComm.parity = parity;
	}

	public SerialComm() {
		super();
	}

	protected boolean connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			Sensai.display_text("Error: Port is currently in use");
			return false;
		} else {
			commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				inputStreamThread = new Thread(new SerialReader(in));
				inputStreamThread.start();
				outputStreamThread = new Thread(new SerialWriter(out));
				outputStreamThread.start();
				return true;
			} else {
				Sensai.display_text("Error: Only serial ports are handled by this example.");
				return false;
			}
		}
	}

	/*
	 * protected void start_rxtx() throws Exception { if (commPort != null &&
	 * commPort instanceof SerialPort) { SerialPort serialPort = (SerialPort)
	 * commPort; if (inputStreamThread != null) {
	 * serialPort.getInputStream().close(); inputStreamThread.stop(); } if
	 * (outputStreamThread != null) { serialPort.getOutputStream().close();
	 * outputStreamThread.stop(); } } }
	 */

	@SuppressWarnings("deprecation")
	protected boolean disconnect() throws Exception {
		if (commPort != null && commPort instanceof SerialPort) {
			SerialPort serialPort = (SerialPort) commPort;
			if (inputStreamThread != null) {
				serialPort.getInputStream().close();
				inputStreamThread.stop();
				// inputStreamThread.destroy();
			}
			if (outputStreamThread != null) {
				serialPort.getOutputStream().close();
				outputStreamThread.stop();
				// outputStreamThread.destroy();
			}

			serialPort.close();
			return true;
		}
		return false;
	}

	/** */
	public static class SerialReader implements Runnable {
		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1];
			StringBuilder builder = new StringBuilder();
			long timeStamp = -1;
			int len = -1;
			long sensorcount = 0;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					if (Sensai.get_start_measurements()) {
						String readString = new String(buffer, 0, len);
						builder.append(readString);
						if (readString.equalsIgnoreCase("[")) {
							timeStamp = System.currentTimeMillis();
						} else if (readString.equalsIgnoreCase("]")) {
							try {
								int[] senvals = AccelerometerData
										.parseInput(builder.toString());
								double force_as_distance = Accelerometer
										.parse_acclr_data(senvals);
								if (Sensai.getCalibrated_distance_force() == 0
										&& force_as_distance != 0) {
									Sensai.set_Final_force_ready(false);
									System.out.println("Calibrated Data: "
											+ force_as_distance);
									Sensai.setCalibrated_distance_force(force_as_distance);
								} else {
									double force = (force_as_distance - Sensai
											.getCalibrated_distance_force())
											/ Accelerometer.FORCE_CALC_DIVIDE_FACTOR;
									if (Sensai.getFinal_force() < force) {
										Sensai.setFinal_force((int) force);
									} else if (Sensai.getFinal_force() == 0) {
										Sensai.setFinal_force((int) force);
									} else {
										Sensai.set_Final_force_ready(true);
									}

									// System.out.println("Force set: "
									// + (int) force + "  Force get: "
									// + Sensai.getFinal_force());
								}

							} catch (Exception e) {
								System.out.println(builder.toString());
								e.printStackTrace();
							}
							// For debugging
							// System.out.println(builder.toString());

							// Clear out data
							sensorcount++;
							timeStamp = -1;
							builder.setLength(0); // clear buffer
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** */
	public static class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {
				int c = 0;
				while ((c = System.in.read()) > -1) {
					this.out.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	static String queryCommPorts() {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		String commport = null;
		while (ports.hasMoreElements() && commport == null) {
			CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
			switch (port.getPortType()) {
			case CommPortIdentifier.PORT_PARALLEL:
				return null;
			case CommPortIdentifier.PORT_SERIAL:
				break;
			default: // / Shouldn't happen
				return null;
			}
			commport = port.getName();
			// System.out.println(commport);
		}
		return commport;
	}

	public static void main(String[] args) {
		try {
			String commport = SerialComm.queryCommPorts();
			if (commport != null)
				(new SerialComm()).connect(commport);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}