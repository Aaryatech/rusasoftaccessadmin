package com.ats.rusaaccessweb.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.ExceUtil;
import com.ats.rusaaccessweb.common.ExportToExcel;
import com.ats.rusaaccessweb.model.ProgramType;
import com.ats.rusaaccessweb.model.dashb.AcademicYear;
import com.ats.rusaaccessweb.reportnew.model.AdmissionsAgainstCategory;
import com.ats.rusaaccessweb.reportnew.model.AluminiAssoMeetReport;
import com.ats.rusaaccessweb.reportnew.model.AluminiEngagement;
import com.ats.rusaaccessweb.reportnew.model.AvgEnrollmentPrcnt;
import com.ats.rusaaccessweb.reportnew.model.AwardRecogDetailReport;
import com.ats.rusaaccessweb.reportnew.model.BookPublicationDetReport;
import com.ats.rusaaccessweb.reportnew.model.CapabilityEnhancementDev;
import com.ats.rusaaccessweb.reportnew.model.Cast;
import com.ats.rusaaccessweb.reportnew.model.DistinguishedAlumini;
import com.ats.rusaaccessweb.reportnew.model.EGovernenceOperation;
import com.ats.rusaaccessweb.reportnew.model.ExtensionActivityReport;
import com.ats.rusaaccessweb.reportnew.model.FacParticipationInBodies;
import com.ats.rusaaccessweb.reportnew.model.FinancialSuppToProfMem;
import com.ats.rusaaccessweb.reportnew.model.GenderEquityProg;
import com.ats.rusaaccessweb.reportnew.model.GetAluminiEngagementReport;
import com.ats.rusaaccessweb.reportnew.model.GetAvgStudYearwise;
import com.ats.rusaaccessweb.reportnew.model.GetMissions;
import com.ats.rusaaccessweb.reportnew.model.GetTeachersUsingICT;
import com.ats.rusaaccessweb.reportnew.model.GetVisions;
import com.ats.rusaaccessweb.reportnew.model.GovtScheme;
import com.ats.rusaaccessweb.reportnew.model.IQACQualInititive;
import com.ats.rusaaccessweb.reportnew.model.IniToEngageLocComm;
import com.ats.rusaaccessweb.reportnew.model.LibAutoLMSInfo;
import com.ats.rusaaccessweb.reportnew.model.LibSpecFacilities;
import com.ats.rusaaccessweb.reportnew.model.NoFacultyFinSupp;
import com.ats.rusaaccessweb.reportnew.model.NoOfBookReport;
import com.ats.rusaaccessweb.reportnew.model.NoOfGenderEquityProg;
import com.ats.rusaaccessweb.reportnew.model.NoOfMentorsAssignedStudent;
import com.ats.rusaaccessweb.reportnew.model.NoOfPrograms;
import com.ats.rusaaccessweb.reportnew.model.NoOfStudTeachLinkageReport;
import com.ats.rusaaccessweb.reportnew.model.NoOfUniversalvalues;
import com.ats.rusaaccessweb.reportnew.model.OtherThanGovtScheme;
import com.ats.rusaaccessweb.reportnew.model.PhdGuideReport;
import com.ats.rusaaccessweb.reportnew.model.PlagarismCodeEthicsReport;
import com.ats.rusaaccessweb.reportnew.model.QualInitiativeAssurance;
import com.ats.rusaaccessweb.reportnew.model.RareBookManuscriptSpec;
import com.ats.rusaaccessweb.reportnew.model.ReaddressalOfStudGrievennce;
import com.ats.rusaaccessweb.reportnew.model.ResearchProjNoPerTeacher;
import com.ats.rusaaccessweb.reportnew.model.SportsCulturalActivityComp;
import com.ats.rusaaccessweb.reportnew.model.StakeHolderFBReport;
import com.ats.rusaaccessweb.reportnew.model.StudEnrooledForProgramReport;
import com.ats.rusaaccessweb.reportnew.model.StudQualifyingExamReport;
import com.ats.rusaaccessweb.reportnew.model.StudentPerformanceOutcome;
import com.ats.rusaaccessweb.reportnew.model.TeacherStudUsingLib;
import com.ats.rusaaccessweb.reportnew.model.TrainProgForTeacherStaff;
import com.ats.rusaaccessweb.reportnew.model.TrainProgOrgnizedForTeach;
import com.ats.rusaaccessweb.reportnew.model.UniversalValPromot;
import com.ats.rusaaccessweb.reportnew.model.ValueAddedCoursesReport;
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
public class ReportNewController {
	
	RestTemplate rest = new RestTemplate();
	DecimalFormat decimalFormat = new DecimalFormat("0.00");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	String curDateTime = dateFormat.format(cal.getTime());
	String redirect = null;

	MultiValueMap<String, Object> map = null;

	@RequestMapping(value = "/showReports", method = RequestMethod.GET)
	public ModelAndView showReports(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/report_dashboard");

			map = new LinkedMultiValueMap<String, Object>();
			map.add("type", 1);

			AcademicYear[] quolArray = rest.postForObject(Constants.url + "getAcademicYearListByTypeId", map,
					AcademicYear[].class);
			List<AcademicYear> acaYearList = new ArrayList<>(Arrays.asList(quolArray));

			model.addObject("acaYearList", acaYearList);

			ProgramType[] progTypes = rest.getForObject(Constants.url + "getAllProgramType", ProgramType[].class);
			List<ProgramType> progTypeList = new ArrayList<>(Arrays.asList(progTypes));
			model.addObject("progTypeList", progTypeList);

			map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			Cast[] catsArray = restTemplate.getForObject(Constants.url + "getAllCastCategory", Cast[].class);
			List<Cast> castList = new ArrayList<>(Arrays.asList(catsArray));
			System.err.println("castList " + castList.toString());

			model.addObject("castList", castList);

		} catch (Exception e) {

			System.err.println("Exce in showReports " + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/showProgReport", method = RequestMethod.POST)
	public void showProgReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : No. of Certificate/Diploma Programs";

		ModelAndView model = null;
		try {
			// String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			NoOfPrograms[] resArray = rest.postForObject(Constants.url + "getNoOfProgramsList", map,
					NoOfPrograms[].class);
			List<NoOfPrograms> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				// table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
				table.setTotalWidth(document.getPageSize().getWidth() - 80);
				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Program", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Level", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Duration (Months)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Introduction Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Approved By", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoOfPrograms prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNameOfProgram(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getLevelOfProgram(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getMonthDuration(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYearOfIntrod(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getApprovedBy(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Name of Program");
					rowData.add("Level");
					rowData.add("Duration (Months)");
					rowData.add("Introduction Year");
					rowData.add("Approved By");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getNameOfProgram());
						rowData.add("" + progList.get(i).getLevelOfProgram());
						rowData.add("" + progList.get(i).getMonthDuration());
						rowData.add("" + progList.get(i).getYearOfIntrod());
						rowData.add("" + progList.get(i).getApprovedBy());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'F');
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

	/*
	 * private XSSFWorkbook createWorkbook(List<ExportToExcel>
	 * exportToExcelList,String instName,String reportName) throws IOException {
	 * XSSFWorkbook wb = new XSSFWorkbook(); XSSFSheet sheet =
	 * wb.createSheet("Sheet1"); sheet.createFreezePane(0, 3); CellStyle
	 * style=wb.createCellStyle(); style.setAlignment(CellStyle.ALIGN_RIGHT);
	 * 
	 * Row titleRow = sheet.createRow(0); titleRow.setHeightInPoints(20);
	 * titleRow.setRowStyle(style); Cell titleCell = titleRow.createCell(0);
	 * 
	 * //titleCell.setCellValue("Report"); titleCell.setCellValue(instName);
	 * 
	 * sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$F$1"));
	 * 
	 * 
	 * Row titleRow3 = sheet.createRow(0); titleRow3.setHeightInPoints(20);
	 * titleRow3.setRowStyle(style); Cell titleCell3 = titleRow3.createCell(0);
	 * titleCell3.setCellValue("Academic Year :2018-2019 ");//Need Dynamic
	 * 
	 * //titleCell.setCellValue("Report"); //titleCell3.setCellValue(instName);
	 * 
	 * sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$F$2"));
	 * 
	 * CellStyle style2=wb.createCellStyle();
	 * style2.setAlignment(CellStyle.ALIGN_CENTER); Row titleRow2 =
	 * sheet.createRow(1); titleRow2.setHeightInPoints(20);
	 * titleRow2.setRowStyle(style2);
	 * 
	 * XSSFFont font=wb.createFont();
	 * 
	 * 
	 * Cell titleCell2 = titleRow2.createCell(0);
	 * 
	 * //titleCell2.setCellValue("Sub "); titleCell2.setCellValue(reportName);
	 * 
	 * 
	 * sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$F$2"));
	 * 
	 * 
	 * writeHeaders(wb, sheet); writeHeaders(wb, sheet); writeHeaders(wb, sheet);
	 * 
	 * 
	 * for (int rowIndex = 0; rowIndex < exportToExcelList.size(); rowIndex++) {
	 * XSSFRow row = sheet.createRow(rowIndex+2); for (int j = 0; j <
	 * exportToExcelList.get(rowIndex).getRowData().size(); j++) {
	 * 
	 * XSSFCell cell = row.createCell(j);
	 * 
	 * cell.setCellValue(exportToExcelList.get(rowIndex).getRowData().get(j)); if
	 * ((rowIndex+2) == 2) cell.setCellStyle(createHeaderStyleNew(wb)); } //if
	 * (rowIndex == 0) //row.setRowStyle(createHeaderStyle(wb)); } return wb; }
	 * public void autoSizeColumns(Workbook workbook,int index) { int numberOfSheets
	 * = workbook.getNumberOfSheets(); for (int i = 0; i < numberOfSheets; i++) {
	 * Sheet sheet = workbook.getSheetAt(i); if (sheet.getPhysicalNumberOfRows() >
	 * 0) { Row row = sheet.getRow(index); row.setHeight((short)700);
	 * 
	 * Iterator<Cell> cellIterator = row.cellIterator(); while
	 * (cellIterator.hasNext()) { Cell cell = cellIterator.next(); int columnIndex =
	 * cell.getColumnIndex(); sheet.autoSizeColumn(columnIndex); } } } } private
	 * XSSFCellStyle createHeaderStyleNew(XSSFWorkbook workbook) { XSSFCellStyle
	 * style = workbook.createCellStyle(); style.setWrapText(true);
	 * style.setFillForegroundColor(new XSSFColor(new java.awt.Color(247, 161,
	 * 103)));
	 * 
	 * style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	 * style.setAlignment(CellStyle.ALIGN_CENTER);
	 * style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	 * 
	 * style.setBorderRight(CellStyle.BORDER_THIN);
	 * style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	 * style.setBorderBottom(CellStyle.BORDER_THIN);
	 * style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	 * style.setBorderLeft(CellStyle.BORDER_THIN);
	 * style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	 * style.setBorderTop(CellStyle.BORDER_THIN);
	 * style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	 * style.setDataFormat(1);
	 * 
	 * org.apache.poi.ss.usermodel.Font font =workbook.createFont();
	 * font.setFontName("Times New Roman");
	 * font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); font.setBold(true);
	 * font.setColor(HSSFColor.WHITE.index); style.setFont(font);
	 * 
	 * return style; }
	 */

	@RequestMapping(value = "/showFacPartiVarBodies", method = RequestMethod.POST)
	public void showFacPartiVarBodies(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : Percentage(%) of Participation in various University Bodies";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			FacParticipationInBodies[] resArray = rest.postForObject(Constants.url + "getFacParticipationInBodies", map,
					FacParticipationInBodies[].class);
			List<FacParticipationInBodies> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Faculty Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Member of", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("University", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Validity", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					FacParticipationInBodies prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getConLevel() + "-" + prog.getConName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getConUniversity(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getConTo(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of Faculty");
					rowData.add("Member of");
					rowData.add("University");
					rowData.add("Valid upto");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getFacultyFirstName());
						rowData.add("" + progList.get(i).getConLevel() + "" + progList.get(i).getConName());
						rowData.add("" + progList.get(i).getConUniversity());
						rowData.add("" + progList.get(i).getConTo());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						// wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
						// "Academic Year :" + temp_ac_year + " ");

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'F');

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

	// Report 11

	@RequestMapping(value = "/showAvgEnrollPrcntReport", method = RequestMethod.POST)
	public void showAvgEnrollPrcntReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching-Learing and Evaluation : Average Enrollment Percentage";
		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			AvgEnrollmentPrcnt[] resArray = rest.postForObject(Constants.url + "getAvgEnrollmentPrcnt", map,
					AvgEnrollmentPrcnt[].class);
			List<AvgEnrollmentPrcnt> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total Sanctioned Intake", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No of Student Admitted", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% Yearwise", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				double rslt = 0.0;

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					AvgEnrollmentPrcnt prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalSanctIntake(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoCurrentAdmitedStnt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					String tempprcnt = decimalFormat
							.format(prog.getNoCurrentAdmitedStnt() / prog.getTotalSanctIntake() * 100);

					// System.out.println("prog.getNoCurrentAdmitedStnt()" +
					// prog.getNoCurrentAdmitedStnt()
					// + "prog.getTotalSanctIntake()" + prog.getTotalSanctIntake());
					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					rslt = rslt + Double.parseDouble(tempprcnt);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */
				double n = 0.0;
				if (ac_year.equals("-5")) {
					n = (rslt / 5);
				} else {
					n = rslt;
				}

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Avg% :" + decimalFormat.format(n) + ""));
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
					rowData.add("Academic Year");
					rowData.add("Total Sanctioned Intake");
					rowData.add("Total No of Student Admitted");
					rowData.add("% Yearwise");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					double temp = 0.0;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						String tempprcnt = decimalFormat.format(progList.get(i).getNoCurrentAdmitedStnt()
								/ progList.get(i).getTotalSanctIntake() * 100);
						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getTotalSanctIntake());
						rowData.add("" + progList.get(i).getNoCurrentAdmitedStnt());

						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String leaveSum = "Average% :" + n + "";
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", leaveSum, 'E');

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

	// Report 10
	@RequestMapping(value = "/showAvgStudYearwiseReport", method = RequestMethod.POST)
	public void showAvgStudYearwiseReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching-Learing and Evaluation : Average % of Students from other States/Countries – Yearwise";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", "-5");

			GetAvgStudYearwise[] resArray = rest.postForObject(Constants.url + "getAvgStudYearwiseLocWise", map,
					GetAvgStudYearwise[].class);
			List<GetAvgStudYearwise> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(7);

			table.setHeaderRows(1);
			double f1 = 0;
			double f3 = 0;
			double f2 = 0;
			double f4 = 0;
			double f5 = 0;
			double s1 = 0;
			double s3 = 0;
			double s2 = 0;
			double s4 = 0;
			double s5 = 0;
			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Location", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CAY", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CAY-1", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CAY-2", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CAY-3", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CAY-4", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					GetAvgStudYearwise prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getLocationName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcYearAdmiStud1(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcYearAdmiStud2(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcYearAdmiStud3(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcYearAdmiStud4(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcYearAdmiStud5(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					f1 = f1 + prog.getAcYearAdmiStud1();
					f2 = f2 + prog.getAcYearAdmiStud2();
					f3 = f3 + prog.getAcYearAdmiStud3();
					f4 = f4 + prog.getAcYearAdmiStud4();
					f5 = f5 + prog.getAcYearAdmiStud5();
					s1 = prog.getTotStud1();
					s2 = prog.getTotStud2();
					s3 = prog.getTotStud3();
					s4 = prog.getTotStud4();
					s5 = prog.getTotStud5();

				}

				System.err.println("f1 is " + f1);
				System.err.println("s1 is " + s1);
				String r1 = null;
				String r2 = null;
				String r3 = null;
				String r4 = null;
				String r5 = null;

				if (s1 != 0) {
					r1 = decimalFormat.format((f1 / s1) * 100);
				} else {
					r1 = "0";
				}
				if (s2 != 0) {
					r2 = decimalFormat.format((f2 / s2) * 100);
				} else {
					r2 = "0";
				}
				if (s3 != 0) {
					r3 = decimalFormat.format((f3 / s3) * 100);
				} else {
					r3 = "0";
				}
				if (s4 != 0) {
					r4 = decimalFormat.format((f4 / s4) * 100);
				} else {
					r4 = "0";
				}
				if (s5 != 0) {
					r5 = decimalFormat.format((f5 / s5) * 100);
				} else {
					r5 = "0";
				}

				PdfPCell cell1;
				cell1 = new PdfPCell(new Phrase(String.valueOf(index + 1), headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell1);

				cell1 = new PdfPCell(new Phrase("" + "%Year", headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell1);

				cell1 = new PdfPCell(new Phrase("" + r1, headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell1);

				cell1 = new PdfPCell(new Phrase("" + r2, headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell1);

				cell1 = new PdfPCell(new Phrase("" + r3, headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell1);

				cell1 = new PdfPCell(new Phrase("" + r4, headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell1);

				cell1 = new PdfPCell(new Phrase("" + r5, headFontData));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell1);

				double a = 0;
				double c = 0;
				for (int i = 0; i < progList.size(); i++) {
					a = a + progList.get(i).getAcYearAdmiStud1() + progList.get(i).getAcYearAdmiStud2()
							+ progList.get(i).getAcYearAdmiStud3() + progList.get(i).getAcYearAdmiStud4()
							+ progList.get(i).getAcYearAdmiStud5();
					c = c + progList.get(i).getTotStud1() + progList.get(i).getTotStud2()
							+ progList.get(i).getTotStud3() + progList.get(i).getTotStud4()
							+ progList.get(i).getTotStud5();
				}

				double n = Double.parseDouble(r1) + Double.parseDouble(r2) + Double.parseDouble(r4)
						+ Double.parseDouble(r5) + Double.parseDouble(r3);

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Total No. of Students from other state and countries :" + a + ""));
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Total No. of Students Enrolled :" + c + ""));

				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Average % :" + decimalFormat.format(n / 5) + ""));

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
					rowData.add("Location");
					rowData.add("ACY");
					rowData.add("ACY-1");
					rowData.add("ACY-2");
					rowData.add("ACY-3");
					rowData.add("ACY-4");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getLocationName());
						rowData.add("" + progList.get(i).getAcYearAdmiStud1());
						rowData.add("" + progList.get(i).getAcYearAdmiStud2());
						rowData.add("" + progList.get(i).getAcYearAdmiStud3());
						rowData.add("" + progList.get(i).getAcYearAdmiStud4());
						rowData.add("" + progList.get(i).getAcYearAdmiStud5());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();

					rowData.add("" + 3);

					rowData.add("" + "% Per Year");
					rowData.add("" + r1);
					rowData.add("" + r2);
					rowData.add("" + r3);
					rowData.add("" + r4);
					rowData.add("" + r5);

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					XSSFWorkbook wb = null;
					try {

						String leaveSum = "Total No. of Students from other State and Countries : " + a + "";
						String leaveSum1 = "Total No. of Students Enrolled : " + c + "";
						String leaveSum2 = "Average % : " + decimalFormat.format(n / 5) + "";

						String reportSummary = leaveSum + "" + leaveSum1 + "" + leaveSum2;

						// System.out.println("Excel List :" + exportToExcelList.toString());

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", reportSummary, 'G');
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

	@RequestMapping(value = "/showTeachersUsingIctReport", method = RequestMethod.POST)
	public void showTeachersUsingIctReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching-Learing and Evaluation : Teachers Using ICT";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			GetTeachersUsingICT[] resArray = rest.postForObject(Constants.url + "getTeachersUsingICT", map,
					GetTeachersUsingICT[].class);
			List<GetTeachersUsingICT> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Teachers using ICT/LMS/No. of Resources", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total  No. of Full Time Teachers", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Teachers using ICT/LMS/No. of Resources", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					GetTeachersUsingICT prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTechersUsingIct(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalFaculty(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					String tempprcnt = decimalFormat.format(prog.getTechersUsingIct() / prog.getTotalFaculty() * 100);

					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("No. of Teachers using ICT/LMS/No. of Resources");
					rowData.add("Total  No. of Full Time Teachers");
					rowData.add("% of Teachers using ICT/LMS/No. of Resources");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getTechersUsingIct());
						rowData.add("" + progList.get(i).getTotalFaculty());

						String tempprcnt = decimalFormat
								.format(progList.get(i).getTechersUsingIct() / progList.get(i).getTotalFaculty() * 100);
						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'E');
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

	@RequestMapping(value = "/showNoOfMentorsAssignedStudentReport", method = RequestMethod.POST)
	public void showNoOfMentorsAssignedStudentReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching-Learing and Evaluation : Total No. of Mentors, No. of Students Assigned";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			NoOfMentorsAssignedStudent[] resArray = rest.postForObject(Constants.url + "getNoOfMentorsAssignedStudent",
					map, NoOfMentorsAssignedStudent[].class);
			List<NoOfMentorsAssignedStudent> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("No. of  Faculty Mentor", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Student Mentoring", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Mentor Mentee Ratio", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoOfMentorsAssignedStudent prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalFacMentor(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalStudMentoring(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					String tempprcnt = null;
					if (prog.getTotalFacMentor() == 0.0) {
						tempprcnt = "0";
					} else {
						tempprcnt = decimalFormat.format(prog.getTotalStudMentoring() / prog.getTotalFacMentor() * 100);
					}
					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("No. of  Faculty Mentor");
					rowData.add("No. of Student Mentoring");
					rowData.add("Mentor Mentee Ratio");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getTotalFacMentor());
						rowData.add("" + progList.get(i).getTotalStudMentoring());

						String tempprcnt = null;
						if (progList.get(i).getTotalFacMentor() == 0.0) {
							tempprcnt = "0";
						} else {
							tempprcnt = decimalFormat.format(progList.get(i).getTotalStudMentoring()
									/ progList.get(i).getTotalFacMentor() * 100);
						}

						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'D');
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

	@RequestMapping(value = "/showStudPerformanceOutconmeReport", method = RequestMethod.POST)
	public void showStudPerformanceOutconmeReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching-Learing and Evaluation : Students Performance & Learning Outcomes";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			// String temp_ac_year = request.getParameter("temp_ac_year");
			int programId = Integer.parseInt(request.getParameter("prog_name"));
			int programType = Integer.parseInt(request.getParameter("prog_type"));
			HttpSession session = request.getSession();
			String temp_prog_name = request.getParameter("temp_prog_name");
			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("programId", programId);
			StudentPerformanceOutcome[] resArray = rest.postForObject(Constants.url + "getStudPerformancePo", map,
					StudentPerformanceOutcome[].class);
			List<StudentPerformanceOutcome> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			System.out.println("Inside Pdf showStudPerformanceOutconmeReport" + progList.toString());
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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
			String ProgramName = null;

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String repDate = DF2.format(new Date());

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Name of Course", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Course Outcome", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					StudentPerformanceOutcome prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSubName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getCoName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					ProgramName = prog.getProgramName();

				}
				System.out.println("pro************" + ProgramName);
				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("Program Name :" + ProgramName + ""));
				document.add(new Paragraph("Program Type :" + temp_prog_name + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Name of Course");
					rowData.add("Course Outcome");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getSubName());
						rowData.add("" + progList.get(i).getCoName());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						String leaveSum = "Program: " + progList.get(0).getProgramName() + "";
						String leaveSum1 = ",Program Type: " + temp_prog_name + "";

						String reportSummary = leaveSum + "" + leaveSum1;

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, reportSummary, "",
								'C');
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

	@RequestMapping(value = "/showAdmissionsAgainstCatReport", method = RequestMethod.POST)
	public void showAdmissionsAgainstCatReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching-Learing and Evaluation : Admissions feeds against reservation category";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			int catId = Integer.parseInt(request.getParameter("catId"));
			String temp_ac_year = request.getParameter("temp_ac_year");
			String temp_cat = request.getParameter("temp_cat");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("catId", catId);
			map.add("acYearList", ac_year);

			AdmissionsAgainstCategory[] resArray = rest.postForObject(Constants.url + "getAdmisssionsAgainstCat", map,
					AdmissionsAgainstCategory[].class);
			List<AdmissionsAgainstCategory> progList = new ArrayList<>(Arrays.asList(resArray));
			System.out.println("showAvgStudYearwiseReport------"+progList);
			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Student Admitted in Reservation Category", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No. of  Student Admitted", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				double rslt = 0.0;
				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					AdmissionsAgainstCategory prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getCatTotStudent(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSeatsAvaailable(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					String tempprcnt = decimalFormat.format(prog.getCatTotStudent() / prog.getSeatsAvaailable() * 100);

					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					try {
					rslt = rslt + Double.parseDouble(tempprcnt);
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("  Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Average%:" + decimalFormat.format(rslt / 5) + ""));
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
					rowData.add("Academic Year");
					rowData.add("No. of Student Admitted in Reservation Category");
					rowData.add("Total No. of  Student Admitted");
					rowData.add("% Year");
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getCatTotStudent());
						rowData.add("" + progList.get(i).getSeatsAvaailable());

						String tempprcnt = decimalFormat.format(
								progList.get(i).getCatTotStudent() / progList.get(i).getSeatsAvaailable() * 100);
						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String leaveSum = "Academic Year: " + temp_ac_year + "";
						String leaveSum1 = ",Category: " + temp_cat + "";

						String reportSummary = leaveSum + "" + leaveSum1;
						double reportSummary1 = rslt / 5;

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, reportSummary,
								"Average%:" + String.valueOf(reportSummary1), 'E');
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

	@RequestMapping(value = "/showLibLMSInfoReport", method = RequestMethod.POST)
	public void showLibLMSInfoReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Library Automation and ILMS Information";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acadYear", ac_year); // on 18-06-2019 Mahendra

			LibAutoLMSInfo[] resArray = rest.postForObject(Constants.url + "getLibLMSInfo", map,
					LibAutoLMSInfo[].class);
			List<LibAutoLMSInfo> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Name of Integrated LMS", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year of Automation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Version of ILMS", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Existing Users", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					LibAutoLMSInfo prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSoftName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSoftVersion(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getUsersOfLms(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Name of Integrated LMS");
					rowData.add("Year of Automation");
					rowData.add("Version of ILMS");
					rowData.add("No. of Existing Users");
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getSoftName());
						rowData.add("" + progList.get(i).getYear());
						rowData.add("" + progList.get(i).getSoftVersion());

						rowData.add("" + progList.get(i).getUsersOfLms());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"For Academic Year :" + temp_ac_year, "", 'E');
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

	@RequestMapping(value = "/showRareBookManuscriptReport", method = RequestMethod.POST)
	public void showRareBookManuscriptReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Rare Book – manuscripts – special report";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			RareBookManuscriptSpec[] resArray = rest.postForObject(Constants.url + "getRareBookManuscript", map,
					RareBookManuscriptSpec[].class);
			List<RareBookManuscriptSpec> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Name of Book/Manuscript", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Publisher", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Author of Book", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Copies", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year of Publication", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					RareBookManuscriptSpec prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getRareBookname(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getPublisher(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAuthor(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getBookCopies(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getPublicatioYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				// document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Name of Book/Manuscript");
					rowData.add("Year of Automation");
					rowData.add("Version of ILMS");
					rowData.add("No. of Existing Users");
					rowData.add("Publication Year");
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getRareBookname());
						rowData.add("" + progList.get(i).getPublisher());
						rowData.add("" + progList.get(i).getAuthor());
						rowData.add("" + progList.get(i).getBookCopies());
						rowData.add("" + progList.get(i).getPublicatioYear());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, "", "", 'F');
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

	@RequestMapping(value = "/showLibSpecFacilitiesReport", method = RequestMethod.POST)
	public void showLibSpecFacilitiesReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Availability of Special Facilities in Library";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			LibSpecFacilities[] resArray = rest.postForObject(Constants.url + "getLibSpecFacilities", map,
					LibSpecFacilities[].class);
			List<LibSpecFacilities> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Title of Facility", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Status(Y/N)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Membership/Subscription Details", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				String a = null;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					LibSpecFacilities prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYesnoTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					if (prog.getYesnoValue() == 1) {
						a = "Yes";

					} else {
						a = "No";
					}
					cell = new PdfPCell(new Phrase("" + a, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstYesnoResponse(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					String b = null;
					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Title of Facility");
					rowData.add("Status(Y/N)");
					rowData.add("Membership/Subscription Details");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					double temp = 0.0;
					for (int i = 0; i < progList.size(); i++) {

						if (progList.get(i).getYesnoValue() == 1) {
							b = "Yes";

						} else {
							b = "No";
						}
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getYesnoTitle());
						rowData.add("" + a);
						rowData.add("" + progList.get(i).getInstYesnoResponse());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, "", "", 'D');
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

	@RequestMapping(value = "/showTeacherStudUsingLibReport", method = RequestMethod.POST)
	public void showTeacherStudUsingLibReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : No. of Students and Teachers using Library Per Day";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			TeacherStudUsingLib[] resArray = rest.postForObject(Constants.url + "getTeachersStudUsingLib", map,
					TeacherStudUsingLib[].class);
			List<TeacherStudUsingLib> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);
			// System.out.println("size" + progList.size());

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase(" Avg No. of Students Using Library Resources Per Day", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase(" Avg No. of Teachers Using Library Resources Per Day", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Teachers and Students Using Library per day", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				double n1 = 0.00;
				int index = 0;
				String a = null;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					TeacherStudUsingLib prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAvgStudent(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAvgTeacher(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					n1 = ((prog.getAvgStudent() + prog.getAvgTeacher())
							/ (prog.getNoOfFullTimeFaculty() + prog.getNoOfCurrentAdmitedStnt())) * 100;

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(n1), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("\n"));
				double n = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					document.add(new Paragraph("For Academic Year :" + progList.get(i).getAcademicYear() + ""));
					document.add(new Paragraph(
							"Total No. of Faculty in the Institute:" + progList.get(i).getNoOfFullTimeFaculty() + ""));
					document.add(new Paragraph("Total No. of Students on roll in the Institute :"
							+ progList.get(i).getNoOfCurrentAdmitedStnt() + ""));
					if (progList.get(i).getNoOfFullTimeFaculty() == 0
							&& progList.get(i).getNoOfCurrentAdmitedStnt() == 0) {
						n = 0;
					} else {

						n = ((progList.get(i).getAvgStudent() + progList.get(i).getAvgTeacher())
								/ (progList.get(i).getNoOfFullTimeFaculty()
										+ progList.get(i).getNoOfCurrentAdmitedStnt()))
								* 100;
					}
					document.add(new Paragraph("Library Usages Per Day:" + decimalFormat.format(n) + ""));
				}
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
					String b = null;
					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("Avg No. of Students Using Library Resources Per Day");
					rowData.add("Avg No. of Teachers Using Library Resources Per Day");
					rowData.add("% of Teachers and Students Using Library per day");
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;

					for (int i = 0; i < progList.size(); i++) {

						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						if (progList.get(i).getNoOfFullTimeFaculty() == 0
								&& progList.get(i).getNoOfCurrentAdmitedStnt() == 0) {
							n = 0;
						} else {

							n = ((progList.get(i).getAvgStudent() + progList.get(i).getAvgTeacher())
									/ (progList.get(i).getNoOfFullTimeFaculty()
											+ progList.get(i).getNoOfCurrentAdmitedStnt()))
									* 100;
						}

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getAvgStudent());
						rowData.add("" + progList.get(i).getAvgTeacher());
						rowData.add("" + decimalFormat.format(n));

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					for (int i = 0; i < progList.size(); i++) {

						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						if (progList.get(i).getNoOfFullTimeFaculty() == 0
								&& progList.get(i).getNoOfCurrentAdmitedStnt() == 0) {
							n = 0;
						} else {

							n = ((progList.get(i).getAvgStudent() + progList.get(i).getAvgTeacher())
									/ (progList.get(i).getNoOfFullTimeFaculty()
											+ progList.get(i).getNoOfCurrentAdmitedStnt()))
									* 100;
						}

						rowData.add("");
						rowData.add("For Academic Year: " + progList.get(i).getAcademicYear());
						rowData.add(
								"Total No. of Faculty in the Institute: " + progList.get(i).getNoOfFullTimeFaculty());
						rowData.add("Total No. of Students on roll in the Institute: "
								+ progList.get(i).getNoOfCurrentAdmitedStnt());
						rowData.add("Library Usages Per Day: " + decimalFormat.format(n));

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year :" + temp_ac_year, "", 'E');
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

	@RequestMapping(value = "/showTrainProgForTeachStaffReport", method = RequestMethod.POST)
	public void showTrainProgForTeachStaffReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Average No. of Training Programmes Organized for Teachers and Non Teaching staff (Professional Development, Administrative)";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			TrainProgForTeacherStaff[] resArray = rest.postForObject(Constants.url + "getTrainProgForTeachStaffDetail",
					map, TrainProgForTeacherStaff[].class);
			List<TrainProgForTeacherStaff> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase(
						"No. of Professional  Development Training Organized for Teaching Staff ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Administrative Trainning Organized for Non Teaching Staff ",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No of Training Programmes", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					TrainProgForTeacherStaff prog = progList.get(i);
					temp = prog.getProfDevCount() + prog.getAdmDevCount();
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getProfDevCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAdmDevCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + temp, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("No. of Professional  Development Training Organized for Teaching Staff");
					rowData.add("No. of Administrative Trainning Organized for Non Teaching Staff");
					rowData.add("Total No of Training Programmes");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						temp = progList.get(i).getProfDevCount() + progList.get(i).getAdmDevCount();
						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getProfDevCount());
						rowData.add("" + progList.get(i).getAdmDevCount());
						rowData.add("" + temp);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');
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

	@RequestMapping(value = "/showEGovernanceOptReport", method = RequestMethod.POST)
	public void showEGovernanceOptReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : E-Governance & Areas of Operation";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 1);

			EGovernenceOperation[] resArray = rest.postForObject(Constants.url + "geteGovernanceOpt", map,
					EGovernenceOperation[].class);
			List<EGovernenceOperation> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Operational Area with E-Governance", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("Implemented From", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					EGovernenceOperation prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYesnoTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstYesnoResponse(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Operational Area with E-Governance");
					rowData.add("Academic Year");
					rowData.add("Implemented From");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getYesnoTitle());
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getInstYesnoResponse());
						rowData.add("" + temp);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'D');
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

	// report 67
	@RequestMapping(value = "/showFinSuppReportForInst", method = RequestMethod.POST)
	public void showFinSuppReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Financial Support to Professional Membership/Conference/Workshop";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 1);

			FinancialSuppToProfMem[] resArray = rest.postForObject(Constants.url + "getFinancialSuppToProfMemDetail",
					map, FinancialSuppToProfMem[].class);
			List<FinancialSuppToProfMem> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Faculty Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Department", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("Title of Professional Membership / Conference /Workshop Attended",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Financial Support from Institute Amount in Rs.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					FinancialSuppToProfMem prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getDeptName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNameOfAcitvity(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAmtReceived(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Faculty Name");
					rowData.add("Department");
					rowData.add("Title of Professional Membership / Conference /Workshop Attended");
					rowData.add("Financial Support from Institute Amount in Rs.");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getFacultyFirstName() + " "
								+ progList.get(i).getFacultyLastName());
						rowData.add("" + progList.get(i).getDeptName());
						rowData.add("" + progList.get(i).getNameOfAcitvity());
						rowData.add("" + progList.get(i).getAmtReceived());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'F');
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

	// report 73
	@RequestMapping(value = "/showFinSuppReportForOther", method = RequestMethod.POST)
	public void showFinSuppReportForOther(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Funds/Grants Received from Non-Government Organisation, Individuals, Other Agencies (in Cr.)";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 2);

			FinancialSuppToProfMem[] resArray = rest.postForObject(Constants.url + "getFinancialSuppToProfMemDetail",
					map, FinancialSuppToProfMem[].class);
			List<FinancialSuppToProfMem> progList = new ArrayList<>(Arrays.asList(resArray));
			//System.out.println("Prg Ldtp-------"+progList);
			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Faculty Name", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Department", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("Title of Professional Membership / Conference /Workshop Attended",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Financial Support from Institute Amount in Rs.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					FinancialSuppToProfMem prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(),	headFontData));		//prog.getFacultyFirstName() + " " + prog.getFacultyLastName()
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getDeptName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNameOfAcitvity(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAmtReceived(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Faculty Name");
					rowData.add("Department");
					rowData.add("Title of Professional Membership / Conference /Workshop Attended");
					rowData.add("Financial Support from Institute Amount in Rs.");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getFacultyFirstName() + " "
								+ progList.get(i).getFacultyLastName());
						rowData.add("" + progList.get(i).getDeptName());
						rowData.add("" + progList.get(i).getNameOfAcitvity());
						rowData.add("" + progList.get(i).getAmtReceived());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'F');
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

	@RequestMapping(value = "/showNoFacultyFinSuppReport", method = RequestMethod.POST)
	public void showNoFacultyFinSuppReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : No. of Faculty Financial support to Professional Membership/Conference/Workshop ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			NoFacultyFinSupp[] resArray = rest.postForObject(Constants.url + "getNoFacultyFinSuppDetail", map,
					NoFacultyFinSupp[].class);
			List<NoFacultyFinSupp> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase(
						"No. of Faculty Provided Financial Support to Professional Membership/Conference/Workshop",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Faculty Provided with Financial Support", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoFacultyFinSupp prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNofFacSupport(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					String tempprcnt = decimalFormat.format(prog.getNofFacSupport() / prog.getTotalFaculty() * 100);
					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add(
							"No. of Faculty Provided Financial Support to Professional Membership/Conference/Workshop");
					rowData.add("% of Faculty Provided with Financial Support");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						String tempprcnt = decimalFormat
								.format(progList.get(i).getNofFacSupport() / progList.get(i).getTotalFaculty() * 100);
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNofFacSupport());
						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'D');
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

	@RequestMapping(value = "/showTrainProgOrgnizedForTeachReport", method = RequestMethod.POST)
	public void showTrainProgOrgnizedForTeachReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Training programmes organized for Teachers (Professional Development) ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 2);

			TrainProgOrgnizedForTeach[] resArray = rest.postForObject(Constants.url + "getTrainProgOrgnizedForTeach",
					map, TrainProgOrgnizedForTeach[].class);
			List<TrainProgOrgnizedForTeach> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title Professional Development Programme Organized for Teaching Staff",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("To Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Financial Support From", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					TrainProgOrgnizedForTeach prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTrainingTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTrainingFromdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTrainingTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFinSupportFrom(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					temp = temp + Double.parseDouble(prog.getTrainingPcount());

				}
				//System.err.println("temp bean ::" + progList.get(0).toString());
				String tempprcnt = null;
				try {
					tempprcnt = decimalFormat.format((temp / progList.get(0).getTotCount()) * 100);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph(" Avg % of Training Program attended by Teachers :" + tempprcnt + ""));

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
					rowData.add("Title Professional Development Programme Organized for Teaching Staff");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("Financial Support From");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					double temp1 = 0.00;
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getTrainingTitle());
						rowData.add("" + progList.get(i).getTrainingFromdt());
						rowData.add("" + progList.get(i).getTrainingTodt());
						rowData.add("" + progList.get(i).getFinSupportFrom());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						temp1 = temp1 + Double.parseDouble(progList.get(i).getTrainingPcount());

					}
					String tempprcnt1 = decimalFormat.format((temp1 / progList.get(0).getTotCount()) * 100);

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ",
								" Avg % of Training Program attended by Teachers:" + tempprcnt1 + " ", 'E');
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

	@RequestMapping(value = "/showTrainAdministrativeReport", method = RequestMethod.POST)
	public void showTrainAdministrativeReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Training programmes organized for Teachers (Administrative Development) ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 1);

			TrainProgOrgnizedForTeach[] resArray = rest.postForObject(Constants.url + "getTrainProgOrgnizedForTeach",
					map, TrainProgOrgnizedForTeach[].class);
			List<TrainProgOrgnizedForTeach> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title Professional Development Programme Organized for Teaching Staff",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("To Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Financial Support From", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				double temp = 0.0;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					TrainProgOrgnizedForTeach prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTrainingTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTrainingFromdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTrainingTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFinSupportFrom(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					temp = temp + Double.parseDouble(prog.getTrainingPcount());

				}
				String tempprcnt = null;
				///System.err.println("temp bean ::" + progList.get(0).toString());
				try {
				 tempprcnt = decimalFormat.format((temp / progList.get(0).getTotCount()) * 100);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph(" Avg % of Training Program attended by Teachers :" + tempprcnt + ""));

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
					rowData.add("Title Professional Development Programme Organized for Teaching Staff");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("Financial Support From");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getTrainingTitle());
						rowData.add("" + progList.get(i).getTrainingFromdt());
						rowData.add("" + progList.get(i).getTrainingTodt());
						rowData.add("" + progList.get(i).getFinSupportFrom());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						temp = temp + Double.parseDouble(progList.get(i).getTrainingPcount());

					}
					String tempprcnt1 = decimalFormat.format((temp / progList.get(0).getTotCount()) * 100);

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ",
								" Avg % of Training Program attended by Teachers:" + tempprcnt1 + " ", 'E');
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

	@RequestMapping(value = "/showVisionMissionReport", method = RequestMethod.POST)
	public void showVisionMisiionReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Institutional Vision & Mission";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			GetMissions[] resArray = rest.postForObject(Constants.url + "getInstMissionList", map, GetMissions[].class);
			List<GetMissions> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			GetVisions[] resArray1 = rest.postForObject(Constants.url + "getInstVisionList", map, GetVisions[].class);
			List<GetVisions> progList1 = new ArrayList<>(Arrays.asList(resArray1));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.4f, 4.2f, 4.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Institute Vision", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Institute Missions", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < 1; i++) {
					// System.err.println("I " + i);
					GetVisions prog = progList1.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstVisionText(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setRowspan(progList.size());
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + progList.get(0).getInstMissionText(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}

				for (int i = 1; i < progList.size(); i++) {
					// System.err.println("I " + i);
					GetMissions prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setRowspan(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstMissionText(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Misssion");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getInstMissionText());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Vision:" + progList1.get(0).getInstVisionText() + " ", "  " + " ", 'B');
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

	@RequestMapping(value = "/showQualInitiativeAssuranceReport", method = RequestMethod.POST)
	public void showQualInitiativeAssuranceReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Quality Asssurance Initiative";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			IQACQualInititive[] resArray = rest.postForObject(Constants.url + "getQualInititiveList", map,
					IQACQualInititive[].class);
			List<IQACQualInititive> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Quality Assurance Initiatives", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Status", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

			 
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					IQACQualInititive prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getQualityInitiativeName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);
 
					cell = new PdfPCell(new Phrase("" + prog.getqStatus(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Quality Assurance Initiatives");
					rowData.add("Status");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					String stst = null;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						 
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getQualityInitiativeName());
						rowData.add("" + progList.get(i).getqStatus());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, " ", "", 'C');
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

	@RequestMapping(value = "/showQualInitiativeReport", method = RequestMethod.POST)
	public void showQualInitiativeReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management: Quality Initiative by IQAC";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			QualInitiativeAssurance[] resArray = rest.postForObject(Constants.url + "getInstQualAssurance", map,
					QualInitiativeAssurance[].class);
			List<QualInitiativeAssurance> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Quality Initiative By IQAC", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("To Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Participants", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				String temp = null;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					QualInitiativeAssurance prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getQualityInitiativeName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getQualityFromdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getQualityTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getQualityPcount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

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
					rowData.add("Quality Assurance Initiatives");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("No. of Participants");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getQualityInitiativeName());
						rowData.add("" + progList.get(i).getQualityFromdt());
						rowData.add("" + progList.get(i).getQualityTodt());
						rowData.add("" + progList.get(i).getQualityPcount());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'E');
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

	@RequestMapping(value = "/showGenderEquityReport", method = RequestMethod.POST)
	public void showGenderEquityReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices: Gender Equality Programmes";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			GenderEquityProg[] resArray = rest.postForObject(Constants.url + "getGenderEquityProgDetails", map,
					GenderEquityProg[].class);
			List<GenderEquityProg> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title of Gender Equity Programme", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("To Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Participants", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				String temp = null;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					GenderEquityProg prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getGprogName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getGprogFromdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getGprogTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getpCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Title of Gender Equity Programme");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("No. of Participants");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getGprogName());
						rowData.add("" + progList.get(i).getGprogFromdt());
						rowData.add("" + progList.get(i).getGprogTodt());
						rowData.add("" + progList.get(i).getpCount());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'E');
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

	@RequestMapping(value = "/showNoOfGenderEquityReport", method = RequestMethod.POST)
	public void showNoOfGenderEquityReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices: No of Gender Equality Program";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			NoOfGenderEquityProg[] resArray = rest.postForObject(Constants.url + "getNoOfGenderEquityProg", map,
					NoOfGenderEquityProg[].class);
			List<NoOfGenderEquityProg> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Gender Equity Programme", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				String temp = null;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoOfGenderEquityProg prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfProg(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("No. of Gender Equity Programme");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNoOfProg());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'C');
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

	@RequestMapping(value = "/showGenderSensitivityFacReport", method = RequestMethod.POST)
	public void showGenderSensitivityFacReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : Gender sensitivity in Providing Facility\n" + "";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 2);

			EGovernenceOperation[] resArray = rest.postForObject(Constants.url + "geteGovernanceOpt", map,
					EGovernenceOperation[].class);
			List<EGovernenceOperation> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Facility", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Y/N", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				String temp = null;
				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					EGovernenceOperation prog = progList.get(i);
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYesnoTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);
					if (prog.getYesnoValue() == 1) {
						temp = "Yes";
					} else {
						temp = "No";
					}
					cell = new PdfPCell(new Phrase("" + temp, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Facility");
					rowData.add("Y/N");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					String temp1 = null;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						if (progList.get(i).getYesnoValue() == 1) {
							temp1 = "Yes";
						} else {
							temp1 = "No";
						}
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getYesnoTitle());
						rowData.add("" + temp1);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'D');
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

	@RequestMapping(value = "/showAlternativeEnergyIniReport", method = RequestMethod.POST)
	public void showAlternativeEnergyIniReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : Alternative Energy Initiative\n" + "";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 3);

			EGovernenceOperation[] resArray = rest.postForObject(Constants.url + "geteGovernanceOpt", map,
					EGovernenceOperation[].class);
			List<EGovernenceOperation> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase(" Power Requirement met by Renewable Energy Sources(KwH)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total Power Requirements", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("% of Power Requirement Met by Alternative Energy Sources", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				double temp_tot = 0.0;
				int index = 0;
				String val = null;

				for (int i = 0; i < progList.size(); i++) {
					

					EGovernenceOperation prog = progList.get(i);
					 System.err.println("data  " + progList.toString());
					 System.err.println("I*** " + prog.getInstYesnoResponse());

					
					if (prog.getYesnoTitle().equalsIgnoreCase("Total Power requirement")) {
						if(prog.getInstYesnoResponse()!=null || prog.getInstYesnoResponse()!=" " || prog.getInstYesnoResponse()!="NA" || prog.getInstYesnoResponse().isEmpty()==true) {
						try{
							temp_tot = Double.parseDouble(prog.getInstYesnoResponse());
						}catch (Exception e) {
							///temp_tot=0;
							e.getMessage();
						}
					}
					}

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYesnoTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstYesnoResponse(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					if (prog.getInstYesnoResponse().equals("-") || prog.getInstYesnoResponse().equals("NA") || Integer.parseInt(prog.getInstYesnoResponse())==0 || temp_tot==0.0 ) {
						val = "-";
					} else {
						val = String.valueOf((Double.parseDouble(prog.getInstYesnoResponse()) / temp_tot) * 100);
					}

					cell = new PdfPCell(new Phrase("" + val, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Power Requirement met by Renewable Energy Sources(KwH)");
					rowData.add("Annual Lightning Power Requirements");
					rowData.add("% of Power Requirement Met by Alternative Energy Sources");
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					double temp_tot1 = 0.0;
					String val1 = null;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						if (progList.get(i).getYesnoTitle().equalsIgnoreCase("Total Power requirement")) {
							temp_tot1 = Double.parseDouble(progList.get(i).getInstYesnoResponse());
						}
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getYesnoTitle());
						rowData.add("" + progList.get(i).getInstYesnoResponse());

						if (progList.get(i).getInstYesnoResponse().equals("-")) {
							val1 = "-";
						} else {
							val1 = String.valueOf(
									(Double.parseDouble(progList.get(i).getInstYesnoResponse()) / temp_tot1) * 100);
						}

						rowData.add("" + val1);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');
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

	@RequestMapping(value = "/showPowerReqThroughLEDReport", method = RequestMethod.POST)
	public void showPowerReqThroughLEDReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Governance,Leadership and Management : Power Requirement met through LED Bulbs for Lighting\n"
				+ "";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 4);

			EGovernenceOperation[] resArray = rest.postForObject(Constants.url + "geteGovernanceOpt", map,
					EGovernenceOperation[].class);
			List<EGovernenceOperation> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Annual Power Requirement met through LED Bulbs", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Annual Lightning Power Requirements", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Power Requirement Met through LED Bulbs", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				double temp_tot = 0.0;
				double temp_tot_led = 0.0;
				int index = 0;
				String val = null;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);

					EGovernenceOperation prog = progList.get(i);

					if (prog.getYesnoTitle().equalsIgnoreCase("Total Power requirement")) {
						temp_tot = Double.parseDouble(prog.getInstYesnoResponse());
					}
					if (prog.getYesnoTitle().equalsIgnoreCase("Power requirements of LED bulbs")) {
						try {
						temp_tot_led = Double.parseDouble(prog.getInstYesnoResponse());
						}catch (Exception e) {
							System.out.println("Typecasting Exception"+e.getMessage());
							// TODO: handle exception
						}
					}
				}
				index++;
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + temp_tot_led, headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + temp_tot, headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);
				val = String.valueOf(((temp_tot_led) / temp_tot) * 100);

				cell = new PdfPCell(new Phrase("" + val, headFontData));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				table.addCell(cell);

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Annual Power Requirement met through LED Bulbs");
					rowData.add("Annual Lightning Power Requirements");
					rowData.add("% of Power Requirement Met through LED Bulbs");
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					double temp_tot1 = 0.0;
					String val1 = null;
					for (int i = 0; i < progList.size(); i++) {

						if (progList.get(i).getYesnoTitle().equalsIgnoreCase("Total Power requirement")) {
							temp_tot1 = Double.parseDouble(progList.get(i).getInstYesnoResponse());
						}
						if (progList.get(i).getYesnoTitle().equalsIgnoreCase("Power requirements of LED bulbs")) {
							temp_tot_led = Double.parseDouble(progList.get(i).getInstYesnoResponse());
						}
					}
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();

					rowData.add("" + 1);
					rowData.add("" + temp_tot_led);
					rowData.add("" + temp_tot);

					val1 = String.valueOf((temp_tot_led / temp_tot1) * 100);

					rowData.add("" + val1);

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'D');
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

	@RequestMapping(value = "/showIniLocalCommunityReport", method = RequestMethod.POST)
	public void showIniLocalCommunityReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : Initiative to Engage & Contribute Local Community";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			IniToEngageLocComm[] resArray = rest.postForObject(Constants.url + "getInitiativeOfLocalCommunityProg", map,
					IniToEngageLocComm[].class);
			List<IniToEngageLocComm> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Initiatives to Engage & Contribute Local Community", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					IniToEngageLocComm prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getIniCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("No. of Initiatives to Engage & Contribute Local Community");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getIniCount());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						// wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
						// "Academic Year :" + temp_ac_year + " ");

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'C');

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

	@RequestMapping(value = "/showUniversalValProReport", method = RequestMethod.POST)
	public void showUniversalValProReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : Promotion of Universal Values";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			UniversalValPromot[] resArray = rest.postForObject(Constants.url + "getUniversalValues", map,
					UniversalValPromot[].class);
			List<UniversalValPromot> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name Of Activity", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("To Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of participants", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					UniversalValPromot prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTtleProgrmAct(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFromDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getToDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfParticipant(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name Of Activity");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("No. of Perticipants");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getTtleProgrmAct());
						rowData.add("" + progList.get(i).getFromDate());
						rowData.add("" + progList.get(i).getToDate());
						rowData.add("" + progList.get(i).getNoOfParticipant());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						// wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
						// "Academic Year :" + temp_ac_year + " ");

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'F');

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

	@RequestMapping(value = "/showNoOfUniversalReport", method = RequestMethod.POST)
	public void showNoOfUniversalReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : No. of Promotion of Universal Values";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			NoOfUniversalvalues[] resArray = rest.postForObject(Constants.url + "getNoUniversalValues", map,
					NoOfUniversalvalues[].class);
			List<NoOfUniversalvalues> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Activities Conducted for Promotion of Universal Values", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoOfUniversalvalues prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getActCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("No. of Activities Conducted for Promotion of Universal Values ");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getActCount());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						// wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
						// "Academic Year :" + temp_ac_year + " ");

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'C');

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

	@RequestMapping(value = "/showGovtSchemeBenefitReport", method = RequestMethod.POST)
	public void showGovtSchemeBenefitReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Govt Scholership Scheme";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			GovtScheme[] resArray = rest.postForObject(Constants.url + "getGovtScheme", map, GovtScheme[].class);
			List<GovtScheme> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Scheme Offered By Goverment", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Benefited", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Students Benefited", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					GovtScheme prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNameOfScheme(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfStudBenftd(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					String tempprcnt = decimalFormat
							.format(prog.getNoOfStudBenftd() / prog.getNoCurrentAdmitedStnt() * 100);

					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of Scheme Offered By Goverment");
					rowData.add("No. of Students Benefited");
					rowData.add("% of Students Benefited");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNameOfScheme());
						rowData.add("" + progList.get(i).getNoOfStudBenftd());

						String tempprcnt = decimalFormat.format(
								progList.get(i).getNoOfStudBenftd() / progList.get(i).getNoCurrentAdmitedStnt() * 100);
						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'E');
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

	@RequestMapping(value = "/showOtherThanGovtSchemeBenefitReport", method = RequestMethod.POST)
	public void showOtherThanGovtSchemeBenefitReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Institutional Financial Support besides Govt.";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			OtherThanGovtScheme[] resArray = rest.postForObject(Constants.url + "getOtherThanGovtScheme", map,
					OtherThanGovtScheme[].class);
			List<OtherThanGovtScheme> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Scheme Besides Goverment Scheme", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Scheme Supported/Offered By", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Benefited", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Students Benefited", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double fin = 0.0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					OtherThanGovtScheme prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstSchemeName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstSchmeOfferedby(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstStudentsBenefited(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					String tempprcnt = decimalFormat
							.format(prog.getInstStudentsBenefited() / prog.getNoCurrentAdmitedStnt() * 100);

					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					fin = fin + Double.parseDouble(tempprcnt);

				}
				double x1 = fin / 5;
				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);
				// document.add(new Paragraph("Institute " +
				// progList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Avarage Percentage:" + x1 + ""));

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
					rowData.add("Academic Year");
					rowData.add("Name of Scheme Besides Goverment Scheme");
					rowData.add("Scheme Supported/Offered By");
					rowData.add("No. of Students Benefited");
					rowData.add("% of Students Benefited");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					double fin1 = 0.0;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getInstSchemeName());
						rowData.add("" + progList.get(i).getInstSchmeOfferedby());
						rowData.add("" + progList.get(i).getInstStudentsBenefited());

						String tempprcnt = decimalFormat.format(progList.get(i).getInstStudentsBenefited()
								/ progList.get(i).getNoCurrentAdmitedStnt() * 100);
						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						fin1 = fin1 + Double.parseDouble(tempprcnt);
					}
					double x2 = fin1 / 5;
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "Avarage Percentage:" + x2 + " ", 'F');
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

	@RequestMapping(value = "/showStudSupportSchemeReport", method = RequestMethod.POST)
	public void showStudSupportSchemeReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Institutional Financial Support besides Govt";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			OtherThanGovtScheme[] resArray = rest.postForObject(Constants.url + "getOtherThanGovtScheme", map,
					OtherThanGovtScheme[].class);
			List<OtherThanGovtScheme> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Scheme Besides Goverment Scheme", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Scheme Supported/Offered By", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Benefited", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Students Benefited", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double fin = 0.0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					OtherThanGovtScheme prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstSchemeName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstSchmeOfferedby(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getInstStudentsBenefited(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					String tempprcnt = decimalFormat
							.format(prog.getInstStudentsBenefited() / prog.getNoCurrentAdmitedStnt() * 100);

					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					fin = fin + Double.parseDouble(tempprcnt);

				}

				Double x1 = fin / 5;
				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Avarage Percentage:" + x1 + ""));

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
					rowData.add("Academic Year");
					rowData.add("Name of Scheme Besides Goverment Scheme");
					rowData.add("Scheme Supported/Offered By");
					rowData.add("No. of Students Benefited");
					rowData.add("% of Students Benefited");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					double fin1 = 0.0;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getInstSchemeName());
						rowData.add("" + progList.get(i).getInstSchmeOfferedby());
						rowData.add("" + progList.get(i).getInstStudentsBenefited());

						String tempprcnt = decimalFormat.format(progList.get(i).getInstStudentsBenefited()
								/ progList.get(i).getNoCurrentAdmitedStnt() * 100);
						rowData.add("" + tempprcnt);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						fin1 = fin1 + Double.parseDouble(tempprcnt);
					}

					double x2 = fin1 / 5;
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "Avarage Percentage:" + x2 + " ", 'F');
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

	@RequestMapping(value = "/showDistinguishedAluminiReport", method = RequestMethod.POST)
	public void showDistinguishedAluminiReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : List of Distinguished Alumni";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);

			DistinguishedAlumini[] resArray = rest.postForObject(Constants.url + "getDistinctAlumini", map,
					DistinguishedAlumini[].class);
			List<DistinguishedAlumini> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Passing Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Current Position/Designation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Contribution to Institute", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					DistinguishedAlumini prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getPassingYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getCurrDesn(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getExInt1(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Year of Passing");
					rowData.add("Current Position/Designation");
					rowData.add("Contribution to the Institute");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getPassingYear());
						rowData.add("" + progList.get(i).getCurrDesn());
						rowData.add("" + progList.get(i).getExInt1());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'E');

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

	@RequestMapping(value = "/showAluminiEngagementReport", method = RequestMethod.POST)
	public void showAluminiEngagementReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Alumni Engagement ";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			AluminiEngagement[] resArray = rest.postForObject(Constants.url + "getAluminiEngg", map,
					AluminiEngagement[].class);
			List<AluminiEngagement> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			map = new LinkedMultiValueMap<>();
			map.add("instId", instituteId);
			GetAluminiEngagementReport editFaculty = rest.postForObject(Constants.url + "/getAluminiEngagementDetails",
					map, GetAluminiEngagementReport.class);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total Contribution of Alumini in Rs.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double x1 = 0.0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					AluminiEngagement prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getContri(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					x1 = x1 + prog.getContri();

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph(" Name of Alumini Association : " + editFaculty.getNameAlumniAssoc()));
				document.add(new Paragraph(" Year  of Estabilishment : " + editFaculty.getEstYear()));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Alumini Contribution During Last Years : " + decimalFormat.format(x1)));

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
					rowData.add("Academic Year");
					rowData.add("Total Contribution of Alumini in Rs.");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getContri());
						x1 = x1 + progList.get(i).getContri();
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String leaveSum = "Name Of Association: " + editFaculty.getNameAlumniAssoc() + "";
						String leaveSum1 = ",Est. Year: " + editFaculty.getEstYear() + "";
						String leaveSum2 = ",Academic Year:" + temp_ac_year + " ";

						String reportSummary = leaveSum + "" + leaveSum1 + "" + leaveSum2;

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, reportSummary,
								"Alumini Contribution During Last Five Years:" + x1 + "", 'C');

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

	@RequestMapping(value = "/showOrganizationSportsCulturalReport", method = RequestMethod.POST)
	public void showOrganizationSportsCulturalReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Organization of Sports & Cultural Activities - Competitions(Organized)";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 0);

			SportsCulturalActivityComp[] resArray = rest.postForObject(Constants.url + "getSportsActivityComp", map,
					SportsCulturalActivityComp[].class);
			List<SportsCulturalActivityComp> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Activity/Competition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Type of Activity/Competition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					SportsCulturalActivityComp prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getActivityName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getLevel(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of Activity/Competition");
					rowData.add("Date");
					rowData.add("Type of Activity/Competition");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getActivityName());
						rowData.add("" + progList.get(i).getDate());
						rowData.add("" + progList.get(i).getLevel());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');
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

	@RequestMapping(value = "/showAttendedSportsCulturalReport", method = RequestMethod.POST)
	public void showAttandedSportsCulturalReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Students Outstanding Performance (Sport and Cultural) ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 1);

			SportsCulturalActivityComp[] resArray = rest.postForObject(Constants.url + "getSportsActivityComp", map,
					SportsCulturalActivityComp[].class);
			List<SportsCulturalActivityComp> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Activity/Competition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Type of Activity/Competition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					SportsCulturalActivityComp prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getActivityName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getLevel(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of Activity/Competition");
					rowData.add("Date");
					rowData.add("Type of Activity/Competition");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getActivityName());
						rowData.add("" + progList.get(i).getDate());
						rowData.add("" + progList.get(i).getLevel());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');
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

	@RequestMapping(value = "/showCapabilityEnhancementReport", method = RequestMethod.POST)
	public void showCapabilityEnhancementReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression:Capability Enhancement & Development Schemes ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("typeId", 1);

			CapabilityEnhancementDev[] resArray = rest.postForObject(Constants.url + "getCapabilityEnhancementDev", map,
					CapabilityEnhancementDev[].class);
			List<CapabilityEnhancementDev> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Scheme", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year of Implementation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Enrolled", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Support Agency", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					CapabilityEnhancementDev prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSchemeName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoStudentBenifited(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSupportAgencyName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Name of Scheme");
					rowData.add("Year of Implementation");
					rowData.add("No. of Students Enrolled");
					rowData.add("Name of Support Agency");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getSchemeName());
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNoStudentBenifited());
						rowData.add("" + progList.get(i).getSupportAgencyName());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');
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

	@RequestMapping(value = "/showVETReport", method = RequestMethod.POST)
	public void showVETReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Students Benifited from VET ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", 0);
			map.add("typeId", 2);

			CapabilityEnhancementDev[] resArray = rest.postForObject(Constants.url + "getCapabilityEnhancementDev", map,
					CapabilityEnhancementDev[].class);
			List<CapabilityEnhancementDev> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year of Implementation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Enrolled", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Support Agency", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					CapabilityEnhancementDev prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoStudentBenifited(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getSupportAgencyName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Scheme Name :Vocational Education Training"));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Year of Implementation");
					rowData.add("No. of Students Enrolled");
					rowData.add("Name of Support Agency");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNoStudentBenifited());
						rowData.add("" + progList.get(i).getSupportAgencyName());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Scheme Name :Vocational Education Training", " ", 'D');
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

	@RequestMapping(value = "/showStudQualifyingExamReport", method = RequestMethod.POST)
	public void showStudQualifyingExamReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Students qualifying State/National/Internationa Exams per year ";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			StudQualifyingExamReport[] resArray = rest.postForObject(Constants.url + "getStudQualifyingExam", map,
					StudQualifyingExamReport[].class);
			List<StudQualifyingExamReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Exam", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Appeared", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Student Qualified", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Per Year Avg %", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double result = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					StudQualifyingExamReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNameQualifExam(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoStudAppeared(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoStudQualified(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getPrcnt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					result = result + prog.getPrcnt();
				}
				double x1 = result / 5;
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Avg%:" + decimalFormat.format(x1) + ""));
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
					rowData.add("Academic Year");
					rowData.add("Year of Implementation");
					rowData.add("No. of Students Enrolled");
					rowData.add("Name of Support Agency");
					rowData.add("Per Year Avg %");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					double result1 = 0.0;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNameQualifExam());
						rowData.add("" + progList.get(i).getNoStudAppeared());
						rowData.add("" + progList.get(i).getNoStudQualified());
						rowData.add("" + progList.get(i).getPrcnt());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						result = result + progList.get(i).getPrcnt();
					}
					double x2 = result1 / 5;
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "Avg%" + x2 + " ", 'F');
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

	@RequestMapping(value = "/showAluminiAssoMeetingReport", method = RequestMethod.POST)
	public void showAluminiAssoMeetingReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Alumni Association Meeting Details";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			AluminiAssoMeetReport[] resArray = rest.postForObject(Constants.url + "getAluminiAssoMeetDetails", map,
					AluminiAssoMeetReport[].class);
			List<AluminiAssoMeetReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Alumini Association", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Date of Meeting of Alumini Association", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double result = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					AluminiAssoMeetReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNameAlumniAssoc(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getDateOfMeeting(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

				}
				double x1 = result / 5;
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				// document.add(new Paragraph("Avg%:" + x1 + ""));
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
					rowData.add("Academic Year");
					rowData.add("Name of Alumini Association");
					rowData.add("Date of Meeting of Alumini Association");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					double result1 = 0.0;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNameAlumniAssoc());
						rowData.add("" + progList.get(i).getDateOfMeeting());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
					}
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "  ", 'D');
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

	@RequestMapping(value = "/showHumanValProfEthicsReport", method = RequestMethod.POST)
	public void showHumanValProfEthicsReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : Human Values & Professional Ethics";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYearList", ac_year);
			map.add("typeId", 5);

			EGovernenceOperation[] resArray = rest.postForObject(Constants.url + "geteGovernanceOpt", map,
					EGovernenceOperation[].class);
			List<EGovernenceOperation> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title of Human values and Professional Ethics", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Status(Y/N)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				String a = null;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					EGovernenceOperation prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getYesnoTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					if (prog.getYesnoValue() == 1) {
						a = "Yes";

					} else {
						a = "No";
					}
					cell = new PdfPCell(new Phrase("" + a, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					String b = null;
					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("Title of Human values and Professional Ethics");
					rowData.add("Status(Y/N)");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					double temp = 0.0;
					for (int i = 0; i < progList.size(); i++) {

						if (progList.get(i).getYesnoValue() == 1) {
							b = "Yes";

						} else {
							b = "No";
						}
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getYesnoTitle());
						rowData.add("" + a);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'D');
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

	@RequestMapping(value = "/showExtensionActivityReport", method = RequestMethod.POST)
	public void showExtensionActivityReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : No. of Student/Teachers Participation in Extension Activity";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			ExtensionActivityReport[] resArray = rest.postForObject(Constants.url + "getExtesionActivityDetails", map,
					ExtensionActivityReport[].class);
			List<ExtensionActivityReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Extension Activity", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No of Students Participating", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No of Teachers Participating", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double totStud = 0.0;
				double totTeach = 0.0;
				double result = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					ExtensionActivityReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.gettActivityTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfStudParticipated(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfFacultyParticipated(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					totStud = totStud + prog.getNoOfStudParticipated();
					totTeach = totTeach + prog.getNoOfStudInInst();
					result = result + prog.getResult();
				}
				double x1 = result / 5;
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(
						new Paragraph("Total No of Students Participating in Extension Activity in Last Five Years :"
								+ totStud + ""));
				document.add(new Paragraph("Total No of Students in Institute  in Last Five Years :" + totTeach + ""));
				document.add(new Paragraph("% of Students Participating in Extra-Curricular Activities :" + x1 + ""));

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
					rowData.add("Name of Extension Activity");
					rowData.add("Academic Year");
					rowData.add("No of Students Participating");
					rowData.add("No of Teachers Participating");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					double totStud1 = 0.0;
					double totTeach1 = 0.0;
					double result1 = 0.0;
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).gettActivityTitle());
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNoOfStudParticipated());
						rowData.add("" + progList.get(i).getNoOfFacultyParticipated());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						totStud1 = totStud + progList.get(i).getNoOfStudParticipated();
						totTeach1 = totTeach + progList.get(i).getNoOfStudInInst();
						result1 = result + progList.get(i).getResult();
					}
					XSSFWorkbook wb = null;
					double x2 = result1 / 5;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}

						String leaveSum = "Total No of Students Participating in Extension Activity in Last Five Years : "
								+ totStud1 + "";
						String leaveSum1 = "Total No of Students in Institute  in Last Five Years : " + totTeach1 + "";
						String leaveSum2 = "% of Students Participating in Extra-Curricular Activities :" + x2 + "";

						String reportSummary = leaveSum + "" + leaveSum1 + "" + leaveSum2;

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", reportSummary, 'E');
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

	@RequestMapping(value = "/showNoOfStudTeachLinkageReport", method = RequestMethod.POST)
	public void showNoOfStudTeachLinkageReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : No of Linkages";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			int catId = Integer.parseInt(request.getParameter("catId"));
			String temp_ac_year = request.getParameter("temp_ac_year");
			String temp_cat = request.getParameter("temp_cat");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			NoOfStudTeachLinkageReport[] resArray = rest.postForObject(Constants.url + "getNoOfStudTeachLinkageDetails",
					map, NoOfStudTeachLinkageReport[].class);
			List<NoOfStudTeachLinkageReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Linkages/Collaboration", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoOfStudTeachLinkageReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getLinkageCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("  Academic Year :" + temp_ac_year + ""));
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("No. of Linkages/Collaboration");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getLinkageCount());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'C');
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

	@RequestMapping(value = "/showAwardRecognizationReport", method = RequestMethod.POST)
	public void showAwardRecognizationReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : No of Recognition/Awards";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();
			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			AwardRecogDetailReport[] resArray = rest.postForObject(Constants.url + "getAwardRecogDetails", map,
					AwardRecogDetailReport[].class);
			List<AwardRecogDetailReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Award/Rec/Fellowships at state/national/International", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double awad = 0.0;
				double fulllTime = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					AwardRecogDetailReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAwardCount(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					awad = awad + prog.getAwardCount();
					fulllTime = fulllTime + prog.getNoOfFulltimeFaculty();
				}
				double x1 = awad / fulllTime;
				String a2 = decimalFormat.format(x1);
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(
						new Paragraph("% of Students of Full-time Teachers with Award/Rec/Fellowships:" + a2 + ""));

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
					rowData.add("Academic Year");
					rowData.add("No of Students Participating");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					double fulllTime1 = 0.0;
					double awad1 = 0.0;

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getAwardCount());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

						awad1 = awad + progList.get(i).getAwardCount();
						fulllTime1 = fulllTime + progList.get(i).getNoOfFulltimeFaculty();
					}
					XSSFWorkbook wb = null;

					double x2 = awad1 / fulllTime1;
					String a1 = decimalFormat.format(x2);
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}

						String leaveSum2 = "% of Students of Full-time Teachers with Award/Rec/Fellowships :" + a1 + "";

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", leaveSum2, 'C');
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

	@RequestMapping(value = "/showBookPublicationReport", method = RequestMethod.POST)
	public void showBookPublicationReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : Book and Chapter Publication by Teachers and Papers in Conference Proceedings";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			BookPublicationDetReport[] resArray = rest.postForObject(Constants.url + "getBookPublicationDetails", map,
					BookPublicationDetReport[].class);
			List<BookPublicationDetReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Full Time Teachers", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title of Book & Paper in Conference Proceedings", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase(" Publisher/Name of Conference Proceedings", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISBN/ISSN   ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year of Publication", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					BookPublicationDetReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(),	headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getBookTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getBookPublisher(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getBookIsbn(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getBookPubYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Name of Full Time Teachers");
					rowData.add("Title of Book & Paoers in Conference Proceedings");
					rowData.add("Publisher/Name of Conference Proceedings");
					rowData.add("ISBN/ISSN");
					rowData.add("Year of Publication");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getFacultyFirstName() + " "
								+ progList.get(i).getFacultyLastName());
						rowData.add("" + progList.get(i).getBookTitle());
						rowData.add("" + progList.get(i).getBookPublisher());
						rowData.add("" + progList.get(i).getBookIsbn());
						rowData.add("" + progList.get(i).getBookPubYear());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'F');
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

	@RequestMapping(value = "/showNoOfBookPublicationReport", method = RequestMethod.POST)
	public void showNoOfBookPublicationReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : No Book and Chapter Publication by Teachers and Papers in Conference Proceedings";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();
			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			NoOfBookReport[] resArray = rest.postForObject(Constants.url + "getNoOfBookPaperDetails", map,
					NoOfBookReport[].class);
			List<NoOfBookReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year Of Publication", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Book Published+ No. of Papers in Conference Proceedings", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double awad = 0.0;
				double fulllTime = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					NoOfBookReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					double y1 = prog.getBookCount() + prog.getPaperCount();

					cell = new PdfPCell(new Phrase("" + y1, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					awad = awad + prog.getBookCount() + prog.getPaperCount();
					fulllTime = fulllTime + prog.getNoOfFulltimeFaculty();
				}
				double x1 = awad / fulllTime;
				String a2 = decimalFormat.format(x1);
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(
						new Paragraph("% of Students of Full-time Teachers with Award/Rec/Fellowships:" + a2 + ""));

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
					rowData.add("Academic Year");
					rowData.add("No. of Book Published+ No. of Papers in Conference Proceedings");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					double fulllTime1 = 0.0;
					double awad1 = 0.0;

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						double y1 = progList.get(i).getBookCount() + progList.get(i).getPaperCount();

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + y1);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

						awad1 = awad + progList.get(i).getBookCount() + progList.get(i).getPaperCount();
						fulllTime1 = fulllTime + progList.get(i).getNoOfFulltimeFaculty();
					}
					XSSFWorkbook wb = null;

					double x2 = awad1 / fulllTime1;
					String a1 = decimalFormat.format(x2);
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}

						String leaveSum2 = "No of Books and Chapters Per Teacher    :" + a1 + "";

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", leaveSum2, 'C');
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

	@RequestMapping(value = "/showPhdGuideDetailsReport", method = RequestMethod.POST)
	public void showPhdGuideDetailsReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : Ph.D Awarded Information";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();
			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			PhdGuideReport[] resArray = rest.postForObject(Constants.url + "getPddGuideDetails", map,
					PhdGuideReport[].class);
			List<PhdGuideReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(8);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Ph.D Scholar", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Guide", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Co-Guide", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year Of Registration", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year Of Ph.D. Award", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("University", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double awad = 0.0;
				double fulllTime = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					PhdGuideReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getPhdScholarName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getGuideName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getCoGuideName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getRegYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAwdYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getUniversity(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of Ph.D Scholar");
					rowData.add("Name of Guide");
					rowData.add("Name of Co-Guide");
					rowData.add("Year Of Registration");
					rowData.add("Year Of Ph.D. Award");
					rowData.add("University");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					double fulllTime1 = 0.0;
					double awad1 = 0.0;

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getPhdScholarName());
						rowData.add("" + progList.get(i).getGuideName());
						rowData.add("" + progList.get(i).getCoGuideName());
						rowData.add("" + progList.get(i).getRegYear());
						rowData.add("" + progList.get(i).getAwdYear());
						rowData.add("" + progList.get(i).getUniversity());
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					XSSFWorkbook wb = null;

					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'H');
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

	@RequestMapping(value = "/showPlagarismCodeEthicsDetailsReport", method = RequestMethod.POST)
	public void showPlagarismCodeEthicsDetailsReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research,Innovation and Extension : Plagarism and Code of Ethics";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();
			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			PlagarismCodeEthicsReport[] resArray = rest.postForObject(Constants.url + "getPlagarismCodeEthicsDetails",
					map, PlagarismCodeEthicsReport[].class);
			List<PlagarismCodeEthicsReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Provide upload the URL Having code of Ethics", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("Whether colleges have been provided access to Plagarism detecting software(Y/N)",
								tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Mechanism for detecting plagarism", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double awad = 0.0;
				double fulllTime = 0.0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					PlagarismCodeEthicsReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getUrlLink(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					int n = prog.getIsSoftwrAvabl();
					String sw = null;
					if (n == 1) {
						sw = "Yes";
					} else {
						sw = "No";
					}

					cell = new PdfPCell(new Phrase("" + sw, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getMechDetectPlag(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					hcell = new PdfPCell(new Phrase("Provide upload the URL Having code of Ethics", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase(
							"Whether colleges have been provided access to Plagarism detecting software(Y/N)",
							tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("Mechanism for detecting plagarism", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);
					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("Provide upload the URL Having code of Ethics");
					rowData.add("Whether colleges have been provided access to Plagarism detecting software(Y/N)");
					rowData.add("Mechanism for detecting plagarism");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					double fulllTime1 = 0.0;
					double awad1 = 0.0;

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getUrlLink());
						int n = progList.get(i).getIsSoftwrAvabl();
						String sw = null;
						if (n == 1) {
							sw = "Yes";
						} else {
							sw = "No";
						}
						rowData.add("" + sw);
						rowData.add("" + progList.get(i).getMechDetectPlag());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					XSSFWorkbook wb = null;

					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", "", 'E');
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

	@RequestMapping(value = "/showStudEnrooledForProgramReport", method = RequestMethod.POST)
	public void showStudEnrooledForProgramReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curriculam Aspects : Students Enrolled in Certi., Diploma or Add-On Programs";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");
			
			int programId = 0;
			int programType = 0;
			
			try {
				 programId = Integer.parseInt(request.getParameter("prog_name"));
				 programType = Integer.parseInt(request.getParameter("prog_type"));
			}catch (Exception e) {
				e.getMessage();
			}
			

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			
			HttpSession session = request.getSession();
			String temp_prog_name = request.getParameter("temp_prog_name");
			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);
			map.add("programId", programType);
			StudEnrooledForProgramReport[] resArray = rest.postForObject(Constants.url + "getStudEnrooledForProgram",
					map, StudEnrooledForProgramReport[].class);
			List<StudEnrooledForProgramReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showAvgStudYearwiseReport");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Enrolled in Certificate/Program", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No. of Students in All Programs", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("%(Year)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				double finalPrcnt = 0.0;
				int index = 0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					StudEnrooledForProgramReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getProgStudEnrolled(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalStud(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					double avg = 0.0;
					if (prog.getTotalStud() == 0) {
						avg = 0;
					} else {
						avg = prog.getProgStudEnrolled() / prog.getTotalStud();
					}

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(avg), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					finalPrcnt = finalPrcnt + avg;
				}
				String x1 = decimalFormat.format(finalPrcnt / 5);

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("Academic Year:" + temp_ac_year + ""));
				document.add(new Paragraph("Program Name :" + progList.get(0).getProgramName() + ""));
				document.add(new Paragraph("Program Type :" + temp_prog_name + ""));
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("Average% :" + x1 + ""));

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

					hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(
							new Phrase("No. of Students Enrolled in Certificate/Program", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("Total No. of Students in All Programs", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("%(Year)", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);
					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("No. of Students Enrolled in Certificate/Program");
					rowData.add("Total No. of Students in All Programs");
					rowData.add("%(Year)");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					double rslt = 0.0;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;
						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getProgStudEnrolled());
						rowData.add("" + progList.get(i).getTotalStud());

						double avg = 0.0;
						if (progList.get(i).getTotalStud() == 0) {
							avg = 0;
						} else {
							avg = progList.get(i).getProgStudEnrolled() / progList.get(i).getTotalStud();
						}
						rowData.add("" + avg);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						rslt = rslt + avg;
					}
					String x2 = decimalFormat.format(rslt / 5);
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						String leaveSum = "Program: " + progList.get(0).getProgramName() + "";
						String leaveSum1 = ",Program Type: " + temp_prog_name + "";
						String leaveSum2 = ",Academic Year: " + temp_ac_year + "";

						String reportSummary = leaveSum + "" + leaveSum1 + "" + leaveSum2;

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, reportSummary,
								"Average%:" + x2 + "", 'E');
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

	@RequestMapping(value = "/showValueAddedCourseReport", method = RequestMethod.POST)
	public void showValueAddedCourseReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : Value Added Courses";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			ValueAddedCoursesReport[] resArray = rest.postForObject(Constants.url + "getValueAddedCoursesDetails", map,
					ValueAddedCoursesReport[].class);
			List<ValueAddedCoursesReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(6);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Course Code", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Course Name ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Students Enrolled for Course ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase(" No. of Students Completed The Course", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					ValueAddedCoursesReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getCourseCode(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getValueAddedCourseName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfStudentsEnrolled(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfStudentsCompletedCourse(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Course Code");
					rowData.add("Course Name");
					rowData.add("No. of Students Enrolled for Course");
					rowData.add("No. of Students Completed The Course");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getCourseCode());
						rowData.add("" + progList.get(i).getValueAddedCourseName());
						rowData.add("" + progList.get(i).getNoOfStudentsEnrolled());
						rowData.add("" + progList.get(i).getNoOfStudentsCompletedCourse());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'F');
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

	@RequestMapping(value = "/showStakeHolderFBDetailsReport", method = RequestMethod.POST)
	public void showStakeHolderFBDetailsReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : Feedback Processed";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			StakeHolderFBReport[] resArray = rest.postForObject(Constants.url + "getStakeHolderFBDetails", map,
					StakeHolderFBReport[].class);
			List<StakeHolderFBReport> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Feedback Received From", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Status ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Action Taken Status", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					StakeHolderFBReport prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFeedbackFrom(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);
					String n = null;

					if (prog.getFbYesno() == 1) {
						n = "Yes";
					} else {
						n = "No";
					}
					cell = new PdfPCell(new Phrase("" + n, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					String n1 = null;
					if (prog.getFbProcess().equalsIgnoreCase("A")) {
						n1 = "Feedback Collected, analyzed  and action taken and feedback available on Websites";

					} else if (prog.getFbProcess().equalsIgnoreCase("B")) {
						n1 = "Collected, analyzed and action has been taken";

					} else if (prog.getFbProcess().equalsIgnoreCase("C")) {
						n1 = "Feedback Collected and analyzed";

					} else if (prog.getFbProcess().equalsIgnoreCase("D")) {
						n1 = "Feedback Collected";

					} else {
						n1 = "-";
					}

					cell = new PdfPCell(new Phrase("" + n1, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Feedback Received From");
					rowData.add("Status");
					rowData.add("Action Taken Status");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getFeedbackFrom());
						String n = null;
						if (progList.get(i).getFbYesno() == 1) {
							n = "Yes";
						} else {
							n = "No";
						}
						rowData.add("" + n);
						String n1 = null;
						if (progList.get(i).getFbProcess().equalsIgnoreCase("A")) {
							n1 = "Feedback Collected, analyzed  and action taken and feedback available on Websites";

						} else if (progList.get(i).getFbProcess().equalsIgnoreCase("B")) {
							n1 = "Collected, analyzed and action has been taken";

						} else if (progList.get(i).getFbProcess().equalsIgnoreCase("C")) {
							n1 = "Feedback Collected and analyzed";

						} else if (progList.get(i).getFbProcess().equalsIgnoreCase("D")) {
							n1 = "Feedback Collected";

						} else {
							n1 = "-";
						}
						rowData.add("" + n1);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');
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

	@RequestMapping(value = "/showNoResearchProjPerReport", method = RequestMethod.POST)
	public void showNoResearchProjPerReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : No of Research Project Per Teacher";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			ResearchProjNoPerTeacher[] resArray = rest.postForObject(Constants.url + "getResearchProjectnoDetails", map,
					ResearchProjNoPerTeacher[].class);
			List<ResearchProjNoPerTeacher> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			Document document = new Document(PageSize.A4);
			// 50, 45, 50, 60
			document.setMargins(Constants.marginLeft, Constants.marginRight, Constants.marginTop,
					Constants.marginBottom);
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
			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(4);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f });

				Font headFontData = Constants.headFontData;// new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
															// BaseColor.BLACK);
				Font tableHeaderFont = Constants.tableHeaderFont; // new Font(FontFamily.HELVETICA, 12, Font.BOLD,
																	// BaseColor.BLACK);
				tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);

				PdfPCell hcell = new PdfPCell();
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Projects  ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					ResearchProjNoPerTeacher prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicyear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getNoOfProj(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year :" + temp_ac_year + ""));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of Faculty");
					rowData.add("No. of Projects");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicyear());
						rowData.add("" + progList.get(i).getFacultyFirstName());
						rowData.add("" + progList.get(i).getNoOfProj());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = progList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'D');
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

	@RequestMapping(value = "/showStudGrivienceReport", method = RequestMethod.POST)
	public void showStudGrivienceReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Alumni Engagement ";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			ReaddressalOfStudGrievennce[] resArray = rest.postForObject(
					Constants.url + "getStudReaddressalGrievienceDetails", map, ReaddressalOfStudGrievennce[].class);
			List<ReaddressalOfStudGrievennce> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			BufferedOutputStream outStream = null;
			// System.out.println("Inside Pdf showCustomerwisePdf");
			Document document = new Document(PageSize.A4);
			document.setMargins(50, 45, 50, 60);
			document.setMarginMirroring(false);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			String headingName = null;
			try {
				headingName = progList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of grievences", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of applied grivences", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of redressed grievences", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				double x1 = 0.0;
				for (int i = 0; i < progList.size(); i++) {
					// System.err.println("I " + i);
					ReaddressalOfStudGrievennce prog = progList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getStudGrievnce(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalAppealed(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prog.getTotalRedresed(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));

				document.add(new Paragraph("\n"));
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
					rowData.add("Academic Year");
					rowData.add("Name of grievences");
					rowData.add("No of applied grivences");
					rowData.add("No of redressed grievences");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < progList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getStudGrievnce());
						rowData.add("" + progList.get(i).getTotalAppealed());
						rowData.add("" + progList.get(i).getTotalRedresed());
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'E');

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
	
	

}
