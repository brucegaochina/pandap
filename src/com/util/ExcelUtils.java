package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {

	/**
	 * ��Ŀ¼���������csv�ļ�������һ��excel��,ÿ��csv�ļ�Ϊһ��sheet,sheet������Ϊÿ��csv���ļ���
	 * 
	 * @param dir
	 *            csv�ļ�Ŀ¼
	 * @throws Exception
	 */
	public static void exportExcel(String dir, String destiDir) throws Exception {

		if (dir.isEmpty())
			throw new Exception("�ļ��в�����!");
		
		File[] filess = new File(dir).listFiles();
		if(filess.length != 6)
			throw new Exception("tmp file is not completed");

		File fileDir = new File(dir);
		
		if (!fileDir.isDirectory())
			throw new Exception("������Ч���ļ���!");

		File outFile = new File(destiDir + "\\result.xls");

		if (outFile.exists()) {
			outFile.delete();
		}

		File[] files = fileDir.listFiles();

		HSSFWorkbook wr = new HSSFWorkbook();
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;

		BufferedReader br = null;
		int position;

		for (File file : files) {
			System.out.println(file.getName());
			position = file.getName().lastIndexOf(".");
			System.out.println(position);
			sheet = wr.createSheet(file.getName().substring(0, position));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "gb2312"));
			String line = null;
			int num = 0;

			while ((line = br.readLine()) != null) {
				String[] str = line.split(",");
				row = sheet.createRow(num++);
				
				for (int i = 0; i < str.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(CommonUtils.formatDate(str[i]));
				}
			}

			wr.write(new FileOutputStream(outFile));
			br.close();
		}
		
	}

	/**
	 * �ļ�ѡ����
	 * 
	 * @param chooser
	 * @param text
	 */
	public static void chooserFile(JFileChooser chooser, JTextField text,
			String title) {
		chooser.setDialogTitle(title);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			String filenae = chooser.getSelectedFile().getPath();
			text.setText(filenae);
		}
	}

	/**
	 * �ļ���ѡ����
	 * 
	 * @param chooser
	 * @param text
	 */
	public static void chooserDir(JFileChooser chooser, JTextField text) {
		chooser.setDialogTitle("�������Ŀ¼");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			String filenae = chooser.getSelectedFile().getPath();
			text.setText(filenae);
		}
	}

	/**
	 * ��Ԫ����
	 * 
	 * @param file
	 * @param dir
	 * @param outFile
	 */
	public static void standardMul(String file, String dir, String outFile) {

		File csvFile = new File(file);

		File out = new File(dir + "\\" + outFile + ".csv");

		int row = 1;
		StringBuffer buffer = new StringBuffer();

		BufferedReader br = null;

		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					csvFile), "gb2312"));

			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out), "gb2312"));
			String value = null;
			while ((value = br.readLine()) != null) {
				if (row == 1) {
					StringBuffer bf = new StringBuffer(value);
					bw.write(filterMul(bf) + "\n");
				}
				if (row > 1) {
					String str[] = value.split(",");
					System.out.println(str.length);

					if (CommonUtils.isNum(str[0])) {
						if (buffer.length() > 1) {

							bw.write(filterMul(buffer) + "\n");

							buffer.setLength(0);
						}
						if (value != "\r\n") {
							buffer.append(value);
						}

					} else {
						if (value != "\r\n") {
							buffer.append(value);
						}
					}
				}
				row++;
			}
			// д�����һ��
			if (buffer.length() != 0) {
				bw.write(filterMul(buffer) + "\n");
			}

			br.close();

			bw.flush();
			bw.close();
			System.out.println("���ɳɹ�!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @param file
	 * @param dir
	 * @param outFile
	 */
	public static void standardBenz(String file, String dir, String outFile) {

		File csvFile = new File(file);

		File out = new File(dir + "\\" + outFile + ".csv");

		int row = 1;
		StringBuffer buffer = new StringBuffer();

		BufferedReader br = null;

		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					csvFile), "gb2312"));

			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out), "gb2312"));
			String value = null;
			while ((value = br.readLine()) != null) {
				if (row == 1) {
					StringBuffer bf = new StringBuffer(value);
					bw.write(filterBenz(bf) + "\n");
				}
				if (row > 1) {
					String str[] = value.split(",");
					System.out.println(str.length);

					if (CommonUtils.isNum(str[0])) {
						if (buffer.length() > 1) {

							bw.write(filterBenz(buffer) + "\n");

							buffer.setLength(0);
						}
						if (value != "\r\n") {
							buffer.append(value);
						}

					} else {
						if (value != "\r\n") {
							buffer.append(value);
						}
					}
				}
				row++;
			}
			// д�����һ��
			if (buffer.length() != 0) {
				bw.write(filterBenz(buffer) + "\n");
			}

			br.close();

			bw.flush();
			bw.close();
			System.out.println("���ɳɹ�!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ��׼������ҵ������
	 * 
	 * @param file
	 * @param dir
	 */
	public static void lifeStandard(String file, String dir) {
		if ("".equals(file.trim()) || "".equals(dir.trim())) {
			return;
		}

		File inputCsv = new File(file);

		File out1 = new File(dir + "\\2010.csv");
		File out2 = new File(dir + "\\2011.csv");
		File out3 = new File(dir + "\\2012.csv");
		File out4 = new File(dir + "\\2013.csv");
		int row = 1;
		StringBuffer buffer = new StringBuffer();

		BufferedReader br = null;
		BufferedWriter bw1 = null;
		BufferedWriter bw2 = null;
		BufferedWriter bw3 = null;
		BufferedWriter bw4 = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					inputCsv), "gb2312"));
			bw1 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out1), "gb2312"));
			bw2 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out2), "gb2312"));
			bw3 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out3), "gb2312"));
			bw4 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out4), "gb2312"));

			String value = null;
			while ((value = br.readLine()) != null) {
				// write the title
				if (row == 1) {
					StringBuffer bf = new StringBuffer(value);
					bw1.write(filterLife(bf) + "\n");
					bw2.write(filterLife(bf) + "\n");
					bw3.write(filterLife(bf) + "\n");
					bw4.write(filterLife(bf) + "\n");
				}

				if (row > 1) {
					String str[] = value.split(",");

					if (CommonUtils.isNum(str[0])) {
						if (buffer.length() > 1) {
							String[] val = buffer.toString().split(",");
							val = CommonUtils.formatCsvRow(val);
							// System.out.println(val.length);

							if ("2010".equals(CommonUtils.getYear(val[17])))
								bw1.write(filterLife(buffer) + "\n");
							if ("2011".equals(CommonUtils.getYear(val[17])))
								bw2.write(filterLife(buffer) + "\n");
							if ("2012".equals(CommonUtils.getYear(val[17])))
								bw3.write(filterLife(buffer) + "\n");
							if ("2013".equals(CommonUtils.getYear(val[17])))
								bw4.write(filterLife(buffer) + "\n");
							buffer.setLength(0);
						}
						if (value != "\r\n") {
							buffer.append(value);
						}

					} else {
						if (value != "\r\n") {
							buffer.append(value);
						}
					}
				}
				row++;
			}
			// д�����һ��
			if (buffer.length() != 0) {
				String[] val = buffer.toString().split(",");

				if ("2010".equals(CommonUtils.getYear(val[17])))
					bw1.write(filterLife(buffer) + "\n");
				if ("2011".equals(CommonUtils.getYear(val[17])))
					bw2.write(filterLife(buffer) + "\n");
				if ("2012".equals(CommonUtils.getYear(val[17])))
					bw3.write(filterLife(buffer) + "\n");
				if ("2013".equals(CommonUtils.getYear(val[17])))
					bw4.write(filterLife(buffer) + "\n");
			}

			br.close();

			bw1.flush();
			bw1.close();
			bw2.flush();
			bw2.close();
			bw3.flush();
			bw3.close();
			bw4.flush();
			bw4.close();
			System.out.println("���ɳɹ�!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ���˳����յ���Ч��
	 * 
	 * @param line
	 * @return
	 */
	private static StringBuffer filterLife(StringBuffer sf) {
		String[] line1 = sf.toString().split(",",-1);
		String[] line = CommonUtils.formatCsvRow(line1);
		StringBuffer tmp = new StringBuffer();
		tmp.append(line[17]).append(",");
		tmp.append(line[27]).append(",");
		tmp.append(line[18]).append(",");
		tmp.append(line[19]).append(",");
		tmp.append(line[32]).append(",");
		tmp.append(line[22]).append(",");
		tmp.append(line[28]).append(",");
		tmp.append(line[4]).append(",");
		tmp.append(line[1]).append(",");
		if (line.length > 71) {
			int length = line.length;
			StringBuffer end = new StringBuffer();

			for (int i = 70; i < length; i++) {
				end.append(line[i]);
			}
			end.toString().replace(",", " ");
			tmp.append(end).append(",");
		} else {
			tmp.append(line[70]).append(",");
		}
		tmp.append(line[8]).append(",");
		tmp.append(line[9]).append(",");
		tmp.append(line[39]).append(",");//M
		tmp.append(line[41]).append(",");//N
		tmp.append(line[42]).append(",");
		tmp.append(line[43]).append(",");//p
		tmp.append(line[45]).append(",");
		tmp.append(line[49]).append(",");//R
		tmp.append(line[51]).append(",");//S
		tmp.append(line[53]).append(",");
		tmp.append(line[54]).append(",");
		tmp.append(line[55]).append(",");
		tmp.append(line[56]).append(",");
		tmp.append(line[57]).append(",");
		tmp.append(line[59]).append(",");
		tmp.append(line[60]).append(",");
		return tmp;
	}

	/**
	 * ���˶�Ԫ�����ļ�
	 * 
	 * @param sf
	 * @return
	 */
	private static StringBuffer filterMul(StringBuffer sf) {
		String[] line1 = sf.toString().split(",",-1);
		String[] line = CommonUtils.formatCsvRow(line1);
		
		StringBuffer tmp = new StringBuffer();
		tmp.append(line[17]).append(",");
		tmp.append(line[2]).append(",");
		tmp.append(line[18]).append(",");
		tmp.append(line[19]).append(",");
		tmp.append(line[21]).append(",");
		tmp.append(line[20]).append(",");
		tmp.append(line[4]).append(",");
		tmp.append(line[1]).append(",");
		if (line.length > 56) {
			int length = line.length;
			StringBuffer end = new StringBuffer();

			for (int i = 69; i < length; i++) {
				end.append(line[i]);
			}
			end.toString().replace(",", " ");
			tmp.append(end).append(",");
		} else {
			tmp.append(line[55]).append(",");
		}
		tmp.append(line[8]).append(",");
		tmp.append(line[9]).append(",");
		tmp.append(line[10]).append(",");
		tmp.append(line[25]).append(",");
		tmp.append(line[26]).append(",");
		tmp.append(line[29]).append(",");
		tmp.append(line[33]).append(",");
		tmp.append(line[35]).append(",");
		tmp.append(line[40]).append(",");
		tmp.append(line[46]).append(",");
		tmp.append(line[41]).append(",");
		tmp.append(line[52]).append(",");
		tmp.append(line[42]).append(",");
		tmp.append(line[43]).append(",");
		tmp.append(line[44]).append(",");
		tmp.append(line[47]).append(",");
		tmp.append(line[48]).append(",");
		tmp.append(line[49]).append(",");
		return tmp;
	}

	private static StringBuffer filterBenz(StringBuffer sf) {
		String[] line1 = sf.toString().split(",",-1);
		String[] line = CommonUtils.formatCsvRow(line1);
		StringBuffer tmp = new StringBuffer();
		tmp.append(line[17]).append(",");
		tmp.append(line[2]).append(",");
		tmp.append(line[18]).append(",");
		tmp.append(line[19]).append(",");
		tmp.append(line[23]).append(",");
		tmp.append(line[20]).append(",");
		tmp.append(line[22]).append(",");
		tmp.append(line[4]).append(",");
		tmp.append(line[1]).append(",");
		if (line.length > 66) {
			int length = line.length;
			StringBuffer end = new StringBuffer();

			for (int i = 69; i < length; i++) {
				end.append(line[i]);
			}
			end.toString().replace(",", " ");
			tmp.append(end).append(",");
		} else {
			tmp.append(line[65]).append(",");
		}
		tmp.append(line[8]).append(",");
		tmp.append(line[9]).append(",");
		tmp.append(line[10]).append(",");
		tmp.append(line[28]).append(",");
		tmp.append(line[29]).append(",");
		tmp.append(line[30]).append(",");
		tmp.append(line[32]).append(",");
		tmp.append(line[37]).append(",");
		tmp.append(line[39]).append(",");
		tmp.append(line[44]).append(",");
		tmp.append(line[46]).append(",");
		tmp.append(line[48]).append(",");
		tmp.append(line[53]).append(",");
		tmp.append(line[52]).append(",");
		tmp.append(line[58]).append(",");
		tmp.append(line[59]).append(",");
		tmp.append(line[60]).append(",");
		return tmp;
	}



}
