package com.ausxin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.util.ExcelUtils;
import com.util.FileUtils;
import com.util.LocaleUtils;

public class Reporter {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Reporter window = new Reporter();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		displayCenter(shell);
		
		// 设置程序的logo
		Image image = new Image(null,"rs/logo.png");
		shell.setImage(image);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mFile = new MenuItem(menu, SWT.NONE);
		mFile.setText(LocaleUtils.getLocal("file"));
		
		
		MenuItem mHelp = new MenuItem(menu, SWT.CASCADE);
		mHelp.setText(LocaleUtils.getLocal("help"));
		
		Menu menu_1 = new Menu(mHelp);
		mHelp.setMenu(menu_1);
		
		MenuItem mAbout = new MenuItem(menu_1, SWT.NONE);

		mAbout.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				MessageBox msg = new MessageBox(shell,SWT.APPLICATION_MODAL);
				msg.setMessage(LocaleUtils.getLocal("aboutMsg") +"\n" + 
						LocaleUtils.getLocal("author"));
				 
				msg.open();
			}
		});
		mAbout.setText(LocaleUtils.getLocal("about"));
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mClose = new MenuItem(menu_1, SWT.NONE);
		mClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		mClose.setText(LocaleUtils.getLocal("close"));
		//mClose.setImage(new Image(null,"rs/close.png"));
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.MIN|SWT.CLOSE);
		shell.setSize(450, 300);
		shell.setText(LocaleUtils.getLocal("aboutMsg"));

		Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 10, 414, 119);

		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(10, 20, 83, 15);
		lblNewLabel.setText(LocaleUtils.getLocal("lifeLabel"));

		text = new Text(group, SWT.BORDER);
		text.setBounds(99, 17, 223, 21);

		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileDig(shell, text);
			}
		});
		btnNewButton.setBounds(329, 15, 75, 25);
		btnNewButton.setText(LocaleUtils.getLocal("scanButton"));

		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setBounds(10, 55, 83, 15);
		lblNewLabel_1.setText(LocaleUtils.getLocal("mulLabel"));

		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setBounds(10, 89, 83, 15);
		lblNewLabel_2.setText(LocaleUtils.getLocal("benzLabel"));

		text_1 = new Text(group, SWT.BORDER);
		text_1.setBounds(99, 52, 223, 21);

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBounds(99, 86, 223, 21);

		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileDig(shell, text_1);
			}
		});
		button.setText(LocaleUtils.getLocal("scanButton"));
		button.setBounds(329, 50, 75, 25);

		Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileDig(shell, text_2);
			}
		});
		button_1.setText(LocaleUtils.getLocal("scanButton"));
		button_1.setBounds(329, 84, 75, 25);

		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(10, 135, 414, 105);

		Label label = new Label(group_1, SWT.NONE);
		label.setText(LocaleUtils.getLocal("reportLabel"));
		label.setBounds(10, 30, 83, 15);

		text_3 = new Text(group_1, SWT.BORDER);
		text_3.setBounds(99, 27, 223, 21);

		Button button_2 = new Button(group_1, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				folderDig(shell, text_3);
			}
		});
		button_2.setText(LocaleUtils.getLocal("scanButton"));
		button_2.setBounds(329, 25, 75, 25);

		Button button_3 = new Button(group_1, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String outDir = text_3.getText().trim();
				
				// 在用户零时文件目录下面创建一个零时文件夹
				String tmp = FileUtils.getTmpRandom();

				// standard the csv file
				ExcelUtils.lifeStandard(text.getText().trim(), tmp);
				ExcelUtils.standardMul(text_1.getText().trim(), tmp, "多元行销");
				ExcelUtils.standardBenz(text_2.getText().trim(), tmp, "奔驰行销");

				MessageBox msg = null;
				// create the report file
				try {
					ExcelUtils.exportExcel(tmp,outDir);
					msg = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.ICON_WORKING);
					msg.setMessage(LocaleUtils.getLocal("success"));
					msg.setText(LocaleUtils.getLocal("titleSuccess"));
					msg.open();
					
					// 删除零时文件夹及其路径
					FileUtils.deleteFolder(tmp);

				} catch (Exception e1) {
					msg = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.ICON_ERROR);
					msg.setMessage(LocaleUtils.getLocal("fail"));
					msg.setText(LocaleUtils.getLocal("titleFail"));
					msg.open();

				}
			}
		});

		button_3.setText(LocaleUtils.getLocal("createReportbtn"));
		button_3.setBounds(311, 64, 93, 25);

	}

	/**
	 * file selection dialog
	 * 
	 * @param parent
	 * @param text
	 */
	protected void fileDig(Shell parent, Text text) {
		FileDialog fileDlg = new FileDialog(parent, SWT.OPEN);
		fileDlg.setText(LocaleUtils.getLocal("fileSelector"));
		fileDlg.setFilterExtensions(new String[] { "*.csv" });
		fileDlg.setFilterPath(".");
		String selected = fileDlg.open();
		if(selected == null){
			return;
		} else {
			text.setText(selected);
		}
	}

	/**
	 * folder selection dialog
	 * 
	 * @param parent
	 * @param text
	 */
	protected void folderDig(Shell parent, Text text) {
		DirectoryDialog folderDlg = new DirectoryDialog(parent, SWT.OPEN);
		folderDlg.setText(LocaleUtils.getLocal("folderSelector"));
		folderDlg.setFilterPath(".");
		//folderDlg.setMessage("Select the folder");

		String folderDir = folderDlg.open();
		if (folderDir == null) {
			return;
		} else {
			text.setText(folderDir);
		}
	}
	
	/**
	 * 设置shell的显示位置为屏幕中央
	 * 
	 * @param shell
	 */
	private void displayCenter(Shell shell){
		int width = shell.getMonitor().getClientArea().width;
		int heigth = shell.getMonitor().getClientArea().height;
		int x = shell.getSize().x;
		int y = shell.getSize().y;
		
		// display the shell in the center of monitor
		shell.setLocation((width - x)/2, (heigth - y)/2);
	}
}
