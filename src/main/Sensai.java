package main;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;

public class Sensai extends ApplicationWindow {

	protected static final boolean DEBUG = false;
	private static Label label_1;
	private static Label label_2;
	private static Label label_3;
	private static Label label_4;
	private static Label label_5;
	private static Label label_6;
	private static Label label_7;
	private static Label label_8;
	private static Label label_9;
	private static Label label_10;
	private static Label label_11;
	private static Label label_12;
	private static Button btnConnect;
	private static Button btnDisconnect;
	protected static int baud_rate = 9600;
	protected static int data_bits = 8;
	protected static int stop_bits = 1;
	protected static String parity = "None";
	protected static String comPort = null;
	private static SerialComm serialcomport = null;
	private Group grpInfo;
	private static Text info_txtbar;
	private Label lblconnStatus;
	private static boolean START_MEASUREMENTS = false;
	private static boolean connectStatus;
	private static boolean final_force_ready = false;

	protected static boolean is_Final_force_ready() {
		return final_force_ready;
	}

	protected static void set_Final_force_ready(boolean final_force_ready) {
		Sensai.final_force_ready = final_force_ready;
	}

	protected static boolean get_start_measurements() {
		return Sensai.START_MEASUREMENTS;
	}

	private static double calibrated_distance_force = 0;

	protected static double getCalibrated_distance_force() {
		return Sensai.calibrated_distance_force;
	}

	protected static void setCalibrated_distance_force(
			double calibrated_distance_force) {
		Sensai.calibrated_distance_force = calibrated_distance_force;
	}

	private static int final_force = 0;

	protected static int getFinal_force() {
		return final_force;
	}

	protected static void setFinal_force(int final_force) {
		Sensai.final_force = final_force;
	}

	/**
	 * Create the application window.
	 */
	public Sensai() {
		super(null);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.APPLICATION_MODAL);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	protected static void set_force_meter_value(int meter_val) {
		try {
			if (meter_val > 0) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
				playSound(SOUNDS[randomNum]);
			}
			switch (meter_val) {
			case 1:
				label_1.setVisible(true);
				label_2.setVisible(false);
				label_3.setVisible(false);
				label_4.setVisible(false);
				label_5.setVisible(false);
				label_6.setVisible(false);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 2:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(false);
				label_4.setVisible(false);
				label_5.setVisible(false);
				label_6.setVisible(false);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 3:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(false);
				label_5.setVisible(false);
				label_6.setVisible(false);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 4:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(false);
				label_6.setVisible(false);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 5:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(false);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 6:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 7:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 8:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(true);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 9:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(true);
				label_9.setVisible(true);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 10:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(true);
				label_9.setVisible(true);
				label_10.setVisible(true);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			case 11:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(true);
				label_9.setVisible(true);
				label_10.setVisible(true);
				label_11.setVisible(true);
				label_12.setVisible(false);
				break;
			case 12:
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(true);
				label_9.setVisible(true);
				label_10.setVisible(true);
				label_11.setVisible(true);
				label_12.setVisible(true);
				break;
			default:
				label_1.setVisible(false);
				label_2.setVisible(false);
				label_3.setVisible(false);
				label_4.setVisible(false);
				label_5.setVisible(false);
				label_6.setVisible(false);
				label_7.setVisible(false);
				label_8.setVisible(false);
				label_9.setVisible(false);
				label_10.setVisible(false);
				label_11.setVisible(false);
				label_12.setVisible(false);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void display_text(String info) {
		if (info_txtbar != null) {
			info_txtbar.setText(info);
		}
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		Composite graph_composite = new Composite(container, SWT.BORDER);
		graph_composite.setLayout(new GridLayout(1, false));
		GridData gd_graph_composite = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd_graph_composite.widthHint = 207;
		gd_graph_composite.heightHint = 435;
		graph_composite.setLayoutData(gd_graph_composite);

		Label lblForceMeter = new Label(graph_composite, SWT.NONE);
		lblForceMeter.setFont(SWTResourceManager.getFont("Segoe UI", 16,
				SWT.BOLD));
		lblForceMeter.setAlignment(SWT.CENTER);
		GridData gd_lblForceMeter = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_lblForceMeter.widthHint = 201;
		lblForceMeter.setLayoutData(gd_lblForceMeter);
		lblForceMeter.setText("Stoot Kracht");

		label_12 = new Label(graph_composite, SWT.NONE);
		label_12.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		GridData gd_label_11 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_11.heightHint = 43;
		label_12.setLayoutData(gd_label_11);
		label_12.setText("12");
		label_12.setBackground(SWTResourceManager.getColor(204, 0, 0));
		label_12.setAlignment(SWT.CENTER);

		label_11 = new Label(graph_composite, SWT.NONE);
		label_11.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		GridData gd_label_10 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_10.heightHint = 37;
		label_11.setLayoutData(gd_label_10);
		label_11.setText("11");
		label_11.setBackground(SWTResourceManager.getColor(255, 0, 0));
		label_11.setAlignment(SWT.CENTER);

		label_10 = new Label(graph_composite, SWT.NONE);
		label_10.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		GridData gd_label_9 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_9.heightHint = 40;
		label_10.setLayoutData(gd_label_9);
		label_10.setText("10");
		label_10.setBackground(SWTResourceManager.getColor(255, 102, 0));
		label_10.setAlignment(SWT.CENTER);

		label_9 = new Label(graph_composite, SWT.NONE);
		label_9.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		GridData gd_label_8 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_8.heightHint = 34;
		label_9.setLayoutData(gd_label_8);
		label_9.setText("9");
		label_9.setBackground(SWTResourceManager.getColor(255, 153, 0));
		label_9.setAlignment(SWT.CENTER);

		label_8 = new Label(graph_composite, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		GridData gd_label_7 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_7.heightHint = 33;
		label_8.setLayoutData(gd_label_7);
		label_8.setText("8");
		label_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		label_8.setAlignment(SWT.CENTER);

		label_7 = new Label(graph_composite, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		GridData gd_label_6 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_6.heightHint = 30;
		label_7.setLayoutData(gd_label_6);
		label_7.setText("7");
		label_7.setBackground(SWTResourceManager.getColor(0, 204, 51));
		label_7.setAlignment(SWT.CENTER);

		label_6 = new Label(graph_composite, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		GridData gd_label_5 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_5.heightHint = 26;
		label_6.setLayoutData(gd_label_5);
		label_6.setText("6");
		label_6.setBackground(SWTResourceManager.getColor(102, 204, 51));
		label_6.setAlignment(SWT.CENTER);

		label_5 = new Label(graph_composite, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		GridData gd_label_4 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_4.heightHint = 23;
		label_5.setLayoutData(gd_label_4);
		label_5.setText("5");
		label_5.setBackground(SWTResourceManager.getColor(255, 204, 51));
		label_5.setAlignment(SWT.CENTER);

		label_4 = new Label(graph_composite, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		GridData gd_label_3 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_3.heightHint = 21;
		label_4.setLayoutData(gd_label_3);
		label_4.setText("4");
		label_4.setBackground(SWTResourceManager.getColor(204, 255, 51));
		label_4.setAlignment(SWT.CENTER);

		label_3 = new Label(graph_composite, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		GridData gd_label_2 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_2.heightHint = 18;
		label_3.setLayoutData(gd_label_2);
		label_3.setText("3");
		label_3.setBackground(SWTResourceManager.getColor(153, 255, 102));
		label_3.setAlignment(SWT.CENTER);

		label_2 = new Label(graph_composite, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		GridData gd_label_1 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_label_1.heightHint = 16;
		label_2.setLayoutData(gd_label_1);
		label_2.setText("2");
		label_2.setBackground(SWTResourceManager.getColor(204, 255, 102));
		label_2.setAlignment(SWT.CENTER);

		label_1 = new Label(graph_composite, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		GridData gd_label = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_label.heightHint = 13;
		label_1.setLayoutData(gd_label);
		label_1.setBackground(SWTResourceManager.getColor(255, 255, 102));
		label_1.setAlignment(SWT.CENTER);
		label_1.setText("1");

		Composite main_composite = new Composite(container, SWT.BORDER);
		main_composite.setLayout(new GridLayout(5, false));
		GridData gd_main_composite = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd_main_composite.heightHint = 397;
		gd_main_composite.widthHint = 314;
		main_composite.setLayoutData(gd_main_composite);

		Group grpSensaiConnectionSettings = new Group(main_composite, SWT.NONE);
		grpSensaiConnectionSettings.setFont(SWTResourceManager.getFont(
				"Segoe UI", 9, SWT.BOLD));
		grpSensaiConnectionSettings.setLayout(new GridLayout(3, false));
		GridData gd_grpSensaiConnectionSettings = new GridData(SWT.FILL,
				SWT.TOP, false, false, 5, 1);
		gd_grpSensaiConnectionSettings.widthHint = 299;
		gd_grpSensaiConnectionSettings.heightHint = 61;
		grpSensaiConnectionSettings
				.setLayoutData(gd_grpSensaiConnectionSettings);
		grpSensaiConnectionSettings.setText("Connect to Sensai");

		Label lblConnection = new Label(grpSensaiConnectionSettings, SWT.NONE);
		lblConnection.setText("Status:");

		lblconnStatus = new Label(grpSensaiConnectionSettings, SWT.NONE);
		lblconnStatus.setText("Disconnected");
		new Label(grpSensaiConnectionSettings, SWT.NONE);

		btnConnect = new Button(grpSensaiConnectionSettings, SWT.NONE);
		GridData gd_btnConnect = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_btnConnect.widthHint = 88;
		btnConnect.setLayoutData(gd_btnConnect);
		btnConnect.setText("Connect");
		btnConnect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String commport = null;
					if (Sensai.comPort == null)
						commport = SerialComm.queryCommPorts();
					else
						commport = Sensai.comPort;
					if (commport != null) {
						if (serialcomport == null) {
							serialcomport = new SerialComm();
						}
						connectStatus = serialcomport.connect(commport);
						if (connectStatus) {
							btnDisconnect.setEnabled(true);
							btnConnect.setEnabled(false);
							set_Final_force_ready(false);
							lblconnStatus.setText("Connected");

							Sensai.START_MEASUREMENTS = true;
							// new Thread(new ForceMeasure()).start();
							Display.getDefault().syncExec(new Runnable() {
								public void run() {

									while (Sensai.get_start_measurements()) {
										System.out.println("Calibrated: "
												+ Sensai.getCalibrated_distance_force()
												+ "  Force: "
												+ Sensai.getFinal_force());
										if (Sensai
												.getCalibrated_distance_force() > 0
												&& is_Final_force_ready()) {

											Sensai.START_MEASUREMENTS = false;
											System.out.println("Final Force: "
													+ (int) Sensai
															.getFinal_force());
											Sensai.set_force_meter_value((int) Sensai
													.getFinal_force());
											Sensai.setCalibrated_distance_force(0);
											Sensai.setFinal_force(0);
										}
									}

								}
							});
						}
					} else
						display_text("No COM ports found. Check if sensor is connected properly!");
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

			}
		});

		btnDisconnect = new Button(grpSensaiConnectionSettings, SWT.NONE);
		btnDisconnect.setEnabled(false);
		GridData gd_btnDisconnect = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnDisconnect.widthHint = 92;
		btnDisconnect.setLayoutData(gd_btnDisconnect);
		btnDisconnect.setText("Disconnect");
		btnDisconnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (serialcomport.disconnect()) {
						btnDisconnect.setEnabled(false);
						btnConnect.setEnabled(true);
						lblconnStatus.setText("Disconnected");
						START_MEASUREMENTS = false;
						connectStatus = false;
						set_force_meter_value(0);
						Sensai.setCalibrated_distance_force(0);
						Sensai.setFinal_force(0);
						set_Final_force_ready(false);
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

			}
		});

		Button btnSettings = new Button(grpSensaiConnectionSettings, SWT.NONE);
		GridData gd_btnSettings = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnSettings.widthHint = 90;
		btnSettings.setLayoutData(gd_btnSettings);
		btnSettings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSettings.setText("Settings");

		Menu menu_1 = new Menu(grpSensaiConnectionSettings);
		grpSensaiConnectionSettings.setMenu(menu_1);

		MenuItem settings_menu = new MenuItem(menu_1, SWT.NONE);
		settings_menu.setText("Help");
		settings_menu.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				MenuDialog dialog = new MenuDialog(getParentShell());
				dialog.setHelpContent("Settings Info");
				dialog.open();
			}
		});
		btnSettings.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				Settings settings = new Settings(getShell());
				String commport = SerialComm.queryCommPorts();
				if (commport != null) {
					settings.setTxt_comport(commport);
				}
				settings.open();
				baud_rate = Integer.parseInt(settings.getTxt_baudrate());
				data_bits = Integer.parseInt(settings.getTxt_bits());
				parity = settings.getTxt_parity();
				comPort = settings.getTxt_comport();
			}
		});

		if (DEBUG) {
			final Text text = new Text(main_composite, SWT.BORDER);
			GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, true, false,
					1, 1);
			gd_text.widthHint = 172;
			text.setLayoutData(gd_text);
			text.addKeyListener(new KeyListener() {

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

					String text2 = text.getText();
					if (text2 != null && !text2.isEmpty()) {
						set_force_meter_value(Integer.parseInt(text2));
					} else {
						set_force_meter_value(0);
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		grpInfo = new Group(main_composite, SWT.NONE);
		grpInfo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		grpInfo.setText("Info");
		grpInfo.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_grpInfo = new GridData(SWT.FILL, SWT.FILL, false, false, 5,
				1);
		gd_grpInfo.heightHint = 324;
		grpInfo.setLayoutData(gd_grpInfo);

		lbl_logo = new Label(grpInfo, SWT.CENTER);
		lbl_logo.setImage(SWTResourceManager.getImage(Sensai.class,
				"/main/logo.jpeg"));

		info_txtbar = new Text(grpInfo, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		info_txtbar.setEditable(false);

		set_force_meter_value(0);
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Sensai window = new Sensai();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Sensai");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(559, 511);
	}

	private static final String[] SOUNDS = { "p0.wav", "p1.wav", "p2.wav",
			"p3.wav" };
	private Label lbl_logo;

	private static synchronized void playSound(final String url) {
		if (url == null | url.isEmpty())
			return;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {

					String jarpath = getClass().getProtectionDomain()
							.getCodeSource().getLocation().toURI().getPath();
					jarpath = jarpath.substring(1, jarpath.lastIndexOf("/") + 1);
					Clip clip = AudioSystem.getClip();
					String audiofilepath = url;
					InputStream audioSrc = getClass().getResourceAsStream(
							audiofilepath);
					InputStream bufferedIn = new BufferedInputStream(audioSrc);
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(bufferedIn);
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
					// display_text(e.getMessage());
				}
			}
		});
	}
}
