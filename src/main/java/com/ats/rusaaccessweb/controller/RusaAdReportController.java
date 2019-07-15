package com.ats.rusaaccessweb.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.ExceUtil;
import com.ats.rusaaccessweb.common.ExportToExcel;
import com.ats.rusaaccessweb.model.CurricularActivityCnts;
import com.ats.rusaaccessweb.model.dashb.CompetitiveExamReport;
import com.ats.rusaaccessweb.model.dashb.FacultyResearchDetail;
import com.ats.rusaaccessweb.model.dashb.GrantRecivResrchReport;
import com.ats.rusaaccessweb.model.dashb.MaleFemaleRatioResponse;
import com.ats.rusaaccessweb.model.dashb.PlacementUgPgStud;
import com.ats.rusaaccessweb.model.dashb.SubjectResrchReport;
import com.ats.rusasoft.util.ItextPageEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@Scope("session")
public class RusaAdReportController {

	RestTemplate rest = new RestTemplate();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	String curDateTime = dateFormat.format(cal.getTime());
	String redirect = null;

	MultiValueMap<String, Object> map = null;

	@RequestMapping(value = "/coExCurricularAct", method = RequestMethod.POST)
	public void getCntCurriclarActivity(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular, Co-Curricular and Extra Curricular Activities";

		try {
			map = new LinkedMultiValueMap<String, Object>();
			//String yearId = "12";
			String yearId = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			map.add("yearId", yearId);

			CurricularActivityCnts[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getCntCurriclarActivity", map, CurricularActivityCnts[].class);
			List<CurricularActivityCnts> curriActList = new ArrayList<>(Arrays.asList(resArray));

			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.5f, 5.9f, 2.3f, 2.3f, 2.3f });
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Curricular Activities", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Co-Curricular Activities", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Extra Curricular Activities", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < curriActList.size(); i++) {
					// System.err.println("I " + i);
					CurricularActivityCnts curricular = curriActList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + curricular.getInstituteName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + curricular.getCurriCnt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + curricular.getCoCurryCnt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + curricular.getExtraCoCarryCnt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Institute Name");
					rowData.add("No. of Curricular Activities");
					rowData.add("No. of Co-Curricular Activities");
					rowData.add("No. of Extra Co-Curricular Activities");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < curriActList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + curriActList.get(i).getInstituteName());
						rowData.add("" + curriActList.get(i).getCurriCnt());
						rowData.add("" + curriActList.get(i).getCoCurryCnt());
						rowData.add("" + curriActList.get(i).getExtraCoCarryCnt());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "Academic Year:" + temp_ac_year + " ", "", 'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in showProgReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/getCompitetiveExmReport", method = RequestMethod.POST)
	public void getCompitetiveExmReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Competitive Exam(No. of Student appeared for Conpetitive Exam at various levels)";

		try {
			map = new LinkedMultiValueMap<String, Object>();
			
			String yearId = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			map.add("yearId", yearId);

			CompetitiveExamReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getCompetitiveExamReport", map, CompetitiveExamReport[].class);
			List<CompetitiveExamReport> competExmList = new ArrayList<>(Arrays.asList(resArray));

			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.5f, 5.9f, 2.3f, 2.3f, 2.3f });
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("State Level", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("National Level", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("International Level", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < competExmList.size(); i++) {
					// System.err.println("I " + i);
					CompetitiveExamReport competExm = competExmList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + competExm.getInstituteName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + competExm.getStateLevelAppear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + competExm.getNationalLevelAppear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + competExm.getIntLevelAppear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Institute Name");
					rowData.add("State Level");
					rowData.add("National Level");
					rowData.add("International Level");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < competExmList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + competExmList.get(i).getInstituteName());
						rowData.add("" + competExmList.get(i).getStateLevelAppear());
						rowData.add("" + competExmList.get(i).getNationalLevelAppear());
						rowData.add("" + competExmList.get(i).getIntLevelAppear());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Academic Year:" + temp_ac_year + " ", "", 'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in getCompitetiveExmReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/getMaleFemaleRatioReport", method = RequestMethod.POST)
	public void getMaleFemaleRatioReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Male Female Ratio";

		try {
			map = new LinkedMultiValueMap<String, Object>();
			String yearId = request.getParameter("ac_year");
			//int instId = Integer.parseInt(request.getParameter("instituteId"));
			map.add("yearId", yearId);
			//map.add("instId", instId);

			MaleFemaleRatioResponse[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getMaleFemaleRatioReport", map, MaleFemaleRatioResponse[].class);
			List<MaleFemaleRatioResponse> malFeMalRatioList = new ArrayList<>(Arrays.asList(resArray));

			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.5f, 5.9f, 2.3f, 2.3f, 2.3f, 2.3f, 2.3f, 2.3f });
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				
				table.addCell(hcell);
				
				hcell = new PdfPCell(new Phrase("Male Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Female Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Transgender Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Male Students", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Female Students", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Transgender Students", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < malFeMalRatioList.size(); i++) {
					// System.err.println("I " + i);
					MaleFemaleRatioResponse ratio = malFeMalRatioList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getInstituteName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getMaleFaculty(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getFemaleFaculty(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getTransFaculty(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getMaleStudent(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getFemaleStudent(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getTransStudent(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Institute Name");
					rowData.add("Male Faculty");
					rowData.add("Female Faculty");
					rowData.add("Transgender Faculty");
					rowData.add("Male Students");
					rowData.add("Female Students");
					rowData.add("Transgender Students");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < malFeMalRatioList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + malFeMalRatioList.get(i).getInstituteName());
						rowData.add("" + malFeMalRatioList.get(i).getMaleFaculty());
						rowData.add("" + malFeMalRatioList.get(i).getFemaleFaculty());
						rowData.add("" + malFeMalRatioList.get(i).getTransFaculty());
						rowData.add("" + malFeMalRatioList.get(i).getMaleStudent());
						rowData.add("" + malFeMalRatioList.get(i).getFemaleStudent());
						rowData.add("" + malFeMalRatioList.get(i).getTransStudent());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, "  ", "", 'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in getMaleFemaleRatioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/getGrntRecvResrchReprt", method = RequestMethod.POST)
	public void getGrntRecvResrchReprt(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Grant received for research";

		try {
			map = new LinkedMultiValueMap<String, Object>();
			String yearId = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			map.add("yearId", yearId);

			
			GrantRecivResrchReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getGrantReceivReport", map, GrantRecivResrchReport[].class);
			List<GrantRecivResrchReport> grntRecvList = new ArrayList<>(Arrays.asList(resArray));

			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.5f, 5.9f, 2.3f});
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Grant Amount", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);


				int index = 0;
				for (int i = 0; i < grntRecvList.size(); i++) {
					// System.err.println("I " + i);
					GrantRecivResrchReport grant = grntRecvList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getInstituteName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getGrantAmt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);					

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Institute Name");
					rowData.add("Grant Amount");
				

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < grntRecvList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + grntRecvList.get(i).getInstituteName());
						rowData.add("" + grntRecvList.get(i).getGrantAmt());						

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, " Academic Year:" + temp_ac_year + " ", "", 'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in getGrntRecvResrchReprt " + e.getMessage());
			e.printStackTrace();

		}
	}
	
	@RequestMapping(value = "/getSubjctRsrchReport", method = RequestMethod.POST)
	public void getSubjctRsrchReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Subject wise research";

		String instituteName=null;
		try {
			map = new LinkedMultiValueMap<String, Object>();
			
			String yearId = request.getParameter("ac_year");
			int instId = Integer.parseInt(request.getParameter("instituteId"));
			String temp_ac_year = request.getParameter("temp_ac_year");
			//System.out.println(yearId+"---"+instId);
			map.add("yearId", yearId);
			map.add("instId", instId);

			SubjectResrchReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getSubjectResrchRep", map, SubjectResrchReport[].class);
			List<SubjectResrchReport> subRsrchList = new ArrayList<>(Arrays.asList(resArray));

			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.5f, 5.9f, 2.3f, 2.3f});
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				/*
				 * hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Facuty Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Affiliated Guide", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Student Registerred", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < subRsrchList.size(); i++) {
					// System.err.println("I " + i);
					SubjectResrchReport subRsrch = subRsrchList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + subRsrch.getFacultyName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + subRsrch.getAffiliatedGuide(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + subRsrch.getStudentReg(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					instituteName=subRsrch.getInstituteName();

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Institute name :" + instituteName + ""));
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Faculty Name");
					rowData.add("No. of Affiliated Guide");
					rowData.add("No. of Student Registerred");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < subRsrchList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + subRsrchList.get(i).getFacultyName());
						rowData.add("" + subRsrchList.get(i).getAffiliatedGuide());
						rowData.add("" + subRsrchList.get(i).getStudentReg());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						instituteName=subRsrchList.get(i).getInstituteName();

					}

					XSSFWorkbook wb = null;
					try {
						String leaveSum1 = "Academic Year: " + temp_ac_year + ",";
						String leaveSum2 = "Institute Name:" + instituteName + "";
						String reportSummary =leaveSum1 + "" + leaveSum2;
						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, reportSummary, "", 'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in getSubjctRsrchReport " + e.getMessage());
			e.printStackTrace();

		}

	}
	
	@RequestMapping(value = "/getFacultyRsrchReport", method = RequestMethod.POST)
	public void getFacultyRsrchReport(HttpServletRequest request, HttpServletResponse response) {

		String instituteName=null;
		String reportName = "Teacher Participate in research";
 		try {
			map = new LinkedMultiValueMap<String, Object>();
			
			int instId = Integer.parseInt(request.getParameter("instituteId"));
			//System.out.println("---"+instId);
			
			map.add("instId", instId);

			FacultyResearchDetail[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getFacultyResrchRep", map, FacultyResearchDetail[].class);
			List<FacultyResearchDetail> facRsrchList = new ArrayList<>(Arrays.asList(resArray));

			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			String FILE_PATH = Constants.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = new FileOutputStream(FILE_PATH);
			try {
				writer = PdfWriter.getInstance(document, out);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			String header = "";
			String title = "                 ";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			ItextPageEvent event = new ItextPageEvent(header, title, "", "");

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.5f, 5.9f, 2.3f, 2.3f, 2.3f});
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				/*
				 * hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Facuty Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title of Project", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Sanctioed Grant Amount", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				

				hcell = new PdfPCell(new Phrase("Sponsoring Authority", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < facRsrchList.size(); i++) {
					// System.err.println("I " + i);
					FacultyResearchDetail facRsrch = facRsrchList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + facRsrch.getFacultyFirstName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + facRsrch.getProjName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + facRsrch.getProjTotalAmt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + facRsrch.getProjSponsor(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					instituteName=facRsrch.getInstituteName();

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Institute Name:" + instituteName + ""));
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

				document.add(table);

				int totalPages = writer.getPageNumber();

				// System.out.println("Page no " + totalPages);

				document.close();
				int p = Integer.parseInt(request.getParameter("p"));
				System.err.println("p " + p);

				if (p == 1) {

					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition",
								String.format("inline; filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							// System.out.println("Excep in Opening a Pdf File");
							e.printStackTrace();
						}
					}
				} else {

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Faculty Name");
					rowData.add("Title of Project");
					rowData.add("Sanctioned Grant Amount");
					rowData.add("Sponsoring Authority");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < facRsrchList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + facRsrchList.get(i).getFacultyFirstName());
						rowData.add("" + facRsrchList.get(i).getProjName());
						rowData.add("" + facRsrchList.get(i).getProjTotalAmt());
						rowData.add("" + facRsrchList.get(i).getProjSponsor());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						instituteName= facRsrchList.get(i).getInstituteName();

					}

					XSSFWorkbook wb = null;
					try {
						String leaveSum1 = "Institute Name: " + instituteName + "";
						wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName, leaveSum1, "", 'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						response.setHeader("Content-disposition",
								"attachment; filename=" + reportName + "-" + date + ".xlsx");
						wb.write(response.getOutputStream());

					} catch (IOException ioe) {
						throw new RuntimeException("Error writing spreadsheet to output stream");
					} finally {
						if (wb != null) {
							wb.close();
						}
					}

				}

			} catch (DocumentException ex) {

				// System.out.println("Pdf Generation Error: " + ex.getMessage());

				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in getFacultyRsrchReport " + e.getMessage());
			e.printStackTrace();

		}

	}
}
