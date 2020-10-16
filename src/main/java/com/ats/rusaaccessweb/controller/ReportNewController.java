package com.ats.rusaaccessweb.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.ats.rusaaccessweb.model.Program;
import com.ats.rusaaccessweb.model.ProgramType;
import com.ats.rusaaccessweb.model.dashb.AcademicYear;
import com.ats.rusaaccessweb.reportnew.model.AdmissionsAgainstCategory;
import com.ats.rusaaccessweb.reportnew.model.AluminiAssoMeetReport;
import com.ats.rusaaccessweb.reportnew.model.AluminiEngagement;
import com.ats.rusaaccessweb.reportnew.model.AvgEnrollmentPrcnt;
import com.ats.rusaaccessweb.reportnew.model.AvgPerPlacement;
import com.ats.rusaaccessweb.reportnew.model.AwardRecogDetailReport;
import com.ats.rusaaccessweb.reportnew.model.AwrdRecgAgnstExtActivityReport;
import com.ats.rusaaccessweb.reportnew.model.BookPublicationDetReport;
import com.ats.rusaaccessweb.reportnew.model.BudgetInfraAugmntn;
import com.ats.rusaaccessweb.reportnew.model.CapabilityEnhancementDev;
import com.ats.rusaaccessweb.reportnew.model.Cast;
import com.ats.rusaaccessweb.reportnew.model.DifferentlyAbldStudReport;
import com.ats.rusaaccessweb.reportnew.model.DistinguishedAlumini;
import com.ats.rusaaccessweb.reportnew.model.EContntDevFacReport;
import com.ats.rusaaccessweb.reportnew.model.EGovernenceOperation;
import com.ats.rusaaccessweb.reportnew.model.ExpenditureOnPrchaseBooksJournal;
import com.ats.rusaaccessweb.reportnew.model.ExpndGreenInitveWsteMgmt;
import com.ats.rusaaccessweb.reportnew.model.ExpndturOnPhysclAcademicSupprt;
import com.ats.rusaaccessweb.reportnew.model.ExtensionActivityReport;
import com.ats.rusaaccessweb.reportnew.model.FacAgnstSanctnPost;
import com.ats.rusaaccessweb.reportnew.model.FacAgnstSanctnPostOthrState;
import com.ats.rusaaccessweb.reportnew.model.FacParticipationInBodies;
import com.ats.rusaaccessweb.reportnew.model.FildeProjectInternReport;
import com.ats.rusaaccessweb.reportnew.model.FinancialSuppToProfMem;
import com.ats.rusaaccessweb.reportnew.model.FulTimFacultyWithPhd;
import com.ats.rusaaccessweb.reportnew.model.FullTimeTechrInstResrchGuide;
import com.ats.rusaaccessweb.reportnew.model.FunctionalMou;
import com.ats.rusaaccessweb.reportnew.model.GenderEquityProg;
import com.ats.rusaaccessweb.reportnew.model.GetAluminiEngagementReport;
import com.ats.rusaaccessweb.reportnew.model.GetAvgStudYearwise;
import com.ats.rusaaccessweb.reportnew.model.GetMissions;
import com.ats.rusaaccessweb.reportnew.model.GetTeachersUsingICT;
import com.ats.rusaaccessweb.reportnew.model.GetVisions;
import com.ats.rusaaccessweb.reportnew.model.GovtScheme;
import com.ats.rusaaccessweb.reportnew.model.ICtEnbldFaclitiesReport;
import com.ats.rusaaccessweb.reportnew.model.IQACQualInititive;
import com.ats.rusaaccessweb.reportnew.model.IniToEngageLocComm;
import com.ats.rusaaccessweb.reportnew.model.InitivAddrsLoctnAdvDisadv;
import com.ats.rusaaccessweb.reportnew.model.InstStakeholderFeedbackReport;
import com.ats.rusaaccessweb.reportnew.model.IntelectulPropRightReport;
import com.ats.rusaaccessweb.reportnew.model.IntrnetConnInfo;
import com.ats.rusaaccessweb.reportnew.model.LibAutoLMSInfo;
import com.ats.rusaaccessweb.reportnew.model.LibSpecFacilities;
import com.ats.rusaaccessweb.reportnew.model.NoAwardRecogExtAct;
import com.ats.rusaaccessweb.reportnew.model.NoFacultyFinSupp;
import com.ats.rusaaccessweb.reportnew.model.NoInitivAddrsLoctnAdvDisadv;
import com.ats.rusaaccessweb.reportnew.model.NoOfBookReport;
import com.ats.rusaaccessweb.reportnew.model.NoOfGenderEquityProg;
import com.ats.rusaaccessweb.reportnew.model.NoOfLinkages;
import com.ats.rusaaccessweb.reportnew.model.NoOfMentorsAssignedStudent;
import com.ats.rusaaccessweb.reportnew.model.NoOfPrograms;
import com.ats.rusaaccessweb.reportnew.model.NoOfStudTeachLinkageReport;
import com.ats.rusaaccessweb.reportnew.model.NoOfUniversalvalues;
import com.ats.rusaaccessweb.reportnew.model.OtherThanGovtScheme;
import com.ats.rusaaccessweb.reportnew.model.PerNewCource;
import com.ats.rusaaccessweb.reportnew.model.PerProgCbseElectiveCourse;
import com.ats.rusaaccessweb.reportnew.model.PhdGuideReport;
import com.ats.rusaaccessweb.reportnew.model.PlagarismCodeEthicsReport;
import com.ats.rusaaccessweb.reportnew.model.QualInitiativeAssurance;
import com.ats.rusaaccessweb.reportnew.model.RareBookManuscriptSpec;
import com.ats.rusaaccessweb.reportnew.model.ReaddressalOfStudGrievennce;
import com.ats.rusaaccessweb.reportnew.model.ResearchProjNoPerTeacher;
import com.ats.rusaaccessweb.reportnew.model.ResrchProjectGrants;
import com.ats.rusaaccessweb.reportnew.model.SportsCulturalActivityComp;
import com.ats.rusaaccessweb.reportnew.model.StakeHolderFBReport;
import com.ats.rusaaccessweb.reportnew.model.StudCompRatioReport;
import com.ats.rusaaccessweb.reportnew.model.StudEnrooledForProgramReport;
import com.ats.rusaaccessweb.reportnew.model.StudPrfrmInFinlYr;
import com.ats.rusaaccessweb.reportnew.model.StudProgression;
import com.ats.rusaaccessweb.reportnew.model.StudQualifyingExamReport;
import com.ats.rusaaccessweb.reportnew.model.StudTeachrRatio;
import com.ats.rusaaccessweb.reportnew.model.StudentPerformanceOutcome;
import com.ats.rusaaccessweb.reportnew.model.TeacExpFullTimFac;
import com.ats.rusaaccessweb.reportnew.model.TeacherAwardRecognitn;
import com.ats.rusaaccessweb.reportnew.model.TeacherStudUsingLib;
import com.ats.rusaaccessweb.reportnew.model.TechrResrchPaprJournlInfo;
import com.ats.rusaaccessweb.reportnew.model.TechrResrchPaprJournlRatio;
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

			AcademicYear[] quolArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAcademicYearListByTypeId", map, AcademicYear[].class);
			List<AcademicYear> acaYearList = new ArrayList<>(Arrays.asList(quolArray));

			model.addObject("acaYearList", acaYearList);

			ProgramType[] progTypes = Constants.getRestTemplate().getForObject(Constants.url + "getAllProgramType",
					ProgramType[].class);
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

			NoOfPrograms[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getNoOfProgramsList",
					map, NoOfPrograms[].class);
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
						System.err.println("response before " +response.getContentType());
						response.setContentType("application/vnd.ms-excel");
						System.err.println("response after " +response.getContentType());

						
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Curricular Aspects:No of Certificate-Diploma Programs";

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

			FacParticipationInBodies[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getFacParticipationInBodies", map, FacParticipationInBodies[].class);
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
						reportName = "Curricular Aspects:Percentage of Participation in various University Bodies";

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

		String reportName = "Teaching-Learning and Evaluation : Average Enrollment Percentage";
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

			AvgEnrollmentPrcnt[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAvgEnrollmentPrcnt", map, AvgEnrollmentPrcnt[].class);
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

					String tempprcnt = "0";
					try {
						if ((prog.getNoCurrentAdmitedStnt() > 0 && prog.getTotalSanctIntake() > 0)) {
							tempprcnt = decimalFormat
									.format(prog.getNoCurrentAdmitedStnt() / prog.getTotalSanctIntake() * 100);
						}
					} catch (Exception e) {
						tempprcnt = "0";
					}

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
						String tempprcnt = "0";
						try {
							if (progList.get(i).getNoCurrentAdmitedStnt() > 0
									&& progList.get(i).getTotalSanctIntake() > 0) {
								tempprcnt = decimalFormat.format(progList.get(i).getNoCurrentAdmitedStnt()
										/ progList.get(i).getTotalSanctIntake() * 100);
							}
						} catch (Exception e) {
							tempprcnt = "0";
						}
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

		String reportName = "Teaching-Learning and Evaluation : Average % of Students from other States/Countries – Yearwise";

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

			GetAvgStudYearwise[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAvgStudYearwiseLocWise", map, GetAvgStudYearwise[].class);
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
						reportName = "Teaching-Learning and Evaluation : Average Students from other States-Countries";

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

		String reportName = "Teaching-Learning and Evaluation : Teachers Using ICT";

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

			GetTeachersUsingICT[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getTeachersUsingICT", map, GetTeachersUsingICT[].class);
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

						String tempprcnt = "0";
						
						try{
							tempprcnt=decimalFormat
								.format(progList.get(i).getTechersUsingIct() / progList.get(i).getTotalFaculty() * 100);
						}catch (Exception e) {
							tempprcnt = "0";
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

		String reportName = "Teaching-Learning and Evaluation : Total No. of Mentors, No. of Students Assigned";

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

			NoOfMentorsAssignedStudent[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getNoOfMentorsAssignedStudent", map, NoOfMentorsAssignedStudent[].class);
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

					String tempprcnt = "0";
					if (prog.getTotalFacMentor() == 0.0) {
						tempprcnt = "0";
					} else {
						try {
						tempprcnt = decimalFormat.format(prog.getTotalStudMentoring() / prog.getTotalFacMentor());
						}catch (Exception e) {
							tempprcnt = "0";// TODO: handle exception
						}
					}
					cell = new PdfPCell(new Phrase("" + decimalFormat.format(tempprcnt), headFontData));
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
				document.add(new Paragraph("Current Academic Year "));
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
							try {
							tempprcnt = decimalFormat.format(progList.get(i).getTotalStudMentoring()
									/ progList.get(i).getTotalFacMentor() );
							}catch (Exception e) {
								tempprcnt = "0";// TODO: handle exception
							}
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
								"Current Academic Year" + " ", "", 'D');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Teaching-Learning and Evaluation : Total No of Mentors No of Students Assigned";

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

		String reportName = "Teaching-Learning and Evaluation : Students Performance & Learning Outcomes";

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
			StudentPerformanceOutcome[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getStudPerformancePo", map, StudentPerformanceOutcome[].class);
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
try {
					ProgramName = prog.getProgramName();
}catch (Exception e) {
	ProgramName="-";
}

				}
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

						String leaveSum = "";//"Program: " + progList.get(0).getProgramName() + "";
						try {
						leaveSum = "Program: " + progList.get(0).getProgramName() + "";
						}catch (Exception e) {
							leaveSum ="-";
						}
						
						String leaveSum1 = ",Program Type: " + temp_prog_name + "";

						String reportSummary = leaveSum + "" + leaveSum1;

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, reportSummary, "",
								'C');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Teaching-Learning and Evaluation : Students Performance-Learning Outcomes";

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

		String reportName = "Teaching-Learning and Evaluation : Admissions feeds against reservation category";

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

			AdmissionsAgainstCategory[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAdmisssionsAgainstCat", map, AdmissionsAgainstCategory[].class);
			List<AdmissionsAgainstCategory> progList = new ArrayList<>(Arrays.asList(resArray));
			System.out.println("showAvgStudYearwiseReport------" + progList);
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

					String tempprcnt = "0";
					try {
						if (prog.getCatTotStudent() > 0 && prog.getSeatsAvaailable() > 0) {
							tempprcnt = decimalFormat.format(prog.getCatTotStudent() / prog.getSeatsAvaailable() * 100);
						}
					} catch (Exception e) {
						tempprcnt = "0";
					}
					cell = new PdfPCell(new Phrase("" + tempprcnt, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					try {
						rslt = rslt + Double.parseDouble(tempprcnt);
					} catch (Exception e) {
						rslt=0;// TODO: handle exception
						e.printStackTrace();
					}
				}

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("  Academic Year :" + temp_ac_year + " -"+progList.get(0).getCastName()));
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

						String tempprcnt = "0";

						try {
							if (progList.get(i).getCatTotStudent() > 0 && progList.get(i).getSeatsAvaailable() > 0) {
								tempprcnt = decimalFormat.format(progList.get(i).getCatTotStudent()
										/ progList.get(i).getSeatsAvaailable() * 100);
							}
						} catch (Exception e) {
							tempprcnt = "0";
						}
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
						float reportSummary1 = (float) (rslt / 5);
						reportSummary1=roundUp(reportSummary1);
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

			LibAutoLMSInfo[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getLibLMSInfo", map,
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

			RareBookManuscriptSpec[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getRareBookManuscript", map, RareBookManuscriptSpec[].class);
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

			LibSpecFacilities[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getLibSpecFacilities", map, LibSpecFacilities[].class);
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

			TeacherStudUsingLib[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getTeachersStudUsingLib", map, TeacherStudUsingLib[].class);
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
					try {
						if(prog.getNoOfFullTimeFaculty()>0) {
					n1 = ((prog.getAvgStudent() + prog.getAvgTeacher())
							/ (prog.getNoOfFullTimeFaculty() + prog.getNoOfCurrentAdmitedStnt())) * 100;
						}
					}catch (Exception e) {
						n1=0;
					}

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
						reportName = "Infrastructure and Learning Resources:No of Students and Teachers using Library Per Day";

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

			TrainProgForTeacherStaff[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getTrainProgForTeachStaffDetail", map, TrainProgForTeacherStaff[].class);
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
						reportName = "Average No of Training Programmes Organized for Teachers and Non Teaching staff (Professional Development- Administrative)";

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

			EGovernenceOperation[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "geteGovernanceOpt", map, EGovernenceOperation[].class);
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
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", " ", 'D');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Governance Leadership and Management:E-Governance Areas of Operation";
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

			FinancialSuppToProfMem[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getFinancialSuppToProfMemDetail", map, FinancialSuppToProfMem[].class);
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
						reportName = "Financial Support to Professional Membership-Conference-Workshop";

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

			FinancialSuppToProfMem[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getFinancialSuppToProfMemDetail", map, FinancialSuppToProfMem[].class);
			List<FinancialSuppToProfMem> progList = new ArrayList<>(Arrays.asList(resArray));
			// System.out.println("Prg Ldtp-------"+progList);
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

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(), headFontData)); // prog.getFacultyFirstName()
																									// + " " +
																									// prog.getFacultyLastName()
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
						reportName = "Funds-Grants Received from Non-Gov Organisation-Individuals-Other Agencies (in Cr)";

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

			NoFacultyFinSupp[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getNoFacultyFinSuppDetail", map, NoFacultyFinSupp[].class);
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

					String tempprcnt ="0";// decimalFormat.format(prog.getNofFacSupport() / prog.getTotalFaculty() * 100);
					try {
						if( prog.getTotalFaculty()>0 && prog.getNofFacSupport()>0) {
					tempprcnt = decimalFormat.format(prog.getNofFacSupport() / prog.getTotalFaculty() * 100);
					}
				}catch (Exception e) {
					tempprcnt ="0";
				}
					
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
						String tempprcnt ="0";
						try {
							if(progList.get(i).getNofFacSupport()>0 && progList.get(i).getTotalFaculty()>0)
						 tempprcnt = decimalFormat
								.format(progList.get(i).getNofFacSupport() / progList.get(i).getTotalFaculty() * 100);
						}catch (Exception e) {
							tempprcnt ="0";
						}
						cnt = cnt + i;
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getNofFacSupport());
						rowData.add("" + decimalFormat.format(tempprcnt));

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
						reportName = "No of Faculty Financial support to Professional Membership-Conference-Workshop ";

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

			TrainProgOrgnizedForTeach[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getTrainProgOrgnizedForTeach", map, TrainProgOrgnizedForTeach[].class);
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
				// System.err.println("temp bean ::" + progList.get(0).toString());
				String tempprcnt = null;
				try {
					tempprcnt = decimalFormat.format((temp / progList.get(0).getTotCount()) * 100);
				} catch (Exception e) {
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
					String tempprcnt1 ="0";
					// decimalFormat.format((temp1 / progList.get(0).getTotCount()) * 100);
					try {
						tempprcnt1 = decimalFormat.format((temp1 / progList.get(0).getTotCount()) * 100);
					}catch (Exception e) {
						tempprcnt1 ="0";
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
								"Academic Year:" + temp_ac_year + " ",
								" Avg % of Training Program attended by Teachers:" + tempprcnt1 + " ", 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Governance-Leadership and Management:Training programmes organized for Teachers (Professional Development) ";

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

			TrainProgOrgnizedForTeach[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getTrainProgOrgnizedForTeach", map, TrainProgOrgnizedForTeach[].class);
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
				float tempprcnt = 0;
				/// System.err.println("temp bean ::" + progList.get(0).toString());
				try {
					tempprcnt = Float.parseFloat(decimalFormat.format((temp / progList.get(0).getTotCount()) * 100));
				} catch (Exception e) {
					// TODO: handle exception
					tempprcnt = 0;
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
					String tempprcnt1 = "0";//decimalFormat.format((temp / progList.get(0).getTotCount()) * 100);
					try {
						tempprcnt1 = decimalFormat.format((temp / progList.get(0).getTotCount()) * 100);
					}catch (Exception e) {
						tempprcnt1 = "0";
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
								"Academic Year:" + temp_ac_year + " ",
								" Avg % of Training Program attended by Teachers:" + tempprcnt1 + " ", 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Governance-Leadership and Management:Training programmes organized for Teachers (Administrative Development) ";

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

			GetMissions[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getInstMissionList",
					map, GetMissions[].class);
			List<GetMissions> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			GetVisions[] resArray1 = Constants.getRestTemplate().postForObject(Constants.url + "getInstVisionList", map,
					GetVisions[].class);
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
						reportName = "Governance-Leadership and Management : Institutional Vision-Mission";

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
			IQACQualInititive[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getQualInititiveList", map, IQACQualInititive[].class);
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
						reportName = "Governance-Leadership and Management : Quality Asssurance Initiative";

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
			QualInitiativeAssurance[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getInstQualAssurance", map, QualInitiativeAssurance[].class);
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
						
						reportName = "Governance-Leadership and Management: Quality Initiative by IQAC";

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
			GenderEquityProg[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getGenderEquityProgDetails", map, GenderEquityProg[].class);
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
			NoOfGenderEquityProg[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getNoOfGenderEquityProg", map, NoOfGenderEquityProg[].class);
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

		String reportName = "Institutional Values and Best Practices : Gender sensitivity in Providing Facility ";

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

			EGovernenceOperation[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "geteGovernanceOpt", map, EGovernenceOperation[].class);
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

		String reportName = "Institutional Values and Best Practices : Alternative Energy Initiative";

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

			EGovernenceOperation[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "geteGovernanceOpt", map, EGovernenceOperation[].class);
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
					//System.err.println("data  " + progList.toString());
					//System.err.println("I*** " + prog.getInstYesnoResponse());

					if (prog.getYesnoTitle().equalsIgnoreCase("Total Power requirement")) {
						if (prog.getInstYesnoResponse() != null || prog.getInstYesnoResponse() != " "
								|| prog.getInstYesnoResponse() != "NA"
								|| prog.getInstYesnoResponse().isEmpty() == true) {
							try {
								temp_tot = Double.parseDouble(prog.getInstYesnoResponse());
							} catch (Exception e) {
								/// temp_tot=0;
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

					if (prog.getInstYesnoResponse().equals("-") || prog.getInstYesnoResponse().equals("NA")
							|| Integer.parseInt(prog.getInstYesnoResponse()) == 0 || temp_tot == 0.0) {
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
							
							if(!progList.get(i).getInstYesnoResponse().equalsIgnoreCase("na")) 
							temp_tot1 = Double.parseDouble(progList.get(i).getInstYesnoResponse());
							else
								temp_tot1=0;
						}
						rowData.add("" + (i + 1));
						rowData.add("" + progList.get(i).getAcademicYear());
						rowData.add("" + progList.get(i).getYesnoTitle());
						rowData.add("" + progList.get(i).getInstYesnoResponse());

						if (progList.get(i).getInstYesnoResponse().equals("-")) {
							val1 = "-";
							
						} else {
							if(progList.get(i).getInstYesnoResponse().equalsIgnoreCase("na")) {
								val1="0";
							}else
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

		String reportName = "Institutional Values and Best Practices: Power Requirement met through LED Bulbs for Lighting "
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

			EGovernenceOperation[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "geteGovernanceOpt", map, EGovernenceOperation[].class);
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

					EGovernenceOperation prog = progList.get(i);
					System.err.println("I " + prog.getInstYesnoResponse());

					if (prog.getYesnoTitle().equalsIgnoreCase("Total Power requirement")) {
						try {
							temp_tot = Double.parseDouble(prog.getInstYesnoResponse());

						} catch (Exception e) {
							temp_tot = 0;
						}

					}
					if (prog.getYesnoTitle().equalsIgnoreCase("Power requirements of LED bulbs")) {
						try {
							temp_tot_led = Double.parseDouble(prog.getInstYesnoResponse());
						} catch (Exception e) {
							temp_tot_led = 0;

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

				if (temp_tot != 0.0) {
					val = String.valueOf(((temp_tot_led) / temp_tot) * 100);
				} else {
					val = "0.0";
				}

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

			IniToEngageLocComm[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getInitiativeOfLocalCommunityProg", map, IniToEngageLocComm[].class);
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

			UniversalValPromot[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getUniversalValues", map, UniversalValPromot[].class);
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

			NoOfUniversalvalues[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getNoUniversalValues", map, NoOfUniversalvalues[].class);
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

			GovtScheme[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getGovtScheme", map,
					GovtScheme[].class);
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

			OtherThanGovtScheme[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getOtherThanGovtScheme", map, OtherThanGovtScheme[].class);
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
					try {
						fin = fin + Double.parseDouble(tempprcnt);
					} catch (Exception e) {
						e.printStackTrace();
					}
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

			OtherThanGovtScheme[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getOtherThanGovtScheme", map, OtherThanGovtScheme[].class);
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

			DistinguishedAlumini[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getDistinctAlumini", map, DistinguishedAlumini[].class);
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

			AluminiEngagement[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getAluminiEngg",
					map, AluminiEngagement[].class);
			List<AluminiEngagement> progList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", progList);

			map = new LinkedMultiValueMap<>();
			map.add("instId", instituteId);
			GetAluminiEngagementReport editFaculty = Constants.getRestTemplate().postForObject(
					Constants.url + "/getAluminiEngagementDetails", map, GetAluminiEngagementReport.class);

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

			SportsCulturalActivityComp[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getSportsActivityComp", map, SportsCulturalActivityComp[].class);
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

			SportsCulturalActivityComp[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getSportsActivityComp", map, SportsCulturalActivityComp[].class);
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

			CapabilityEnhancementDev[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getCapabilityEnhancementDev", map, CapabilityEnhancementDev[].class);
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

			CapabilityEnhancementDev[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getCapabilityEnhancementDev", map, CapabilityEnhancementDev[].class);
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

			StudQualifyingExamReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getStudQualifyingExam", map, StudQualifyingExamReport[].class);
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

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(prog.getPrcnt()), headFontData));
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
					result=0;
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
						rowData.add("" + decimalFormat.format(progList.get(i).getPrcnt()));

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						result = result + progList.get(i).getPrcnt();
					}
					double x2 = result / 5;
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
								"Academic Year:" + temp_ac_year + " ", "Avg%" + decimalFormat.format(x2) + " ", 'F');
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

			AluminiAssoMeetReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAluminiAssoMeetDetails", map, AluminiAssoMeetReport[].class);
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

			EGovernenceOperation[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "geteGovernanceOpt", map, EGovernenceOperation[].class);
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

			ExtensionActivityReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getExtesionActivityDetails", map, ExtensionActivityReport[].class);
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
				document.add(new Paragraph("% of Students Participating in Extra-Curricular Activities :" + decimalFormat.format(x1) + ""));

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
						String leaveSum2 = "% of Students Participating in Extra-Curricular Activities :" + decimalFormat.format(x2) + "";

						String reportSummary = leaveSum + "" + leaveSum1 + "" + leaveSum2;

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", reportSummary, 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						
						reportName = "Research-Innovation and Extension : No of Student-Teachers Participation in Extension Activity";

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

			NoOfStudTeachLinkageReport[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getNoOfStudTeachLinkageDetails", map, NoOfStudTeachLinkageReport[].class);
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
						reportName = "Research-Innovation and Extension : No of Linkages";

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

			AwardRecogDetailReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAwardRecogDetails", map, AwardRecogDetailReport[].class);
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
				document.add(new Paragraph("% of Full-time Teachers with Award/Rec/Fellowships:" + a2 + ""));

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

						String leaveSum2 = "% of Full-time Teachers with Award/Rec/Fellowships :" + a1 + "";

						System.err.println("rep  " + rep);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year:" + temp_ac_year + " ", leaveSum2, 'C');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Research-Innovation and Extension : No of Recognition-Awards";

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

			BookPublicationDetReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getBookPublicationDetails", map, BookPublicationDetReport[].class);
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

					cell = new PdfPCell(new Phrase("" + prog.getFacultyFirstName(), headFontData));
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
						
						reportName = "Research-Innovation and Extension : Book and Chapter Publication by Teachers and Papers in Conference Proceedings";

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

			NoOfBookReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getNoOfBookPaperDetails", map, NoOfBookReport[].class);
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
						
						reportName = "Research-Innovation and Extension : No Book and Chapter Publication by Teachers and Papers in Conference Proceedings";

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

			PhdGuideReport[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getPddGuideDetails",
					map, PhdGuideReport[].class);
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
						reportName = "Research-Innovation and Extension : Ph D Awarded Information";

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

			PlagarismCodeEthicsReport[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getPlagarismCodeEthicsDetails", map, PlagarismCodeEthicsReport[].class);
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
						reportName = "Research-Innovation and Extension : Plagarism and Code of Ethics";

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
			} catch (Exception e) {
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
			map.add("programId", 0/* programType */);
			StudEnrooledForProgramReport[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getStudEnrooledForProgram", map, StudEnrooledForProgramReport[].class);
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
						avg = prog.getProgStudEnrolled() / prog.getTotalStud() * 100;
					}

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(avg), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);
					finalPrcnt = finalPrcnt + avg;
				}
				String x1 = decimalFormat.format(finalPrcnt / 5);
				System.out.println("finalPrcnt is" + finalPrcnt);

				System.out.println("x1 is" + x1);

				document.open();
				Font hf = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, hf);
				name.setAlignment(Element.ALIGN_LEFT);
				document.add(name);

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("Academic Year:" + temp_ac_year + ""));
				// document.add(new Paragraph("Program Name :" +
				// progList.get(0).getProgramName() + ""));
				// document.add(new Paragraph("Program Type :" + temp_prog_name + ""));
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

						float avg = 0.0f;
						if (progList.get(i).getTotalStud() == 0) {
							avg = 0;
						} else {
							avg = progList.get(i).getProgStudEnrolled() / progList.get(i).getTotalStud();
						}
						//avg=roundUp(avg);
						rowData.add("" +  decimalFormat.format(avg));

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						rslt = rslt + avg;
					}
					String x2 = decimalFormat.format(rslt / 5);
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						String leaveSum = "";// "Program: " + progList.get(0).getProgramName() + "";
						String leaveSum1 = "";// ",Program Type: " + temp_prog_name + "";
						String leaveSum2 = ",Academic Year: " + temp_ac_year + "";

						String reportSummary = leaveSum + "" + leaveSum1 + "" + leaveSum2;

						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName, reportSummary,
								"Average%:" + x2 + "", 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						System.err.println("response before " +response.getContentType());
						response.setContentType("application/vnd.ms-excel");
						System.err.println("response after " +response.getContentType());

						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						System.err.println("date " +date + "report name  " +reportName);
						reportName="Stud Enrolled in Add On-";
						
						reportName = "Curriculam Aspects : Students Enrolled in Certi Diploma or Add-On Programs";

						response.setHeader("Content-disposition",
								"attachment; filename="+reportName+"_" + date + ".xlsx");
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

	@RequestMapping(value = "/showValueAddedCourseReportNew", method = RequestMethod.POST)
	public void showValueAddedCourseReportNew(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : Value Added Courses";

		ModelAndView model = null;
		try {
			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			model = new ModelAndView("report/prog_report1");

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();
			System.out.println("year is" + ac_year);
			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			ValueAddedCoursesReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getValueAddedCoursesDetails", map, ValueAddedCoursesReport[].class);
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
								"attachment; filename=" + reportName + "_" + date + ".xlsx");
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

			StakeHolderFBReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getStakeHolderFBDetails", map, StakeHolderFBReport[].class);
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

			ResearchProjNoPerTeacher[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getResearchProjectnoDetails", map, ResearchProjNoPerTeacher[].class);
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

			ReaddressalOfStudGrievennce[] resArray = Constants.getRestTemplate().postForObject(
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

	@RequestMapping(value = "/showStudTeachrRatio", method = RequestMethod.POST)
	public void showStudTeachrRatio(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Student Teacher Ratio";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			// System.out.println("Filter------"+temp_ac_year);
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYear", ac_year);
			map.add("instId", instituteId);

			StudTeachrRatio[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getStudTeachrRatioList", map, StudTeachrRatio[].class);
			List<StudTeachrRatio> ratioList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", ratioList);

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
				headingName = ratioList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Total No. of Full Time Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No. of Student Enrolled", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("STR of Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				int studTchrRato = 0;

				for (int i = 0; i < ratioList.size(); i++) {
					// System.err.println("I " + i);
					StudTeachrRatio ratio = ratioList.get(i);
					studTchrRato = ratio.getNoCurrentAdmitedStnt() / ratio.getNoOfFulltimeFaculty();

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getNoOfFulltimeFaculty(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getNoCurrentAdmitedStnt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studTchrRato, headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));
				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					rowData.add("Academic Year");
					rowData.add("Total No. of Full Time Faculty");
					rowData.add("Total No. of Student Enrolled");
					rowData.add("STR of Year");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					int str = 0;
					String acYear = null;
					for (int i = 0; i < ratioList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						str = ratioList.get(i).getNoCurrentAdmitedStnt() / ratioList.get(i).getNoOfFulltimeFaculty();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + ratioList.get(i).getAcademicYear());
						rowData.add("" + ratioList.get(i).getNoOfFulltimeFaculty());
						rowData.add("" + ratioList.get(i).getNoCurrentAdmitedStnt());
						rowData.add("" + str);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = ratioList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'E');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showFacultyAgnstSanctionpost", method = RequestMethod.POST)
	public void showFacultyAgnstSanctionpost(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Faculty Available Against Sanctioned Post";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			FacAgnstSanctnPost[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getFacAgnstSanctnPostList", map, FacAgnstSanctnPost[].class);
			List<FacAgnstSanctnPost> postList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", postList);

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
				headingName = postList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Full Time Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No. of Sanctioned Post", tableHeaderFont));
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

				hcell = new PdfPCell(new Phrase("Year Wise %", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float postPer = 0.0f;

				for (int i = 0; i < postList.size(); i++) {
					// System.err.println("I " + i);
					FacAgnstSanctnPost post = postList.get(i);
					try {
						postPer = ((float)post.getNoOfFulltimeFaculty() / (float)post.getSanctionedPost()) * 100;
						

					} catch (Exception e) {
						System.err.println("Invalid Values---" + e.getMessage());
					}
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + post.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + post.getNoOfFulltimeFaculty(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + post.getSanctionedPost(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + post.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 */
					postPer=roundUp(postPer);

					cell = new PdfPCell(new Phrase("" + postPer, headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));
				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					rowData.add("Academic Year");
					rowData.add("No. of Full Time Faculty");
					rowData.add("No. of Sanctioned Post");
					// rowData.add("Institute Name");
					rowData.add("Year Wise %");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					float str = 0.0f;
					String acYear = null;
					for (int i = 0; i < postList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						str = ((float)postList.get(i).getNoOfFulltimeFaculty() / (float)postList.get(i).getSanctionedPost()) * 100;
						str=roundUp(str);
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + postList.get(i).getAcademicYear());
						rowData.add("" + postList.get(i).getNoOfFulltimeFaculty());
						rowData.add("" + postList.get(i).getSanctionedPost());
						// rowData.add("" + postList.get(i).getInstituteName());
						rowData.add("" + str);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					acYear = postList.get(0).getAcademicYear();
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = postList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'E');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showDifferentlyAbledStud", method = RequestMethod.POST)
	public void showDifferentlyAbledStud(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Differently Abled Students (Divyanjan)";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYear", ac_year);
			map.add("instId", instituteId);

			DifferentlyAbldStudReport[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getDifferntlyAbldStudList", map, DifferentlyAbldStudReport[].class);
			List<DifferentlyAbldStudReport> studList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", studList);

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
				headingName = studList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Differently Abled Student", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No. of Student Enrolled", tableHeaderFont));
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

				hcell = new PdfPCell(new Phrase("Year Wise %", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float studPer = 0;
				for (int i = 0; i < studList.size(); i++) {
					// System.err.println("I " + i);
					DifferentlyAbldStudReport stud = studList.get(i);
					studPer = 0;
					try {
						if(stud.getNoOfPwdStud()>0 && stud.getTotalStudEnrolled()>0)
						studPer = ((float)stud.getNoOfPwdStud() * 100 )/ (float)stud.getTotalStudEnrolled();
						studPer=roundUp(studPer);
					} catch (Exception e) {
						studPer = 0;
						System.err.println("Invalid Values---" + e.getMessage());
					}
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getNoOfPwdStud(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getTotalStudEnrolled(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + stud.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + studPer, headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));
				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					rowData.add("Academic Year");
					rowData.add("No. of Differently Abled Student");
					rowData.add("Total No. of Student Enrolled");
					// rowData.add("Institute Name");
					rowData.add("Year Wise %");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					float str = 0;
					for (int i = 0; i < studList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						 str = 0;
							try {
								if(studList.get(i).getNoOfPwdStud()>0 &&studList.get(i).getTotalStudEnrolled()>0 )
							str = ((float)studList.get(i).getNoOfPwdStud() * 100) /  (float)studList.get(i).getTotalStudEnrolled();
							str=roundUp(str);
							}catch (Exception e) {
								str = 0;
							}
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + studList.get(i).getAcademicYear());
						rowData.add("" + studList.get(i).getNoOfPwdStud());
						rowData.add("" + studList.get(i).getTotalStudEnrolled());
						// rowData.add("" + studList.get(i).getInstituteName());
						rowData.add("" + str);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = studList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'E');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showFacultyAgnstSanctionPostOthrState", method = RequestMethod.POST)
	public void showFacultyAgnstSanctionPostOthrState(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Full Time Faculty From Other States Against Sanctioned Post";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYear", ac_year);
			map.add("instId", instituteId);

			FacAgnstSanctnPostOthrState[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getFacultyAgnstSanctionPostOthrState", map, FacAgnstSanctnPostOthrState[].class);
			List<FacAgnstSanctnPostOthrState> facList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", facList);

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
				headingName = facList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Full Time Faculty from other States", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Sanctioned Post", tableHeaderFont));
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

				hcell = new PdfPCell(new Phrase("Year Wise %", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float facPer = 0;

				for (int i = 0; i < facList.size(); i++) {
					// System.err.println("I " + i);
					FacAgnstSanctnPostOthrState fac = facList.get(i);
					try {
						facPer=0;
						if(fac.getNoOfOtherStateFac()>0 &&fac.getSanctionedPost()>0)
						facPer = ((float)fac.getNoOfOtherStateFac() / (float)fac.getSanctionedPost()) * 100;
						facPer=roundUp(facPer);
						} catch (Exception e) {
							facPer=0;
						System.err.println("Invalid Values---" + e.getMessage());
					}
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getNoOfOtherStateFac(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getSanctionedPost(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + fac.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + facPer, headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));
				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					rowData.add("Academic Year");
					rowData.add("No. of Full Time Faculty From Other States");
					rowData.add("No. of Sanctioned Post");
					// rowData.add("Institute Name");
					rowData.add("Year Wise %");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					float str = 0;
					for (int i = 0; i < facList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						try {
							str=0;
							if(facList.get(i).getNoOfOtherStateFac()>0 &&facList.get(i).getSanctionedPost()>0)
							str = ((float)facList.get(i).getNoOfOtherStateFac() / (float)facList.get(i).getSanctionedPost()) * 100;
							str=roundUp(str);
						} catch (Exception e) {
							str=0;
							System.err.println("Invalid Values---" + e.getMessage());
						}
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + facList.get(i).getAcademicYear());
						rowData.add("" + facList.get(i).getNoOfOtherStateFac());
						rowData.add("" + facList.get(i).getSanctionedPost());
						// rowData.add("" + facList.get(i).getInstituteName());
						rowData.add("" + str);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = facList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'E');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showTeachingExpOfFillTimFac", method = RequestMethod.POST)
	public void showTeachingExpOfFillTimFac(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Teaching Experience of Full Time Faculty(Current Data)";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			// String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			// map.add("acYear", ac_year);
			map.add("instId", instituteId);

			TeacExpFullTimFac[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getTeachingExpOfFillTimFac", map, TeacExpFullTimFac[].class);
			List<TeacExpFullTimFac> facList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", facList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = facList.get(0).getInstituteName();
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

				/*
				 * hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Name of Full Time Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("PAN No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Designation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Department", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				/*
				 * hcell = new PdfPCell(new Phrase("Institute", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Experience %(Yrs)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float expCount = 0;

				for (int i = 0; i < facList.size(); i++) {
					// System.err.println("I " + i);
					TeacExpFullTimFac fac = facList.get(i);

					expCount = Float.parseFloat(fac.getCurExp()) + expCount;

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getFacultyFirstName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getfPan(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getDesignationName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getDeptName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + fac.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + fac.getCurExp(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

				}
				// System.out.println("Faculty Count-----" + index);
				// System.out.println("Exp Count----" + expCount);

				float teachingExp = expCount / index;
				// System.out.println("Teaching Experience----" + teachingExp);

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				// document.add(new Paragraph("Academic Year : 2019-20"));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Teaching Experience : " + teachingExp));

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
					// rowData.add("Academic Year");
					rowData.add("Name of Full Time Faculty");
					rowData.add("PAN No.");
					rowData.add("Designation");
					rowData.add("Name of Department");
					// rowData.add("Institute Name");
					rowData.add("Experience %(Yrs)");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					float expCnt = 0;
					for (int i = 0; i < facList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						expCnt = Float.parseFloat(facList.get(i).getCurExp()) + expCount;
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + facList.get(i).getFacultyFirstName());
						rowData.add("" + facList.get(i).getfPan());
						rowData.add("" + facList.get(i).getDesignationName());
						rowData.add("" + facList.get(i).getDeptName());
						// rowData.add("" + facList.get(i).getInstituteName());
						rowData.add("" + facList.get(i).getCurExp());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					// System.out.println("Count" + cnt);
					float teachExp = expCnt / cnt;
					// System.out.println("Teaching Exo=" + teachExp);
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = facList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "", "", 'F');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showFulTimFacAvalblePhd", method = RequestMethod.POST)
	public void showFulTimFacAvalblePhd(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Full Time Faculty Available With Ph.D.s";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			// String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			// map.add("acYear", ac_year);
			map.add("instId", instituteId);

			FulTimFacultyWithPhd[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getFulTimFacAvalblePhd", map, FulTimFacultyWithPhd[].class);
			List<FulTimFacultyWithPhd> facList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", facList);

			Document document = new Document(PageSize.A4);

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
				headingName = facList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(3);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, });

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

				/*
				 * hcell = new PdfPCell(new Phrase("Institute", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. Ph.D Awarded Faculty", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				int facPer = 0;

				for (int i = 0; i < facList.size(); i++) {
					// System.err.println("I " + i);
					FulTimFacultyWithPhd fac = facList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + fac.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + fac.getfPassingYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fac.getNoOfPhdFac(), headFontData));
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
				// document.add(new Paragraph("Academic Year : 2019-20"));
				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					// rowData.add("Institute Name");
					rowData.add("Academic Year");
					rowData.add("No. Ph.D Awarded Faculty");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					int str = 0;
					for (int i = 0; i < facList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));

						// rowData.add("" + facList.get(i).getInstituteName());
						rowData.add("" + facList.get(i).getfPassingYear());
						rowData.add("" + facList.get(i).getNoOfPhdFac());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = facList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "", "", 'C');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Teaching Learning and Evaluation : Full Time Faculty Available With PhDs";

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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	/*
	 * @RequestMapping(value = "/showAdmisionAgnstResrvCat", method =
	 * RequestMethod.POST) public void showAdmisionAgnstResrvCat(HttpServletRequest
	 * request, HttpServletResponse response) {
	 * 
	 * String reportName =
	 * "Teaching Learning and Evaluation : Admissions Feeds Against Reservation Category"
	 * ;
	 * 
	 * ModelAndView model = null; try {
	 * 
	 * model = new ModelAndView("report/ratio_report1");
	 * 
	 * HttpSession session = request.getSession();
	 * 
	 * String ac_year = request.getParameter("ac_year"); int instituteId = (int)
	 * session.getAttribute("instituteId");
	 * 
	 * map = new LinkedMultiValueMap<>(); map.add("acYearList", ac_year);
	 * map.add("instId", instituteId);
	 * 
	 * AdmsnAgnstResrvCat[] resArray = rest.postForObject(Constants.url +
	 * "/getAdmisionAgnstResrvCat", map, AdmsnAgnstResrvCat[].class);
	 * List<AdmsnAgnstResrvCat> facList = new ArrayList<>(Arrays.asList(resArray));
	 * 
	 * model.addObject("list", facList);
	 * 
	 * Document document = new Document(PageSize.A4); // 50, 45, 50, 60
	 * document.setMargins(Constants.marginLeft, Constants.marginRight,
	 * Constants.marginTop, Constants.marginBottom);
	 * document.setMarginMirroring(false);
	 * 
	 * String FILE_PATH = Constants.REPORT_SAVE; File file = new File(FILE_PATH);
	 * 
	 * PdfWriter writer = null;
	 * 
	 * FileOutputStream out = new FileOutputStream(FILE_PATH); try { writer =
	 * PdfWriter.getInstance(document, out); } catch (DocumentException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * String header = ""; String title = "";
	 * 
	 * DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy"); String headingName =
	 * null; try { headingName = facList.get(0).getInstituteName(); } catch
	 * (Exception e) {
	 * 
	 * headingName = "-";
	 * 
	 * } ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);
	 * 
	 * writer.setPageEvent(event);
	 * 
	 * PdfPTable table = new PdfPTable(6);
	 * 
	 * table.setHeaderRows(1);
	 * 
	 * try { table.setWidthPercentage(100); table.setWidths(new float[] { 2.4f,
	 * 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
	 * 
	 * Font headFontData = Constants.headFontData;// new
	 * Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, // BaseColor.BLACK); Font
	 * tableHeaderFont = Constants.tableHeaderFont; // new
	 * Font(FontFamily.HELVETICA, 12, Font.BOLD, // BaseColor.BLACK);
	 * tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);
	 * 
	 * PdfPCell hcell = new PdfPCell();
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Cast", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new
	 * Phrase("No. of Students Admitted in Reservation Category", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("No. of Seat Available", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * 
	 * hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * 
	 * hcell = new PdfPCell(new Phrase("%Year", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * 
	 * int index = 0; float admPer = 0; float ttlAdmPer = 0;
	 * 
	 * for (int i = 0; i < facList.size(); i++) { // System.err.println("I " + i);
	 * AdmsnAgnstResrvCat fac = facList.get(i); try { admPer =
	 * fac.getCatTotStudent()*100/fac.getSeatsAvailable(); ttlAdmPer =
	 * admPer+ttlAdmPer; }catch(Exception e) {
	 * System.err.println("Invalid No.---"+e.getMessage()); admPer=0; }
	 * 
	 * index++; PdfPCell cell; cell = new PdfPCell(new Phrase(String.valueOf(index),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + fac.getAcademic_year(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + fac.getCastName(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + fac.getCatTotStudent(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); // cell.setPaddingLeft(10);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + fac.getSeatsAvailable(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * 
	 * cell = new PdfPCell(new Phrase("" + fac.getInstituteName(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * 
	 * 
	 * cell = new PdfPCell(new Phrase("" + admPer, headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * document.open(); Font reportNameFont = Constants.reportNameFont;// new
	 * Font(FontFamily.TIMES_ROMAN, 14.0f, // Font.UNDERLINE, BaseColor.BLACK);
	 * 
	 * Paragraph name = new Paragraph(reportName, reportNameFont);
	 * name.setAlignment(Element.ALIGN_CENTER); document.add(name); document.add(new
	 * Paragraph("\n")); document.add(new
	 * Paragraph("Academic Year : "+facList.get(0).getAcademic_year()));
	 * 
	 * 
	 * // document.add(new Paragraph("Institute " + //
	 * ratioList.get(0).getInstituteName()));
	 * 
	 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
	 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
	 * document.add(new Paragraph(" "));
	 * 
	 * 
	 * DateFormat DF = new SimpleDateFormat("dd-MM-yyyy"); document.add(new
	 * Paragraph("\n")); document.add(table); document.add(new Paragraph("\n"));
	 * //System.out.println("Count-----"+ttlAdmPer); document.add(new
	 * Paragraph("Average %: "+ttlAdmPer/5));
	 * 
	 * int totalPages = writer.getPageNumber();
	 * 
	 * //System.out.println("Page no " + totalPages);
	 * 
	 * document.close(); int p = Integer.parseInt(request.getParameter("p"));
	 * System.err.println("p " + p);
	 * 
	 * if (p == 1) {
	 * 
	 * if (file != null) {
	 * 
	 * String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	 * 
	 * if (mimeType == null) {
	 * 
	 * mimeType = "application/pdf";
	 * 
	 * }
	 * 
	 * response.setContentType(mimeType);
	 * 
	 * response.addHeader("content-disposition",
	 * String.format("inline; filename=\"%s\"", file.getName()));
	 * 
	 * response.setContentLength((int) file.length());
	 * 
	 * InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 * 
	 * try { FileCopyUtils.copy(inputStream, response.getOutputStream()); } catch
	 * (IOException e) { //System.out.println("Excep in Opening a Pdf File");
	 * e.printStackTrace(); } } } else {
	 * 
	 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
	 * 
	 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
	 * ArrayList<String>();
	 * 
	 * rowData.add("Sr. No"); //rowData.add("Academic Year");
	 * rowData.add("Academic Year"); rowData.add("Cast");
	 * rowData.add("No. of Students Admitted in Reservation Category");
	 * rowData.add("No. of Seat Available"); //rowData.add("Institute Name");
	 * rowData.add("%Year");
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * int cnt = 1; float admCatPer = 0; for (int i = 0; i < facList.size(); i++) {
	 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>(); try {
	 * admCatPer =
	 * facList.get(i).getCatTotStudent()*100/facList.get(i).getSeatsAvailable();
	 * }catch(Exception e) { System.err.println("Invalid No.---"+e.getMessage());
	 * admCatPer=0; } cnt = cnt + i;
	 * 
	 * rowData.add("" + (i + 1)); rowData.add("" +
	 * facList.get(i).getAcademic_year()); rowData.add("" +
	 * facList.get(i).getCastName()); rowData.add("" +
	 * facList.get(i).getCatTotStudent()); rowData.add("" +
	 * facList.get(i).getSeatsAvailable()); //rowData.add("" +
	 * facList.get(i).getInstituteName()); rowData.add("" + admCatPer);
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * } XSSFWorkbook wb = null; try {
	 * 
	 * //System.out.println("Excel List :" + exportToExcelList.toString()); String
	 * rep = null; try { rep = facList.get(0).getInstituteName(); } catch (Exception
	 * e) {
	 * 
	 * rep = "-";
	 * 
	 * } System.err.println("headingName  " + headingName); // String excelName =
	 * (String) session.getAttribute("excelName"); wb =
	 * ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "");
	 * ExceUtil.autoSizeColumns(wb, 3);
	 * response.setContentType("application/vnd.ms-excel"); String date = new
	 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 * response.setHeader("Content-disposition", "attachment; filename=" +
	 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
	 * 
	 * } catch (IOException ioe) { throw new
	 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
	 * if (wb != null) { wb.close(); } }
	 * 
	 * }
	 * 
	 * } catch (DocumentException ex) {
	 * 
	 * //System.out.println("Pdf Generation Error: " + ex.getMessage());
	 * 
	 * ex.printStackTrace();
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.println("Exce in showratioReport " + e.getMessage());
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 */

	@RequestMapping(value = "/showStudPerformInFinalYear", method = RequestMethod.POST)
	public void showStudPerformInFinalYear(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Teaching Learning and Evaluation : Students Performance in Final Year";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			StudPrfrmInFinlYr[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getStudPerformInFinalYear", map, StudPrfrmInFinlYr[].class);
			List<StudPrfrmInFinlYr> studList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", studList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = studList.get(0).getInstituteName();
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

				/*
				 * hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Name of Programme", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Programme Code", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Final Year Students Appeared for University Exams", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Final Year Students Passed for University Exams", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Final Year Students Passed", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < studList.size(); i++) {
					// System.err.println("I " + i);
					StudPrfrmInFinlYr stud = studList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					/*
					 * table.addCell(cell); cell = new PdfPCell(new Phrase("" +
					 * stud.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 */

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getProgramName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getNameOfProgram(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getNoStudAppear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getNoStudPass(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getPassingPer(), headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					// rowData.add("Academic Year");
					// rowData.add("Institute Name");
					rowData.add("Name of Programme");
					rowData.add("Programme Code");
					rowData.add("No. of Final Year Students Appeared for University Exams");
					rowData.add("No. of Final Year Students Passed for University Exams");
					rowData.add("% of Final Year Students Passed");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < studList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						// rowData.add("" + studList.get(i).getInstituteName());
						rowData.add("" + studList.get(i).getProgramName());
						rowData.add("" + studList.get(i).getNameOfProgram());
						rowData.add("" + studList.get(i).getNoStudAppear());
						rowData.add("" + studList.get(i).getNoStudPass());
						rowData.add("" + studList.get(i).getPassingPer());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = studList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'F');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showICTEnbldFaclties", method = RequestMethod.POST)
	public void showICTEnbldFaclties(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : ICT Enabled Facilities";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			ICtEnbldFaclitiesReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getICTEnbldFaclties", map, ICtEnbldFaclitiesReport[].class);
			List<ICtEnbldFaclitiesReport> ictFacList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", ictFacList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = ictFacList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Classroom with LCD", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Classroom with WiFi/LAN", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Seminar HAll with ICT", tableHeaderFont));
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

				int index = 0;

				for (int i = 0; i < ictFacList.size(); i++) {
					// System.err.println("I " + i);
					ICtEnbldFaclitiesReport ictFac = ictFacList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + ictFac.getNoClassromLcd(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ictFac.getNoClassroomWifi(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ictFac.getIctSeminarHall(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + ictFac.getInstituteName(),
					 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */

				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					// rowData.add("Academic Year");
					rowData.add("No. of Classroom with LCD");
					rowData.add("No. of  Classroom with WiFi/LAN");
					rowData.add("No. of Seminar Hall");
					// rowData.add("Institute Name");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < ictFacList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + ictFacList.get(i).getNoClassromLcd());
						rowData.add("" + ictFacList.get(i).getNoClassroomWifi());
						rowData.add("" + ictFacList.get(i).getIctSeminarHall());
						// rowData.add("" + ictFacList.get(i).getInstituteName());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = ictFacList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'E');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showExpenditureOnPrchaseBooksJournal", method = RequestMethod.POST)
	public void showExpenditureOnPrchaseBooksJournal(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Expenditure on Purchase of	 Books and Journals";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			ExpenditureOnPrchaseBooksJournal[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getExpenditureOnPrchaseBooksJournal", map,
					ExpenditureOnPrchaseBooksJournal[].class);
			List<ExpenditureOnPrchaseBooksJournal> bookList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", bookList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = bookList.get(0).getInstituteName();
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

				/*
				 * hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Expenditure on Books (Lakhs)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Expenditure on Journals (Lakhs)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Expenditure on e-Journals (Lakhs)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total Expenditure(Lakhs)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float avgAnulExpd = 0;
				for (int i = 0; i < bookList.size(); i++) {
					// System.err.println("I " + i);
					ExpenditureOnPrchaseBooksJournal budget = bookList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" +budget.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + budget.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getCostOfBooks(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getCostOfJournal(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getCostOfEjournal(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getTotalExpenditures(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);
					try {
						avgAnulExpd = budget.getTotalExpenditures() + avgAnulExpd;
					} catch (Exception e) {
						e.getMessage();
					}
				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);

				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Average Annual Expenditure in Last 5-Years : " + avgAnulExpd / 5));
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
					// rowData.add("Academic Year");

					// rowData.add("Institute Name");
					rowData.add("Academic Year");
					rowData.add("Expenditure on Books (Lakhs)");
					rowData.add("Expenditure on Journals (Lakhs)");
					rowData.add("Expenditure on e-Journals (Lakhs)");
					rowData.add("Total Expenditure(Lakhs)");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					float bgtPer = 0;
					float ttlBgtper = 0;
					float avgBgtPer = 0;
					for (int i = 0; i < bookList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						/// rowData.add("" + bookList.get(i).getInstituteName());
						rowData.add("" + bookList.get(i).getAcademicYear());
						rowData.add("" + bookList.get(i).getCostOfBooks());
						rowData.add("" + bookList.get(i).getCostOfJournal());
						rowData.add("" + bookList.get(i).getCostOfEjournal());
						rowData.add("" + bookList.get(i).getTotalExpenditures());

						try {
							ttlBgtper = bookList.get(i).getTotalExpenditures() + ttlBgtper;
							avgBgtPer = ttlBgtper / 5;
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					// System.out.println("AVG Budget = " + avgBgtPer);
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = bookList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");

						String avg = String.valueOf(avgBgtPer);
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year,
								"Average Annual Expenditure in Last 5-Years : " + avg, 'F');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showBudgetInfraAugmentn", method = RequestMethod.POST)
	public void showBudgetInfraAugmentn(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Budget on Infrastructure Augmentation";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			BudgetInfraAugmntn[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getBudgetInfraAugmentn", map, BudgetInfraAugmntn[].class);
			List<BudgetInfraAugmntn> budgetList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", budgetList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = budgetList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Financial Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Budget on Infrastructure Augmentation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total Budget Excluding Salary", tableHeaderFont));
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

				hcell = new PdfPCell(new Phrase("% of Budget on Infrastructure Augmentation", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float bgt = 0;
				float ttlBugtPer = 0;
				float avgPerOnBugt = 0;

				for (int i = 0; i < budgetList.size(); i++) {
					// System.err.println("I " + i);
					BudgetInfraAugmntn budget = budgetList.get(i);
					try {
						//bgt = Float.parseFloat(budget.getBudgetAllocated()) * 100 / budget.getExInt1();
						bgt = (Float.parseFloat(budget.getBudgetAllocated())/(float)budget.getExInt1())*100;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + budget.getFinYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getBudgetAllocated(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getExInt1(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" +budget.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(bgt), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					try {
						ttlBugtPer = bgt + ttlBugtPer;
						avgPerOnBugt = ttlBugtPer / index;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);

				document.add(new Paragraph("\n"));
				// System.out.println("Ttl Bugt %-----" + ttlBugtPer);
				// System.out.println("Avg Bugt %-----" + avgPerOnBugt);
				document.add(new Paragraph("Average % of Budget on Infrastructure Augmentation : " + decimalFormat.format(avgPerOnBugt)));
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
					// rowData.add("Academic Year");

					rowData.add("Financial Year");
					rowData.add("Budget on Infrastructure Augmentation");
					rowData.add("Total Budget Excluding Salary");
					// rowData.add("Institute Name");
					rowData.add("% of Budget on Infrastructure Augmentation");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 0;
					float bgtPer = 0;
					float ttlBgtper = 0;
					float avgBgtPer = 0;
					for (int i = 0; i < budgetList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						try {
							bgtPer = (Float.parseFloat(budgetList.get(i).getBudgetAllocated())/(float)(budgetList.get(i).getExInt1())) * 100;
							
						} catch (Exception e) {
							bgtPer=0;
							System.err.println(e.getMessage());
						}

						cnt = cnt + 1;

						rowData.add("" + (i + 1));
						rowData.add("" + budgetList.get(i).getFinYear());
						rowData.add("" + budgetList.get(i).getBudgetAllocated());
						rowData.add("" + budgetList.get(i).getExInt1());
						// rowData.add("" + budgetList.get(i).getInstituteName());
						rowData.add("" + decimalFormat.format(bgtPer));

						try {
							ttlBgtper = bgtPer + ttlBgtper;
							
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					avgBgtPer = ttlBgtper / cnt;
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = budgetList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year,
								"Average % of Budget on Infrastructure Augmentation : " + decimalFormat.format(avgBgtPer), 'D');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showStudentCompterRatio", method = RequestMethod.POST)
	public void showStudentCompterRatio(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Student-Computer Ratio";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			// String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			// map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			StudCompRatioReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getStudentCompterRatio", map, StudCompRatioReport[].class);
			List<StudCompRatioReport> studCompList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", studCompList);

			Document document = new Document(PageSize.A4);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = studCompList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Computers", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Purchase Date", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Purchase Amount", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Student Utilising", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Usage %", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float studcompratio = 0;

				for (int i = 0; i < studCompList.size(); i++) {
					// System.err.println("I " + i);
					StudCompRatioReport stdCmpRatioList = studCompList.get(i);
					try {
						System.err.println("I------- " + stdCmpRatioList.getNoOfComputers() + "---------"
								+ stdCmpRatioList.getNoOfStudUtilizing());

						studcompratio = (float)stdCmpRatioList.getNoOfComputers() / (float)stdCmpRatioList.getNoOfStudUtilizing();
						studcompratio=roundUp(studcompratio);
						
						System.err.println("Rtio------- " + studcompratio);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + stdCmpRatioList.getNoOfComputers(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stdCmpRatioList.getPurchaseDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stdCmpRatioList.getPurchaseAmt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stdCmpRatioList.getNoOfStudUtilizing(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + stdCmpRatioList.getInstituteName(),
					 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(studcompratio), headFontData));
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
				// document.add(new Paragraph("Academic Year : 2019-20"));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					// rowData.add("Academic Year");

					rowData.add("No. of Computers");
					rowData.add("Purchase Date");
					rowData.add("Purchase Amount");
					rowData.add("No. of Student Utilising");
					rowData.add("Usage %");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					float ratio = 0;
					for (int i = 0; i < studCompList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						try {
							ratio = (float)studCompList.get(i).getNoOfComputers() / (float)studCompList.get(i).getNoOfStudUtilizing();
							ratio=roundUp(ratio);
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + studCompList.get(i).getNoOfComputers());
						rowData.add("" + studCompList.get(i).getPurchaseDate());
						rowData.add("" + studCompList.get(i).getPurchaseAmt());
						rowData.add("" + studCompList.get(i).getNoOfStudUtilizing());
						rowData.add("" + decimalFormat.format(ratio));

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = studCompList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "", "", 'F');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showEContntDevFac", method = RequestMethod.POST)
	public void showEContntDevFac(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : E-Content Development Facilities";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			// String ac_year = request.getParameter("ac_year");
			String eContFacility = request.getParameter("e_contentType");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("eContFacility", eContFacility);
			map.add("instId", instituteId);

			EContntDevFacReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getEContntDevFac", map, EContntDevFacReport[].class);
			List<EContntDevFacReport> eContList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", eContList);

			Document document = new Document(PageSize.A4);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = eContList.get(0).getInstituteName();
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

				/*
				 * hcell = new PdfPCell(new Phrase("E-Content Development Facility",
				 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(new Phrase("Name of E-Content Development Facility", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Video Link", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year Of Establishment", tableHeaderFont));
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

				int index = 0;
				float studcompratio = 0;

				for (int i = 0; i < eContList.size(); i++) {
					// System.err.println("I " + i);
					EContntDevFacReport econt = eContList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					/*
					 * table.addCell(cell); cell = new PdfPCell(new Phrase("" +
					 * econt.geteContentDevFacility(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 */

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + econt.getNameEcontentDevFacility(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + econt.getVideoLink(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + econt.getExVar1(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + stdCmpRatioList.getInstituteName(),
					 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */
				}

				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				// document.add(new Paragraph("Academic Year : 2019-20"));
				document.add(new Paragraph("\n"));
				try {
					document.add(new Paragraph(eContList.get(0).geteContentDevFacility()));
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					// rowData.add("Academic Year");

					// rowData.add("E-Content Development");
					rowData.add("Name of E-Content Development");
					rowData.add("Video Link");
					rowData.add("Year of Establishment");
					rowData.add("");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					float ratio = 0;
					for (int i = 0; i < eContList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						// rowData.add("" + eContList.get(i).geteContentDevFacility());
						rowData.add("" + eContList.get(i).getNameEcontentDevFacility());
						rowData.add("" + eContList.get(i).getVideoLink());
						rowData.add("" + eContList.get(i).getExVar1());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = eContList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						String eCont = eContList.get(0).geteContentDevFacility();
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, eCont, "", 'D');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showInternetConnInfo", method = RequestMethod.POST)
	public void showInternetConnInfo(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Internet Connection Information";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");
			String temp_ac_year = request.getParameter("temp_ac_year");

			map = new LinkedMultiValueMap<>();
			map.add("instId", instituteId);
			map.add("ac_year", ac_year);

			IntrnetConnInfo[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getInternetConnInfo", map, IntrnetConnInfo[].class);
			List<IntrnetConnInfo> intrntInfoList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", intrntInfoList);

			Document document = new Document(PageSize.A4);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = intrntInfoList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Total Bandwidth(Leased Line)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Bandwidth for Library Accessing E-Resources", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < intrntInfoList.size(); i++) {
					// System.err.println("I " + i);
					IntrnetConnInfo intrnt = intrntInfoList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + intrnt.getLeaseLineBandwidth(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + intrnt.getLibBandwidth(), headFontData));
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
				// document.add(new Paragraph("Academic Year : 2019-20"));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

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
					// rowData.add("Academic Year");

					// rowData.add("E-Content Development");
					rowData.add("Total Bandwidth(Leased Line)");
					rowData.add("Bandwidth for Library Abd E-Resources");
					rowData.add("");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					float ratio = 0;
					for (int i = 0; i < intrntInfoList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						// rowData.add("" + eContList.get(i).geteContentDevFacility());
						rowData.add("" + intrntInfoList.get(i).getLeaseLineBandwidth());
						rowData.add("" + intrntInfoList.get(i).getLibBandwidth());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = intrntInfoList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year :" + temp_ac_year, "", 'C');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showExpndPhyAcdSupprtFacilities", method = RequestMethod.POST)
	public void showExpndPhyAcdSupprtFacilities(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Infrastructure and Learning Resources : Expenditure on Physical & Academic Support Facilities";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			// String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("instId", instituteId);

			ExpndturOnPhysclAcademicSupprt[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getExpndPhyAcdSupprtFacilities", map, ExpndturOnPhysclAcademicSupprt[].class);
			List<ExpndturOnPhysclAcademicSupprt> expndList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", expndList);

			Document document = new Document(PageSize.A4);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = expndList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Financial Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("Expenditure on Physical & Academic Support Facilities", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total Expenditure excluding Salary", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("% of Expenditure on Physical & Academic Support Facilities", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float expdPer = 0;

				for (int i = 0; i < expndList.size(); i++) {
					// System.err.println("I " + i);
					ExpndturOnPhysclAcademicSupprt expd = expndList.get(i);
					try {
						expdPer = ((float)expd.getBudgetAllocated()/(float)expd.getTtlExpd()) * 100 ;
						expdPer=roundUp(expdPer);
					} catch (Exception e) {
						System.err.println("Dividr By Zero : " + e.getMessage());
					}
					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + expd.getFinYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + expd.getBudgetAllocated(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + expd.getTtlExpd(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + expdPer, headFontData));
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
				// document.add(new Paragraph("Academic Year : 2019-20"));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					// rowData.add("Academic Year");

					// rowData.add("E-Content Development");
					rowData.add("Financial Year");
					rowData.add("Expenditure on Physical & Academic Support Facilities");
					rowData.add("Total Expenditure excluding Salary");
					rowData.add("% of Expenditure on Physical & Academic Support Facilities");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					float expndPer = 0;
					for (int i = 0; i < expndList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						expndPer = 0;
						try {
						expndPer = ((float)expndList.get(i).getBudgetAllocated() / (float)expndList.get(i).getTtlExpd())*100;
						expndPer=roundUp(expndPer);
						}catch (Exception e) {
							expndPer=0;// TODO: handle exception
						}
						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + expndList.get(i).getFinYear());
						rowData.add("" + expndList.get(i).getBudgetAllocated());
						rowData.add("" + expndList.get(i).getTtlExpd());
						rowData.add("" + expndPer);

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = expndList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "", "", 'E');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showExpndGreenInitveWsteMgmt", method = RequestMethod.POST)
	public void showExpndGreenInitveWsteMgmt(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : Expenditure on Green Initiatives & Waste Management";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			ExpndGreenInitveWsteMgmt[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getExpndGreenInitveWsteMgmt", map, ExpndGreenInitveWsteMgmt[].class);
			List<ExpndGreenInitveWsteMgmt> expndGrnList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", expndGrnList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = expndGrnList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Financial Year", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Expenditure on Green Initiatives & Waste Management Excluding Salary",
						tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("Annual Expenditure on Green Initiatives & Waste Management Excluding Salary",
								tableHeaderFont));
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

				hcell = new PdfPCell(
						new Phrase("% of Expenditure on Green Initiatives & Waste Management", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 1;
				float bgt = 0;
				float ttlBugtPer = 0;
				float avgPerOnBugt = 0;

				for (int i = 0; i < expndGrnList.size(); i++) {
					// System.err.println("I " + i);
					ExpndGreenInitveWsteMgmt budget = expndGrnList.get(i);
					try {
						bgt = ((float)budget.getBudgetAllocated() / (float)budget.getTtlExpnd())* 100 ;

					} catch (Exception e) {
						System.err.println(e.getMessage());
					}

					
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + budget.getFinYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getBudgetAllocated(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + budget.getTtlExpnd(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" +budget.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + decimalFormat.format(bgt), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					try {
						ttlBugtPer = bgt + ttlBugtPer;
						
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					index++;
				}
				avgPerOnBugt = ttlBugtPer / index;
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);

				document.add(new Paragraph("\n"));
				// System.out.println("Ttl Bugt %-----" + ttlBugtPer);
				// System.out.println("Avg Bugt %-----" + avgPerOnBugt);
				document.add(new Paragraph("Average % of Green Initiatives & Waste Management : " + decimalFormat.format(avgPerOnBugt)));
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
					// rowData.add("Academic Year");

					rowData.add("Financial Year");
					rowData.add("Expenditure on Green Initiatives & Waste Management Excluding Salary");
					rowData.add("Annual Expenditure on Green Initiatives & Waste Management Excluding Salary");
					// rowData.add("Institute Name");
					rowData.add("% of Expenditure on Green Initiatives & Waste Management");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					float bgtPer = 0;
					float ttlBgtper = 0;
					float avgBgtPer = 0;
					for (int i = 0; i < expndGrnList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						try {
							bgtPer = ((float)expndGrnList.get(i).getBudgetAllocated() / (float)expndGrnList.get(i).getTtlExpnd())*100;
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + expndGrnList.get(i).getFinYear());
						rowData.add("" + expndGrnList.get(i).getBudgetAllocated());
						rowData.add("" + expndGrnList.get(i).getTtlExpnd());
						// rowData.add("" + budgetList.get(i).getInstituteName());
						rowData.add("" + decimalFormat.format(bgtPer));

						try {
							ttlBgtper = bgtPer + ttlBgtper;
							
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					avgBgtPer = ttlBgtper / cnt;
					// System.out.println("AVG Budget = " + avgBgtPer);
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = expndGrnList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year,
								"Average % of of Green Initiatives & Waste Management : " + decimalFormat.format(avgBgtPer), 'D');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showInitivAddrsLoctnAdvDisadv", method = RequestMethod.POST)
	public void showInitivAddrsLoctnAdvDisadv(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices :  Initiative to Address Locational Advantages & Disadvantages";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearId", ac_year);
			map.add("instId", instituteId);

			InitivAddrsLoctnAdvDisadv[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getInitivAddrsLoctnAdvDisadv", map, InitivAddrsLoctnAdvDisadv[].class);
			List<InitivAddrsLoctnAdvDisadv> initivAdrsLocAdvDisadvList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", initivAdrsLocAdvDisadvList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = initivAdrsLocAdvDisadvList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Name of Initiative", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Beneficiary of Initiative", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < initivAdrsLocAdvDisadvList.size(); i++) {
					// System.err.println("I " + i);
					InitivAddrsLoctnAdvDisadv adv = initivAdrsLocAdvDisadvList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + adv.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + adv.getNameOfInitiatives(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + adv.getNoStudPart(), headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					rowData.add("Academic Year");
					rowData.add("Name of Initiative");
					rowData.add("Beneficiary of initiative");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					for (int i = 0; i < initivAdrsLocAdvDisadvList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + initivAdrsLocAdvDisadvList.get(i).getAcademicYear());
						rowData.add("" + initivAdrsLocAdvDisadvList.get(i).getInstituteName());
						rowData.add("" + initivAdrsLocAdvDisadvList.get(i).getNoStudPart());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = initivAdrsLocAdvDisadvList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}

						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'D');
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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showNoInitivAddrsLoctnAdvDisadv", method = RequestMethod.POST)
	public void showNoInitivAddrsLoctnAdvDisadv(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Institutional Values and Best Practices : No. of Initiative to Address Locational Advantages & Disadvantages";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			// String temp_ac_year = request.getParameter("temp_ac_year");
			// String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("instId", instituteId);

			NoInitivAddrsLoctnAdvDisadv[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getNoInitivAddrsLoctnAdvDisadv", map, NoInitivAddrsLoctnAdvDisadv[].class);
			List<NoInitivAddrsLoctnAdvDisadv> initiativeList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", initiativeList);

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
				headingName = initiativeList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Total No. of Initiatives", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < initiativeList.size(); i++) {
					// System.err.println("I " + i);
					NoInitivAddrsLoctnAdvDisadv stud = initiativeList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getTtlInitives(), headFontData));
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
				// document.add(new Paragraph("\n"));
				// document.add(new Paragraph("Academic Year : " + temp_ac_year));

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
					rowData.add("Total No. of Initiatives");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					int str = 0;
					for (int i = 0; i < initiativeList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + initiativeList.get(i).getAcademicYear());
						rowData.add("" + initiativeList.get(i).getTtlInitives());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = initiativeList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "", "", 'C');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Institutional Values and Best Practices : No of Initiative to Address Locational Advantages & Disadvantages";

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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	/*
	 * @RequestMapping(value = "/showHumanValuProfEthics", method =
	 * RequestMethod.POST) public void showHumanValuProfEthics(HttpServletRequest
	 * request, HttpServletResponse response) {
	 * 
	 * String reportName =
	 * "Institutional Values and Best Practices : Human Values & Professional Ethics"
	 * ;
	 * 
	 * ModelAndView model = null; try {
	 * 
	 * model = new ModelAndView("report/ratio_report1");
	 * 
	 * HttpSession session = request.getSession(); // String temp_ac_year =
	 * request.getParameter("temp_ac_year"); // String ac_year =
	 * request.getParameter("ac_year"); int instituteId = (int)
	 * session.getAttribute("instituteId");
	 * 
	 * map = new LinkedMultiValueMap<>(); map.add("instId", instituteId);
	 * 
	 * NoInitivAddrsLoctnAdvDisadv[] resArray = rest.postForObject(Constants.url +
	 * "/getNoInitivAddrsLoctnAdvDisadv", map, NoInitivAddrsLoctnAdvDisadv[].class);
	 * List<NoInitivAddrsLoctnAdvDisadv> initiativeList = new
	 * ArrayList<>(Arrays.asList(resArray));
	 * 
	 * model.addObject("list", initiativeList);
	 * 
	 * Document document = new Document(PageSize.A4); // 50, 45, 50, 60
	 * document.setMargins(Constants.marginLeft, Constants.marginRight,
	 * Constants.marginTop, Constants.marginBottom);
	 * document.setMarginMirroring(false);
	 * 
	 * String FILE_PATH = Constants.REPORT_SAVE; File file = new File(FILE_PATH);
	 * 
	 * PdfWriter writer = null;
	 * 
	 * FileOutputStream out = new FileOutputStream(FILE_PATH); try { writer =
	 * PdfWriter.getInstance(document, out); } catch (DocumentException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * String header = ""; String title = "                 ";
	 * 
	 * DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy"); String headingName =
	 * null; try { headingName = initiativeList.get(0).getInstituteName(); } catch
	 * (Exception e) {
	 * 
	 * headingName = "-";
	 * 
	 * } ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);
	 * 
	 * writer.setPageEvent(event);
	 * 
	 * PdfPTable table = new PdfPTable(3);
	 * 
	 * table.setHeaderRows(1);
	 * 
	 * try { table.setWidthPercentage(100); table.setWidths(new float[] { 2.4f,
	 * 3.2f, 3.2f });
	 * 
	 * Font headFontData = Constants.headFontData;// new
	 * Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, // BaseColor.BLACK); Font
	 * tableHeaderFont = Constants.tableHeaderFont; // new
	 * Font(FontFamily.HELVETICA, 12, Font.BOLD, // BaseColor.BLACK);
	 * tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);
	 * 
	 * PdfPCell hcell = new PdfPCell();
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Total No. of Initiatives",
	 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * 
	 * int index = 0;
	 * 
	 * for (int i = 0; i < initiativeList.size(); i++) { // System.err.println("I "
	 * + i); NoInitivAddrsLoctnAdvDisadv stud = initiativeList.get(i);
	 * 
	 * index++; PdfPCell cell; cell = new PdfPCell(new Phrase(String.valueOf(index),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + stud.getAcademicYear(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); //
	 * cell.setPaddingLeft(10);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + stud.getTtlInitives(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * }
	 * 
	 * document.open(); Font reportNameFont = Constants.reportNameFont;// new
	 * Font(FontFamily.TIMES_ROMAN, 14.0f, // Font.UNDERLINE, BaseColor.BLACK);
	 * 
	 * Paragraph name = new Paragraph(reportName, reportNameFont);
	 * name.setAlignment(Element.ALIGN_CENTER); document.add(name);
	 * //document.add(new Paragraph("\n")); //document.add(new
	 * Paragraph("Academic Year : " + temp_ac_year));
	 * 
	 * DateFormat DF = new SimpleDateFormat("dd-MM-yyyy"); document.add(new
	 * Paragraph("\n")); document.add(table);
	 * 
	 * int totalPages = writer.getPageNumber();
	 * 
	 * //System.out.println("Page no " + totalPages);
	 * 
	 * document.close(); int p = Integer.parseInt(request.getParameter("p"));
	 * System.err.println("p " + p);
	 * 
	 * if (p == 1) {
	 * 
	 * if (file != null) {
	 * 
	 * String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	 * 
	 * if (mimeType == null) {
	 * 
	 * mimeType = "application/pdf";
	 * 
	 * }
	 * 
	 * response.setContentType(mimeType);
	 * 
	 * response.addHeader("content-disposition",
	 * String.format("inline; filename=\"%s\"", file.getName()));
	 * 
	 * response.setContentLength((int) file.length());
	 * 
	 * InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 * 
	 * try { FileCopyUtils.copy(inputStream, response.getOutputStream()); } catch
	 * (IOException e) { //System.out.println("Excep in Opening a Pdf File");
	 * e.printStackTrace(); } } } else {
	 * 
	 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
	 * 
	 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
	 * ArrayList<String>();
	 * 
	 * rowData.add("Sr. No"); rowData.add("Academic Year");
	 * rowData.add("Total No. of Initiatives");
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * int cnt = 1; int str = 0; for (int i = 0; i < initiativeList.size(); i++) {
	 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
	 * 
	 * cnt = cnt + i;
	 * 
	 * rowData.add("" + (i + 1));
	 * 
	 * rowData.add("" + initiativeList.get(i).getAcademicYear()); rowData.add("" +
	 * initiativeList.get(i).getTtlInitives());
	 * 
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * }
	 * 
	 * XSSFWorkbook wb = null; try {
	 * 
	 * //System.out.println("Excel List :" + exportToExcelList.toString()); String
	 * rep = null; try { rep = initiativeList.get(0).getInstituteName(); } catch
	 * (Exception e) {
	 * 
	 * rep = "-";
	 * 
	 * } System.err.println("headingName  " + headingName); // String excelName =
	 * (String) session.getAttribute("excelName"); wb =
	 * ExceUtil.createWorkbook(exportToExcelList, rep, reportName,"", "",'C' );
	 * ExceUtil.autoSizeColumns(wb, 3);
	 * response.setContentType("application/vnd.ms-excel"); String date = new
	 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 * response.setHeader("Content-disposition", "attachment; filename=" +
	 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
	 * 
	 * } catch (IOException ioe) { throw new
	 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
	 * if (wb != null) { wb.close(); } }
	 * 
	 * }
	 * 
	 * } catch (DocumentException ex) {
	 * 
	 * //System.out.println("Pdf Generation Error: " + ex.getMessage());
	 * 
	 * ex.printStackTrace();
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.println("Exce in showratioReport " + e.getMessage());
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 */

	@RequestMapping(value = "/showNoOfLinkages", method = RequestMethod.POST)
	public void showNoOfLinkages(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research, Innovation and Extension : No. of Linkages";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);
			NoOfLinkages[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "/getNoOfLinkages", map,
					NoOfLinkages[].class);
			List<NoOfLinkages> linkgList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", linkgList);

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
				headingName = linkgList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Title of Linkages", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Agency with", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Nature of Linkage", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Years of", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < linkgList.size(); i++) {
					// System.err.println("I " + i);
					NoOfLinkages stud = linkgList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getLinkageTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					// cell.setPaddingLeft(10);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getPartneringInstitute(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getNatureOfLinkage(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + stud.getEstbYear(), headFontData));
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
				// document.add(new Paragraph("\n"));
				// document.add(new Paragraph("Academic Year : " + temp_ac_year));

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
					rowData.add("Title of Linkages");
					rowData.add("No. of Agency with");
					rowData.add("Nature of Linkage");
					rowData.add("Years of");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					int str = 0;
					for (int i = 0; i < linkgList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + linkgList.get(i).getLinkageTitle());
						rowData.add("" + linkgList.get(i).getPartneringInstitute());
						rowData.add("" + linkgList.get(i).getNatureOfLinkage());
						rowData.add("" + linkgList.get(i).getEstbYear());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = linkgList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Research Innovation and Extension : No of Linkages";

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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	/*
	 * @RequestMapping(value = "/showFunctnlMou", method = RequestMethod.POST)
	 * public void showFunctnlMou(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * String reportName =
	 * "Research, Innovation and Extension : Functional MoU's R&D";
	 * 
	 * ModelAndView model = null; try {
	 * 
	 * model = new ModelAndView("report/ratio_report1");
	 * 
	 * HttpSession session = request.getSession();
	 * 
	 * String temp_ac_year = request.getParameter("temp_ac_year"); String ac_year =
	 * request.getParameter("ac_year"); int instituteId = (int)
	 * session.getAttribute("instituteId");
	 * 
	 * map = new LinkedMultiValueMap<>(); map.add("acYearList", ac_year);
	 * map.add("instId", instituteId);
	 * 
	 * FunctionalMou[] resArray =
	 * Constants.getRestTemplate().postForObject(Constants.url + "/getFunctnlMou",
	 * map, FunctionalMou[].class); List<FunctionalMou> mouList = new
	 * ArrayList<>(Arrays.asList(resArray));
	 * 
	 * model.addObject("list", mouList);
	 * 
	 * Document document = new Document(PageSize.A4);
	 * 
	 * document.setMargins(Constants.marginLeft, Constants.marginRight,
	 * Constants.marginTop, Constants.marginBottom);
	 * document.setMarginMirroring(false);
	 * 
	 * String FILE_PATH = Constants.REPORT_SAVE; File file = new File(FILE_PATH);
	 * 
	 * PdfWriter writer = null;
	 * 
	 * FileOutputStream out = new FileOutputStream(FILE_PATH); try { writer =
	 * PdfWriter.getInstance(document, out); } catch (DocumentException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * String header = ""; String title = "";
	 * 
	 * DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy"); String headingName =
	 * null; try { headingName = mouList.get(0).getInstituteName(); } catch
	 * (Exception e) {
	 * 
	 * headingName = "-";
	 * 
	 * } ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);
	 * 
	 * writer.setPageEvent(event);
	 * 
	 * PdfPTable table = new PdfPTable(6);
	 * 
	 * table.setHeaderRows(1);
	 * 
	 * try { table.setWidthPercentage(100); table.setWidths(new float[] { 2.4f,
	 * 3.2f, 3.2f, 3.2f, 3.2f, 3.2f});
	 * 
	 * Font headFontData = Constants.headFontData;// new
	 * Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, // BaseColor.BLACK); Font
	 * tableHeaderFont = Constants.tableHeaderFont; // new
	 * Font(FontFamily.HELVETICA, 12, Font.BOLD, // BaseColor.BLACK);
	 * tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);
	 * 
	 * PdfPCell hcell = new PdfPCell();
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("Academic Year)", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("MoU Signed with Organization",
	 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("From Date", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("To Date", tableHeaderFont));
	 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * hcell = new PdfPCell(new Phrase("No. of Benificiary of MoU",
	 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
	 * 
	 * table.addCell(hcell);
	 * 
	 * int index = 0;
	 * 
	 * 
	 * for (int i = 0; i < mouList.size(); i++) { // System.err.println("I " + i);
	 * FunctionalMou mou = mouList.get(i);
	 * 
	 * 
	 * index++; PdfPCell cell; cell = new PdfPCell(new Phrase(String.valueOf(index),
	 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + mou.getAcademicYear(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + mou.getOrgName(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + mou.getDurFromdt(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + mou.getDurTodt(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * 
	 * table.addCell(cell);
	 * 
	 * cell = new PdfPCell(new Phrase("" + mou.getNo_benif(), headFontData));
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * table.addCell(cell);
	 * 
	 * }
	 * 
	 * document.open(); Font reportNameFont = Constants.reportNameFont;// new
	 * Font(FontFamily.TIMES_ROMAN, 14.0f, // Font.UNDERLINE, BaseColor.BLACK);
	 * 
	 * Paragraph name = new Paragraph(reportName, reportNameFont);
	 * name.setAlignment(Element.ALIGN_CENTER); document.add(name);
	 * 
	 * document.add(new Paragraph("\n")); document.add(new
	 * Paragraph("Academic Year : " + temp_ac_year));
	 * 
	 * DateFormat DF = new SimpleDateFormat("dd-MM-yyyy"); document.add(new
	 * Paragraph("\n")); document.add(table);
	 * 
	 * int totalPages = writer.getPageNumber();
	 * 
	 * //System.out.println("Page no " + totalPages);
	 * 
	 * document.close(); int p = Integer.parseInt(request.getParameter("p"));
	 * System.err.println("p " + p);
	 * 
	 * if (p == 1) {
	 * 
	 * if (file != null) {
	 * 
	 * String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	 * 
	 * if (mimeType == null) {
	 * 
	 * mimeType = "application/pdf";
	 * 
	 * }
	 * 
	 * response.setContentType(mimeType);
	 * 
	 * response.addHeader("content-disposition",
	 * String.format("inline; filename=\"%s\"", file.getName()));
	 * 
	 * response.setContentLength((int) file.length());
	 * 
	 * InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 * 
	 * try { FileCopyUtils.copy(inputStream, response.getOutputStream()); } catch
	 * (IOException e) { //System.out.println("Excep in Opening a Pdf File");
	 * e.printStackTrace(); } } } else {
	 * 
	 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
	 * 
	 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
	 * ArrayList<String>();
	 * 
	 * rowData.add("Sr. No"); // rowData.add("Academic Year");
	 * 
	 * // rowData.add("E-Content Development"); rowData.add("Academic Year");
	 * rowData.add("MoU Signed with Organization"); rowData.add("From Date");
	 * rowData.add("To Date"); rowData.add("No. Benificiary of MoU");
	 * 
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * int cnt = 1;
	 * 
	 * for (int i = 0; i < mouList.size(); i++) { expoExcel = new ExportToExcel();
	 * rowData = new ArrayList<String>(); cnt = cnt + i;
	 * 
	 * rowData.add("" + (i + 1)); rowData.add("" +
	 * mouList.get(i).getAcademicYear()); rowData.add("" +
	 * mouList.get(i).getOrgName()); rowData.add("" +
	 * mouList.get(i).getDurFromdt()); rowData.add("" +
	 * mouList.get(i).getDurTodt()); rowData.add("" + mouList.get(i).getNo_benif());
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * }
	 * 
	 * XSSFWorkbook wb = null; try {
	 * 
	 * //System.out.println("Excel List :" + exportToExcelList.toString()); String
	 * rep = null; try { rep = mouList.get(0).getInstituteName(); } catch (Exception
	 * e) {
	 * 
	 * rep = "-";
	 * 
	 * } System.err.println("headingName  " + headingName); // String excelName =
	 * (String) session.getAttribute("excelName"); wb =
	 * ExceUtil.createWorkbook(exportToExcelList, rep,
	 * reportName,"Academic Year : "+temp_ac_year, "",'F');
	 * ExceUtil.autoSizeColumns(wb, 3);
	 * response.setContentType("application/vnd.ms-excel"); String date = new
	 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 * response.setHeader("Content-disposition", "attachment; filename=" +
	 * reportName + "-" + date + ".xlsx"); wb.write(response.getOutputStream());
	 * 
	 * } catch (IOException ioe) { throw new
	 * RuntimeException("Error writing spreadsheet to output stream"); } finally {
	 * if (wb != null) { wb.close(); } }
	 * 
	 * }
	 * 
	 * } catch (DocumentException ex) {
	 * 
	 * //System.out.println("Pdf Generation Error: " + ex.getMessage());
	 * 
	 * ex.printStackTrace();
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.println("Exce in showratioReport " + e.getMessage());
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 */
	@RequestMapping(value = "/showFunctnlMou", method = RequestMethod.POST)
	public void showFunctnlMou(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research, Innovation and Extension : Functional MoU's R&D";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			FunctionalMou[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "/getFunctnlMou", map,
					FunctionalMou[].class);
			List<FunctionalMou> mouList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", mouList);

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
			String title = "";

			DateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");
			String headingName = null;
			try {
				headingName = mouList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Mou Signed with Organization", tableHeaderFont));
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

				hcell = new PdfPCell(new Phrase("No. of Benificiary", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);
				int index = 0;

				for (int i = 0; i < mouList.size(); i++) {
					// System.err.println("I " + i);
					FunctionalMou mou = mouList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + mou.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + mou.getOrgName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + mou.getDurFromdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + mou.getDurTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + mou.getNo_benif(), headFontData));
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
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
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
					rowData.add("Academic Year");
					rowData.add("MoU Signed with Organization");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("No. Benificiary of MoU");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < mouList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));
						rowData.add("" + mouList.get(i).getAcademicYear());
						rowData.add("" + mouList.get(i).getOrgName());
						rowData.add("" + mouList.get(i).getDurFromdt());
						rowData.add("" + mouList.get(i).getDurTodt());
						rowData.add("" + mouList.get(i).getNo_benif());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}
					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = mouList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, "", 'F');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Research-Innovation and Extension : Functional MoUs R&D";

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

			System.err.println("Exce in showratioReport " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showAwardRecog", method = RequestMethod.POST)
	public void showAwardRecog(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research, Innovation and Extension : Award/Recognition for Extension Activity";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();

			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			AwrdRecgAgnstExtActivityReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getAwardRecog", map, AwrdRecgAgnstExtActivityReport[].class);
			List<AwrdRecgAgnstExtActivityReport> awrdList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", awrdList);

			Document document = new Document(PageSize.A4);

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
				headingName = awrdList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Name Extension Activity", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Awarding/Recognizing Agency - Authority", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Years Of Awards", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < awrdList.size(); i++) {
					// System.err.println("I " + i);
					AwrdRecgAgnstExtActivityReport awrd = awrdList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getActName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getNameAwardingBody(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getAwardYear(), headFontData));
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
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

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
					rowData.add("Name Extension Activity");
					rowData.add("Awarding/Recognizing Agency - Authority");
					rowData.add("Years Of Awards");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;
					int str = 0;
					for (int i = 0; i < awrdList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + awrdList.get(i).getActName());
						rowData.add("" + awrdList.get(i).getNameAwardingBody());
						rowData.add("" + awrdList.get(i).getAwardYear());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = awrdList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, " ", 'C');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						
						reportName = "Research Innovation and Extension : Award-Recognition for Extension Activity";

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

			System.err.println("Exce in showAwardRecog " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showNoAwardRecogExtAct", method = RequestMethod.POST)
	public void showNoAwardRecogExtAct(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research, Innovation and Extension : No. Award/Recognition for Extension Activity";

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

			NoAwardRecogExtAct[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getNoAwardRecogExtAct", map, NoAwardRecogExtAct[].class);
			List<NoAwardRecogExtAct> noAwrdList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", noAwrdList);

			BufferedOutputStream outStream = null;
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
				headingName = noAwrdList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Year of Award", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of  Award/Recognition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < noAwrdList.size(); i++) {
					// System.err.println("I " + i);
					NoAwardRecogExtAct awrd = noAwrdList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getAwardYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getNoAward(), headFontData));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Year of Award");
					rowData.add("No. of  Award/Recognition");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < noAwrdList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + noAwrdList.get(i).getAwardYear());
						rowData.add("" + noAwrdList.get(i).getNoAward());

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
						reportName = "Research Innovation and Extension : No Award-Recognition for Extension Activity";

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

			System.err.println("Exce in showNoAwardRecogExtAct " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showIntelPropRght", method = RequestMethod.POST)
	public void showIntelPropRght(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research, Innovation and Extension : Intellectual Property Rights and Industry Institute Initiatives";

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

			IntelectulPropRightReport[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getIntelPropRght", map, IntelectulPropRightReport[].class);
			List<IntelectulPropRightReport> intelPropList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", intelPropList);

			BufferedOutputStream outStream = null;
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
				headingName = intelPropList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Title of Seminar/Workshops on Ipr & III", tableHeaderFont));
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

				int index = 0;
				for (int i = 0; i < intelPropList.size(); i++) {
					// System.err.println("I " + i);
					IntelectulPropRightReport prop = intelPropList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prop.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prop.getConName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prop.getConFromdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prop.getConTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + prop.getConPcount(), headFontData));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("Title of Seminar on IPR & III");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("No. of Participants");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < intelPropList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + intelPropList.get(i).getAcademicYear());
						rowData.add("" + intelPropList.get(i).getConName());
						rowData.add("" + intelPropList.get(i).getConFromdt());
						rowData.add("" + intelPropList.get(i).getConTodt());
						rowData.add("" + intelPropList.get(i).getConPcount());

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
						reportName = "Research Innovation and Extension : Intellectual Property Rights and Industry Institute Initiatives";

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

			System.err.println("Exce in showIntelPropRght " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showAvgPerPlacement", method = RequestMethod.POST)
	public void showAvgPerPlacement(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression : Avg. % of Placement: (Last Five Years)";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String prg_name = request.getParameter("prog_name");

			map = new LinkedMultiValueMap<>();

			map.add("programId", prg_name);
			Program progrm = new Program();
			try {
				progrm = Constants.getRestTemplate().postForObject(Constants.url + "/getProgramByProgramId", map,
						Program.class);
			} catch (Exception e) {
				progrm = new Program();
			}

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();

			map.add("prgName", prg_name);
			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			AvgPerPlacement[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "getAvgPerPlacement",
					map, AvgPerPlacement[].class);
			List<AvgPerPlacement> studPlaceList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", studPlaceList);

			BufferedOutputStream outStream = null;
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
				headingName = studPlaceList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(9);

			table.setHeaderRows(1);

			try {
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

				hcell = new PdfPCell(new Phrase("No. of Out going Students", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("No. of Student Placed", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Employer(Company/Industry/Organization)", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Address of Employer", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Contact Details", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Min. Package", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Max. Package", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				float perPlacmntPreYr = 0;
				float AvgPlcmntFiveYr = 0;
				float ttlStudPlaced = 0;
				float ttlstudPassed = 0;
				int index = 0;
				for (int i = 0; i < studPlaceList.size(); i++) {

					AvgPerPlacement studPlace = studPlaceList.get(i);

					// perPlacmntPreYr =
					// (studPlace.getNoStudentPlaced()/studPlace.getNoStudPass())*100;
					ttlStudPlaced = studPlace.getNoStudentPlaced() + ttlStudPlaced;
					ttlstudPassed = studPlace.getNoStudPass() + ttlstudPassed;

					// AvgPlcmntFiveYr = perPlacmntPreYr/5;

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getNoStudPass(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getNoStudentPlaced(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getEmpyrName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getEmpyrAdd(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getContactDetail(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getMinPackage(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + studPlace.getMaxPackage(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);
				}

				// System.out.println("Add="+ttlStudPlaced);
				// System.out.println("VVV"+ttlstudPassed+" / "+ttlstudPassed);

				perPlacmntPreYr = (ttlStudPlaced / ttlstudPassed) * 100;
				// System.out.println("perPlacmntPreYr "+perPlacmntPreYr);
				AvgPlcmntFiveYr = perPlacmntPreYr / 5;

				// System.out.println("AVG %="+perPlacmntPreYr+" "+AvgPlcmntFiveYr);

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
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("For Academic Year :" + temp_ac_year + ""));

				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Programe Type :" + progrm.getNameOfProgram() + ""));
				document.add(new Paragraph("\n"));
				document.add(table);

				if (temp_ac_year.equals("Last Five Years")) {
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("Avg. % of Placement for Last Five Year :" + decimalFormat.format(AvgPlcmntFiveYr) + ""));
				} else {
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("% of Placement Per Year :" + decimalFormat.format(perPlacmntPreYr) + ""));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("No. of Out going Students");
					rowData.add("No. of Student Placed");
					rowData.add("Name of Employer(Company/Industry/Organization)");
					rowData.add("Address of Employer");
					rowData.add("Contact Details");
					rowData.add("Min. Package");
					rowData.add("Max. Package");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;

					for (int i = 0; i < studPlaceList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + studPlaceList.get(i).getAcademicYear());
						rowData.add("" + studPlaceList.get(i).getNoStudPass());
						rowData.add("" + studPlaceList.get(i).getNoStudentPlaced());
						rowData.add("" + studPlaceList.get(i).getEmpyrName());
						rowData.add("" + studPlaceList.get(i).getEmpyrAdd());
						rowData.add("" + studPlaceList.get(i).getContactDetail());
						rowData.add("" + studPlaceList.get(i).getMinPackage());
						rowData.add("" + studPlaceList.get(i).getMaxPackage());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						// wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
						// "Academic Year :" + temp_ac_year + " ");

						String preYear = "% of Placement Pre Year : " + String.valueOf(perPlacmntPreYr);
						String plcmntLastFivYear = "Avg. % of Placement for Last Five Year :"
								+ String.valueOf(AvgPlcmntFiveYr);
						System.err.println(perPlacmntPreYr + " HERE " + AvgPlcmntFiveYr);

						if (temp_ac_year.equals("Last Five Years")) {
							wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
									"Academic Year:" + temp_ac_year + "\n Programe Type : " + progrm.getNameOfProgram(),
									plcmntLastFivYear, 'I');
						} else {
							wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
									"Academic Year:" + temp_ac_year + "\n Programe Type : " + progrm.getNameOfProgram(),
									preYear, 'I');
						}
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Student Support and Progression : Avg Placement: (Last Five Years)";

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

				// System.out.println("Pdf Generation Error: " + ex.getMessage())
				ex.printStackTrace();

			}

		} catch (Exception e) {

			System.err.println("Exce in showAvgPerPlacement " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showStudProgression", method = RequestMethod.POST)
	public void showStudProgression(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression  : % of Students Progression ( Higher Education ): (Current YearData)";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYear", ac_year);
			map.add("instId", instituteId);

			StudProgression[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getStudProgression", map, StudProgression[].class);
			List<StudProgression> studProgList = new ArrayList<>(Arrays.asList(resArray));
			// System.out.println("List : "+studProgList);
			model.addObject("list", studProgList);

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
				headingName = studProgList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				/*
				 * table.setWidthPercentage(100); table.setWidths(new float[] { 2.4f, 3.2f,
				 * 3.2f, 3.2f, 3.2f });
				 * 
				 * Font headFontData = Constants.headFontData;// new
				 * Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, // BaseColor.BLACK); Font
				 * tableHeaderFont = Constants.tableHeaderFont; // new
				 * Font(FontFamily.HELVETICA, 12, Font.BOLD, // BaseColor.BLACK);
				 * tableHeaderFont.setColor(Constants.tableHeaderFontBaseColor);
				 * 
				 * PdfPCell hcell = new PdfPCell();
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("Academic Year", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("No. of Differently Abled Student",
				 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("Total No. of Student Enrolled",
				 * tableHeaderFont)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 * 
				 * 
				 * hcell = new PdfPCell(new Phrase("Institute Name", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 * 
				 * 
				 * hcell = new PdfPCell(new Phrase("Year Wise %", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				int index = 0;
				double studPass = 0;
				double noStud = 0;
				double studProg = 0;

				for (int i = 0; i < studProgList.size(); i++) {
					// System.err.println("I " + i);
					StudProgression stud = studProgList.get(i);
					try {
						studPass = stud.getNoStudPass();
						noStud = stud.getNoStudent();
						studProg = (noStud / studPass) * 100;

						// studProg = (stud.getNoStudent()/stud.getNoStudPass())*100;

					} catch (Exception e) {
						System.err.println("Invalid Values---" + e.getMessage());
					}
					index++;
					PdfPCell cell;
					/*
					 * cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * 
					 * table.addCell(cell);
					 * 
					 * cell = new PdfPCell(new Phrase("" + stud.getAcademicYear(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); //
					 * cell.setPaddingLeft(10);
					 * 
					 * table.addCell(cell);
					 * 
					 * cell = new PdfPCell(new Phrase("" + stud.getNoOfPwdStud(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 * 
					 * cell = new PdfPCell(new Phrase("" + stud.getTotalStudEnrolled(),
					 * headFontData)); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 * 
					 * 
					 * cell = new PdfPCell(new Phrase("" + stud.getInstituteName(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 * 
					 * 
					 * cell = new PdfPCell(new Phrase("" + studPer, headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 */

				}
				// System.out.println("No Stud------------------="+noStud);
				// System.out.println("Stud Pass------------------="+studPass);
				// System.out.println("% Per------------------="+studProg);
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + studProgList.get(0).getAcademicYear()));
				// document.add(new Paragraph("Institute " +
				// ratioList.get(0).getInstituteName()));
				/*
				 * Paragraph company = new Paragraph("Customer Wise Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Students Progression % Per Year : " + decimalFormat.format(studProg)));
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

					/*
					 * rowData.add("Sr. No"); rowData.add("Academic Year");
					 * rowData.add("No. of Differently Abled Student");
					 * rowData.add("Total No. of Student Enrolled"); rowData.add("Institute Name");
					 * rowData.add("Year Wise %");
					 */

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < studProgList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						/*
						 * str = studList.get(i).getNoOfPwdStud() * 100 /
						 * studList.get(i).getTotalStudEnrolled(); cnt = cnt + i;
						 * 
						 * rowData.add("" + (i + 1));
						 * 
						 * rowData.add("" + studList.get(i).getAcademicYear()); rowData.add("" +
						 * studList.get(i).getNoOfPwdStud()); rowData.add("" +
						 * studList.get(i).getTotalStudEnrolled()); rowData.add("" +
						 * studList.get(i).getInstituteName()); rowData.add("" + str);
						 */

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = studProgList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						String summry = "Students Progression % Per Year" + String.valueOf(studProg);
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + studProgList.get(0).getAcademicYear(), summry, 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Student Support and Progression:Per of Students Progression (Higher Education)-(Current YearData)";

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

			System.err.println("Exce in showStudProgression " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showTeacherAwardRecognitn", method = RequestMethod.POST)
	public void showTeacherAwardRecognitn(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression  : Teachers Recognition/Awards and Incentives Information";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYear", ac_year);

			TeacherAwardRecognitn[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getTeacherAwardRecognitn", map, TeacherAwardRecognitn[].class);
			List<TeacherAwardRecognitn> techrAwrdList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", techrAwrdList);

			BufferedOutputStream outStream = null;
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
				headingName = techrAwrdList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(7);

			table.setHeaderRows(1);

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

				hcell = new PdfPCell(new Phrase("Name of Teacher/Awardee", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Address/Contact No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Award/Recognition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Awarding Agency ", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Year of Award/Recognition", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Incentive Details", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				for (int i = 0; i < techrAwrdList.size(); i++) {
					// System.err.println("I " + i);
					TeacherAwardRecognitn awrd = techrAwrdList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getFacultyFirstName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getContactNo(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getAwardName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getAwardAuthority(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getAwardDate(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + awrd.getIncentive(), headFontData));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Name of Teacher/Awardee");
					rowData.add("Address/Contact No.");
					rowData.add("Name of Award/Recognition");
					rowData.add("Name of Awarding Agency");
					rowData.add("Year of Award/Recognition");
					rowData.add("Incentive Details");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < techrAwrdList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + techrAwrdList.get(i).getFacultyFirstName());
						rowData.add("" + techrAwrdList.get(i).getContactNo());
						rowData.add("" + techrAwrdList.get(i).getAwardName());
						rowData.add("" + techrAwrdList.get(i).getAwardAuthority());
						rowData.add("" + techrAwrdList.get(i).getAwardDate());
						rowData.add("" + techrAwrdList.get(i).getIncentive());

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
						reportName = "Student Support and Progression: Teachers Recognition-Awards and Incentives Information";

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

			System.err.println("Exce in showTeacherAwardRecognitn " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showTechrResrchPaprJournlInfo", method = RequestMethod.POST)
	public void showTechrResrchPaprJournlInfo(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression  : Teacher Research Paper/Journal Information";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYear", ac_year);

			TechrResrchPaprJournlInfo[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getTechrResrchPaprJournlInfo", map, TechrResrchPaprJournlInfo[].class);
			List<TechrResrchPaprJournlInfo> resrchInfoList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", resrchInfoList);

			BufferedOutputStream outStream = null;
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
				headingName = resrchInfoList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

			PdfPTable table = new PdfPTable(7);

			table.setHeaderRows(1);

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

				hcell = new PdfPCell(new Phrase("Name of Author/Co-Author", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Department of Authors", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Title of Paper", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Journal", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Academic Year of Publication", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("If UGC/SCOPUS/DIO Recognized", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				String flag = null;
				for (int i = 0; i < resrchInfoList.size(); i++) {
					// System.err.println("I " + i);
					TechrResrchPaprJournlInfo resrch = resrchInfoList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + resrch.getFacultyFirstName() + " " + resrch.getCoAuthor(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + resrch.getDeptName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + resrch.getTitle(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + resrch.getJournalName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + resrch.getPublicationYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					if (resrch.getJournalType() == 0) {
						flag = "Yes";
					} else {
						flag = "No";
					}

					cell = new PdfPCell(new Phrase("" + flag, headFontData));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Name of Author/Co-Author");
					rowData.add("Department of Authors");
					rowData.add("Title of Paper");
					rowData.add("Name of Journal");
					rowData.add("Academic Year of Publication");
					rowData.add("If UGC/SCOPUS/DIO Recognized");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < resrchInfoList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + resrchInfoList.get(i).getFacultyFirstName() + " "
								+ resrchInfoList.get(i).getCoAuthor());
						rowData.add("" + resrchInfoList.get(i).getDeptName());
						rowData.add("" + resrchInfoList.get(i).getTitle());
						rowData.add("" + resrchInfoList.get(i).getJournalName());
						rowData.add("" + resrchInfoList.get(i).getPublicationYear());
						rowData.add("" + flag);

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
						reportName = "Student Support and Progression:Teacher Research Paper-Journal Information";

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

			System.err.println("Exce in showTechrResrchPaprJournlInfo " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showTechrResrchPaprJournlRatio", method = RequestMethod.POST)
	public void showTechrResrchPaprJournlRatio(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression  : Teacher Research Paper/Journal Information";

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

			TechrResrchPaprJournlRatio[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getTechrResrchPaprJournlRatio", map, TechrResrchPaprJournlRatio[].class);
			List<TechrResrchPaprJournlRatio> resrchRatioList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", resrchRatioList);

			BufferedOutputStream outStream = null;
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
				headingName = resrchRatioList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Publication in UGC/SCOPUS/DIO Journals", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total No. of Full Time Teachers in AY", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float ttlTechr = 0;
				float avgTech = 0;
				float ttlPublcatn = 0;
				float resrchPprPerTechr = 0;
				String flag = null;
				for (int i = 0; i < resrchRatioList.size(); i++) {
					// System.err.println("I " + i);
					TechrResrchPaprJournlRatio ratio = resrchRatioList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getPublishedInUgc(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + ratio.getFullTimeTeacher(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					ttlPublcatn = ratio.getPublishedInUgc() + ttlPublcatn;
					ttlTechr = ratio.getFullTimeTeacher() + ttlTechr;

				}

				// System.out.println("Totl Teacher----------"+ttlTechr);
				avgTech = ttlTechr / 5;
				// System.out.println("Avg Last yr------------"+avgTech);
				// System.out.println("TTlNo Publication-----"+ttlPublcatn);
				resrchPprPerTechr = ttlPublcatn / avgTech;
				// System.out.println("Research Paper Per Teacher-------"+resrchPprPerTechr);
				;
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

				document.add(new Paragraph("\n"));
				if (temp_ac_year.equals("Last Five Years")) {
					document.add(new Paragraph("No. of Research Papers per Teacher :" + decimalFormat.format(resrchPprPerTechr) + ""));
					document.add(new Paragraph("\n"));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("Academic Year");
					rowData.add("No. of Publication in UGC/SCOPUS/DIO Journals");
					rowData.add("Total No. of Full Time Teachers in AY");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < resrchRatioList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + resrchRatioList.get(i).getAcademicYear());
						rowData.add("" + resrchRatioList.get(i).getPublishedInUgc());
						rowData.add("" + resrchRatioList.get(i).getFullTimeTeacher());

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());

						// String excelName = (String) session.getAttribute("excelName");
						// wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
						// "Academic Year :" + temp_ac_year + " ");

						if (temp_ac_year.equals("Last Five Years")) {
							String resrchPaper = "No. of Research Papers per Teacher :"
									+ String.valueOf(decimalFormat.format(resrchPprPerTechr));

							wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
									"Academic Year:" + temp_ac_year + " ", resrchPaper, 'F');
						} else {
							String resrchPaper = String.valueOf(decimalFormat.format(resrchPprPerTechr));
							wb = ExceUtil.createWorkbook(exportToExcelList, headingName, reportName,
									"Academic Year:" + temp_ac_year + " ", "", 'F');
						}
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Student Support and Progression:Teacher Research Paper-Journal Information";

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

			System.err.println("Exce in showTechrResrchPaprJournlRatio " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showResrchProjectGrants", method = RequestMethod.POST)
	public void showResrchProjectGrants(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Student Support and Progression  : Research Project Grants";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("instId", instituteId);
			map.add("acYear", ac_year);

			ResrchProjectGrants[] resArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getResrchProjectGrants", map, ResrchProjectGrants[].class);
			List<ResrchProjectGrants> resrchGrantList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", resrchGrantList);

			BufferedOutputStream outStream = null;
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
				headingName = resrchGrantList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);
			// writer.add(new Paragraph("Curricular Aspects"));

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
				hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);

				hcell = new PdfPCell(new Phrase("Sr.No.", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Project", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Name of Investigator", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Dept. of Investigator", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Grant Sanction", tableHeaderFont));
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

				hcell = new PdfPCell(new Phrase("Sponsoring Authority", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				String flag = null;
				for (int i = 0; i < resrchGrantList.size(); i++) {
					// System.err.println("I " + i);
					ResrchProjectGrants grant = resrchGrantList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getProjName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getProjInvName(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getProjInvDept(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getProjGrant(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getProjFrdt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					cell = new PdfPCell(new Phrase("" + grant.getProjTodt(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + grant.getProj_sponsor(), headFontData));
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
					rowData.add("Name of Project");
					rowData.add("Name of Investigator");
					rowData.add("Dept. of Investigator");
					rowData.add("Grant Sanction");
					rowData.add("From Date");
					rowData.add("To Date");
					rowData.add("Sponsoring Authority");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < resrchGrantList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + resrchGrantList.get(i).getProjName());
						rowData.add("" + resrchGrantList.get(i).getProjInvName());
						rowData.add("" + resrchGrantList.get(i).getProjInvDept());
						rowData.add("" + resrchGrantList.get(i).getProjGrant());
						rowData.add("" + resrchGrantList.get(i).getProjFrdt());
						rowData.add("" + resrchGrantList.get(i).getProjTodt());
						rowData.add("" + resrchGrantList.get(i).getProj_sponsor());

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

			System.err.println("Exce in showResrchProjectGrants " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showFullTimeTechrInstResrchGuide", method = RequestMethod.POST)
	public void showFullTimeTechrInstResrchGuide(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Research, Innovation and Extension : No. of Full Time Teachers in the Institute as Research Guide";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);

			map.add("acYear", ac_year);

			FullTimeTechrInstResrchGuide[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getFullTimeTechrInstResrchGuide", map, FullTimeTechrInstResrchGuide[].class);
			List<FullTimeTechrInstResrchGuide> rsrchGuidList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", rsrchGuidList);

			BufferedOutputStream outStream = null;
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
				headingName = rsrchGuidList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("No. of Full Time Teachers", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Research Guide Recognitions to Full Time Teacher", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("% of Research Guide", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float perResrchGuide = 0;

				for (int i = 0; i < rsrchGuidList.size(); i++) {
					// System.err.println("I " + i);
					FullTimeTechrInstResrchGuide guide = rsrchGuidList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + guide.getFullTimeTeacher(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + guide.getNoResearchGuide(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					perResrchGuide = (guide.getNoResearchGuide() / guide.getFullTimeTeacher()) * 100;

					cell = new PdfPCell(new Phrase("" + perResrchGuide, headFontData));
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

					List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

					ExportToExcel expoExcel = new ExportToExcel();
					List<String> rowData = new ArrayList<String>();

					rowData.add("Sr. No");
					rowData.add("No. of Full Time Teacher");
					rowData.add("No. of Research Guide Recognitions to Full Time Teacher");
					rowData.add("% of Research Guide");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < rsrchGuidList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						rowData.add("" + rsrchGuidList.get(i).getFullTimeTeacher());
						rowData.add("" + rsrchGuidList.get(i).getNoResearchGuide());
						rowData.add("" + perResrchGuide);

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
						reportName = "Research-Innovation and Extension : No of Full Time Teachers as Research Guide";

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

			System.err.println("Exce in showIntelPropRght " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showPerNewCource", method = RequestMethod.POST)
	public void showPerNewCource(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : Percentage(%) of New	Courses Introduced";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = "Last Five Years";// request.getParameter("temp_ac_year");
			String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			map.add("acYearList", "-5");
			map.add("instId", instituteId);

			PerNewCource[] resArray = Constants.getRestTemplate().postForObject(Constants.url + "/getPerNewCource", map,
					PerNewCource[].class);
			List<PerNewCource> lastFiveYrList = new ArrayList<>(Arrays.asList(resArray));
			// System.out.println("List : "+lastFiveYrList);
			model.addObject("list", lastFiveYrList);

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
				headingName = lastFiveYrList.get(0).getInstituteName();
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				int index = 0;
				float noCourse = 0;
				float ttlNoCourse = 0;
				float perCourseIntro = 0;

				for (int i = 0; i < lastFiveYrList.size(); i++) {

					try {
						noCourse = lastFiveYrList.get(0).getNoCoursesInLast5(); // ---------------Courses of Last 5
																				// Years
						ttlNoCourse = lastFiveYrList.get(1).getNoCoursesInLast5(); // ---------------Total No. of

						perCourseIntro = (noCourse * 100) / ttlNoCourse;
						perCourseIntro = roundUp(perCourseIntro);
					} catch (Exception e) {
						System.err.println("Invalid Values---" + e.getMessage());
					}
					index++;
					PdfPCell cell;

				}

				// System.out.println("No Course------------------="+noCourse);
				// System.out.println("Totla Course------------------="+ttlNoCourse);
				// System.out.println("% Per------------------="+perCourseIntro);
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Academic Year : " + temp_ac_year));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Percentage(%) of New Courses Introduced : " + perCourseIntro));
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

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < lastFiveYrList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = lastFiveYrList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						String summry = "Percentage(%) of New Courses Introduced : " + String.valueOf(perCourseIntro);
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName,
								"Academic Year : " + temp_ac_year, summry, 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Curricular Aspects:Percentage of New	Courses Introduced";

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

			System.err.println("Exce in showPerNewCource " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showPerProgCbseElecticwCourse", method = RequestMethod.POST)
	public void showPerProgCbseElecticwCourse(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects : Percentage(%) of Programs with CBCS/Elective courses";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/ratio_report1");

			HttpSession session = request.getSession();
			String temp_ac_year = request.getParameter("temp_ac_year");
			// String ac_year = request.getParameter("ac_year");
			int instituteId = (int) session.getAttribute("instituteId");

			map = new LinkedMultiValueMap<>();
			// map.add("acYearList", ac_year);
			map.add("instId", instituteId);

			PerProgCbseElectiveCourse[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "/getPerProgCbseElectiveCourse", map, PerProgCbseElectiveCourse[].class);
			List<PerProgCbseElectiveCourse> courseList = new ArrayList<>(Arrays.asList(resArray));
			// System.out.println("List : "+courseList);
			model.addObject("list", courseList);

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
				if (courseList.get(0).getInstituteName().equals(null)) {
					headingName = courseList.get(1).getInstituteName();
				} else {
					headingName = courseList.get(0).getInstituteName();
				}
			} catch (Exception e) {

				headingName = "-";

			}
			ItextPageEvent event = new ItextPageEvent(header, title, "", headingName);

			writer.setPageEvent(event);

			PdfPTable table = new PdfPTable(5);

			table.setHeaderRows(1);

			try {
				int index = 0;
				float noProgm = 0;
				float ttlNoProgm = 0;
				float perProgmCourse = 0;

				for (int i = 0; i < courseList.size(); i++) {

					try {
						noProgm = courseList.get(0).getCount1(); // ---------------No. of Programs with CBSE/Elective
																	// courses implemented.
						ttlNoProgm = courseList.get(1).getCount1(); // ---------------Total No. of Program offered.
						perProgmCourse = (noProgm * 100) / ttlNoProgm;

					} catch (Exception e) {
						System.err.println("Invalid Values---" + e.getMessage());
					}
					index++;
					PdfPCell cell;

				}

				// System.out.println("No Course------------------="+noProgm);
				// System.out.println("Totle Course Offered------------------="+ttlNoProgm);
				// System.out.println("% Per------------------="+perProgmCourse);
				document.open();
				Font reportNameFont = Constants.reportNameFont;// new Font(FontFamily.TIMES_ROMAN, 14.0f,
																// Font.UNDERLINE, BaseColor.BLACK);

				Paragraph name = new Paragraph(reportName, reportNameFont);
				name.setAlignment(Element.ALIGN_CENTER);
				document.add(name);
				// document.add(new Paragraph("\n"));
				// document.add(new Paragraph("Academic Year : " + temp_ac_year));

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				document.add(new Paragraph("\n"));
				document.add(table);
				document.add(new Paragraph("\n"));
				document.add(new Paragraph("Percentage(%) of Programs with CBSE/Elective Courses : " + perProgmCourse));
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

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

					int cnt = 1;

					for (int i = 0; i < courseList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);

					}

					XSSFWorkbook wb = null;
					try {

						// System.out.println("Excel List :" + exportToExcelList.toString());
						String rep = null;
						try {
							rep = courseList.get(0).getInstituteName();
						} catch (Exception e) {

							rep = "-";

						}
						System.err.println("headingName  " + headingName);
						// String excelName = (String) session.getAttribute("excelName");
						String summry = "Percentage(%) of Programs with CBSE/Elective Courses : "
								+ String.valueOf(perProgmCourse);
						wb = ExceUtil.createWorkbook(exportToExcelList, rep, reportName, "", summry, 'E');
						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Curricular Aspects:Percentage of Programs with CBCS-Elective courses";

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

			System.err.println("Exce in showPerProgCbseElecticwCourse " + e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/showFildeProjectInternReport", method = RequestMethod.POST)
	public void showFildeProjectInternReport(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects :  Fields Project/Internships";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			int prog_name = Integer.parseInt(request.getParameter("prog_name"));

			map = new LinkedMultiValueMap<>();
			map.add("programId", prog_name);
			Program progrm = Constants.getRestTemplate().postForObject(Constants.url + "/getProgramByProgramId", map,
					Program.class);

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("prog_name", prog_name);
			map.add("acYear", ac_year);

			FildeProjectInternReport[] resArray = Constants.getRestTemplate().postForObject(
					Constants.url + "getFildeProjectInternReport", map, FildeProjectInternReport[].class);
			List<FildeProjectInternReport> internList = new ArrayList<>(Arrays.asList(resArray));

			model.addObject("list", internList);

			BufferedOutputStream outStream = null;
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
				headingName = internList.get(0).getInstituteName();
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

				/*
				 * hcell = new PdfPCell(new Phrase("Program Name", tableHeaderFont));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(Constants.baseColorTableHeader);
				 * 
				 * table.addCell(hcell);
				 */

				hcell = new PdfPCell(
						new Phrase("Provision for Undertaking Field Projects/Internship", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(
						new Phrase("No. of Students Undertaking Field Projects/Internship Code", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Link to the Relevant Documents", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				float perResrchGuide = 0;

				for (int i = 0; i < internList.size(); i++) {
					// System.err.println("I " + i);
					FildeProjectInternReport intern = internList.get(i);

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					/*
					 * cell = new PdfPCell(new Phrase("" + intern.getProgramType(), headFontData));
					 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * 
					 * table.addCell(cell);
					 */

					cell = new PdfPCell(new Phrase("" + intern.getProvisionForUndertaking(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + intern.getNoOfStudUndertaking(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + intern.getDocument(), headFontData));
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

				document.add(new Paragraph("Program Type : " + progrm.getNameOfProgram()));
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
					/* rowData.add("Program Name"); */
					/* rowData.add("Type of Program"); */
					rowData.add("Provision for Undertaking Field Projects/Internship");
					rowData.add("No. of Students Undertaking Field Projects/Internship Code");
					rowData.add("Link to the Relevant Documents");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					for (int i = 0; i < internList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						rowData.add("" + (i + 1));

						/* rowData.add("" + internList.get(i).getProgramType()); */
						rowData.add("" + internList.get(i).getProvisionForUndertaking());
						rowData.add("" + internList.get(i).getNoOfStudUndertaking());
						rowData.add("" + internList.get(i).getDocument());

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
								"Academic Year:" + temp_ac_year + "\n Programe Type : " + progrm.getNameOfProgram(), "",
								'F');

						ExceUtil.autoSizeColumns(wb, 3);
						response.setContentType("application/vnd.ms-excel");
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						reportName = "Curricular Aspects:Fields Project-Internships";

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

			System.err.println("Exce in showIntelPropRght " + e.getMessage());
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/showFBReceivedFrmStakeHolder", method = RequestMethod.POST)
	public void showFBReceivedFrmStakeHolder(HttpServletRequest request, HttpServletResponse response) {

		String reportName = "Curricular Aspects :  Feedback Received from Stakeholders";

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/prog_report1");

			String ac_year = request.getParameter("ac_year");
			String temp_ac_year = request.getParameter("temp_ac_year");
			int prog_name = Integer.parseInt(request.getParameter("prog_name"));

			HttpSession session = request.getSession();

			int instituteId = (int) session.getAttribute("instituteId");
			map = new LinkedMultiValueMap<>();

			map.add("instId", instituteId);
			map.add("acYearList", ac_year);

			InstStakeholderFeedbackReport[] feedBackiStkHldrYesNo = Constants.getRestTemplate().postForObject(
					Constants.url + "/getAllFeedBackFrmStackHldr", map, InstStakeholderFeedbackReport[].class);
			List<InstStakeholderFeedbackReport> fbList = new ArrayList<>(Arrays.asList(feedBackiStkHldrYesNo));

			BufferedOutputStream outStream = null;
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
				headingName = fbList.get(0).getInstituteName();
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

				hcell = new PdfPCell(new Phrase("Feedback From", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Status", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Action", tableHeaderFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(Constants.baseColorTableHeader);

				table.addCell(hcell);

				int index = 0;
				String res = null;
				String fbProces = "NA";
				float perResrchGuide = 0;

				for (int i = 0; i < fbList.size(); i++) {

					InstStakeholderFeedbackReport fedBack = fbList.get(i);

					if (fedBack.getFbYesno() != 0) {
						res = "Yes";
					} else {
						res = "No";
					}

					System.err.println(" what  is " + fedBack.getFbProcess());
					if (fedBack.getFbProcess().equals("A")) {
						fbProces = "Feedback Collected,analyzed and action taken and feedback available on websites";
					} else if (fedBack.getFbProcess().equals("B")) {
						fbProces = "Collected,analyzed and action has been taken";
					} else if (fedBack.getFbProcess().equals("C")) {
						fbProces = "Feedback Collected and Analyzed";
					} else if (fedBack.getFbProcess().equals("D")) {
						fbProces = "Feedback Collected";
					} else if (fedBack.getFbProcess().equals("NA")) {

						fbProces = "NA";
					}

					index++;
					PdfPCell cell;
					cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fedBack.getAcademicYear(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fedBack.getFeedbackFrom(), headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + res, headFontData));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + fbProces, headFontData));
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

					rowData.add("Sr. No.");
					rowData.add("Academic Year");
					rowData.add("Feedback From");
					rowData.add("Status");
					rowData.add("Action");

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
					int cnt = 1;
					String yesNo = null;
					String fedBkPrcs = null;
					for (int i = 0; i < fbList.size(); i++) {
						expoExcel = new ExportToExcel();
						rowData = new ArrayList<String>();
						cnt = cnt + i;

						if (fbList.get(i).getFbYesno() != 0) {
							yesNo = "Yes";
						} else {
							yesNo = "No";
						}

						if (fbList.get(i).getFbProcess().equals("A")) {
							fbProces = "Feedback Collected,analyzed and action taken and feedback available on websites";
						}

						else if (fbList.get(i).getFbProcess().equals("B")) {
							fbProces = "Collected,analyzed and action has been taken";
						} else if (fbList.get(i).getFbProcess().equals("C")) {
							fbProces = "Feedback Collected and Analyzed";
						} else if (fbList.get(i).getFbProcess().equals("D")) {
							fbProces = "Feedback Collected";
						} else if (fbList.get(i).getFbProcess().equals("NA")) {
							fbProces = "NA";
						}

						rowData.add("" + (i + 1));
						rowData.add("" + fbList.get(i).getAcademicYear());
						rowData.add("" + fbList.get(i).getFeedbackFrom());
						rowData.add("" + yesNo);
						rowData.add("" + fbProces);

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
								"Academic Year:" + temp_ac_year, "", 'F');

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

			System.err.println("Exce in showFBReceivedFrmStakeHolder " + e.getMessage());
			e.printStackTrace();

		}

	}

	private float roundUp(float inputValue) {
		BigDecimal bd = new BigDecimal(inputValue).setScale(2, RoundingMode.HALF_UP);
		return inputValue = bd.floatValue();
	}

}
