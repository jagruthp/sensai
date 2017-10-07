package main;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;

public class Settings extends Dialog {

	private static String txt_baudrate;
	private static String txt_bits;
	private static String txt_parity;
	private static String txt_comport;
	private Combo combotxt_comport;
	private Combo combotxt_baudrate;
	private Combo combotxt_bits;
	private Combo combotxt_parity;

	protected String getTxt_comport() {
		return Settings.txt_comport;
	}

	protected void setTxt_comport(String txt_comport) {
		Settings.txt_comport = txt_comport;
	}

	protected String getTxt_baudrate() {
		return txt_baudrate;
	}

	protected void setTxt_baudrate(String txt_baudrate) {
		Settings.txt_baudrate = txt_baudrate;
	}

	protected String getTxt_bits() {
		return txt_bits;
	}

	protected void setTxt_bits(String txt_bits) {
		Settings.txt_bits = txt_bits;
	}

	protected String getTxt_parity() {
		return txt_parity;
	}

	protected void setTxt_parity(String txt_parity) {
		Settings.txt_parity = txt_parity;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public Settings(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.TITLE);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		Label lblComPort = new Label(container, SWT.NONE);
		lblComPort.setText("COM Port:");

		combotxt_comport = new Combo(container, SWT.NONE);
		combotxt_comport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		if (Settings.txt_comport != null) {
			combotxt_comport.setText(Settings.txt_comport);
		}

		Label lblBaudrate = new Label(container, SWT.NONE);
		GridData gd_lblBaudrate = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblBaudrate.widthHint = 62;
		lblBaudrate.setLayoutData(gd_lblBaudrate);
		lblBaudrate.setText("Baudrate:");

		combotxt_baudrate = new Combo(container, SWT.READ_ONLY);
		combotxt_baudrate.setItems(new String[] { "9600", "19200", "57600",
				"115200" });
		combotxt_baudrate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		combotxt_baudrate.select(3);
		if (Settings.txt_baudrate != null)
			combotxt_baudrate.setText(Settings.txt_baudrate);

		Label lblBits = new Label(container, SWT.NONE);
		lblBits.setText("Bits:");

		combotxt_bits = new Combo(container, SWT.READ_ONLY);
		combotxt_bits.setItems(new String[] { "7", "8" });
		combotxt_bits.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		combotxt_bits.select(1);
		if (Settings.txt_bits != null)
			combotxt_bits.setText(Settings.txt_bits);

		Label lblParity = new Label(container, SWT.NONE);
		lblParity.setText("Parity:");

		combotxt_parity = new Combo(container, SWT.READ_ONLY);
		combotxt_parity.setItems(new String[] { "none", "odd", "even", "mark",
				"space" });
		combotxt_parity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		combotxt_parity.select(0);
		if (Settings.txt_parity != null)
			combotxt_parity.setText(Settings.txt_parity);

		parent.getShell().setText("Connection Settings");
		parent.getShell().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				txt_baudrate = combotxt_baudrate.getText();
				txt_bits = combotxt_bits.getText();
				txt_parity = combotxt_parity.getText();
				txt_comport = combotxt_comport.getText();

			}
		});
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@SuppressWarnings("unused")
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);

		Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
