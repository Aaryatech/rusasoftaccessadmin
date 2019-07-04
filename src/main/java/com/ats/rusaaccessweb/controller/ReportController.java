package com.ats.rusaaccessweb.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
//orig imports
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
 import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
//

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.ExceUtil;
import com.ats.rusaaccessweb.common.ExportToExcel;
import com.ats.rusaaccessweb.model.dashb.InstituteAccredationReport;
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
public class ReportController {
	
	RestTemplate rest = new RestTemplate();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	String curDateTime = dateFormat.format(cal.getTime());
	String redirect = null;

	MultiValueMap<String, Object> map = null;

	@RequestMapping(value = "/showReports", method = RequestMethod.GET)
	public ModelAndView showReports(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("Report/staticReport");
 

		} catch (Exception e) {

			System.err.println("Exce in showReports " + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}
	
	
	@RequestMapping(value = "/showVariousAccreReport", method = RequestMethod.POST)
	public void showVariousAccreReport(HttpServletRequest request, HttpServletResponse response) {
		 

			String reportName = "Institutewise Various Accreditation Status Report";

		 
			try {
 
				InstituteAccredationReport[] resArray = Constants.getRestTemplate().getForObject(Constants.url + "getInstituteAccreReport", 
						InstituteAccredationReport[].class);
				List<InstituteAccredationReport> progList = new ArrayList<>(Arrays.asList(resArray));
 
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

				 
				ItextPageEvent event = new ItextPageEvent(header, title, "",""  );

				writer.setPageEvent(event);
				// writer.add(new Paragraph("Curricular Aspects"));

				PdfPTable table = new PdfPTable(6);

				table.setHeaderRows(1);

				try {
					table.setWidthPercentage(100);
					table.setWidths(new float[] { 2.4f, 5.0f, 2.4f, 2.4f, 2.4f, 2.4f });
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

					hcell = new PdfPCell(new Phrase("NAAC", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("NBA", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("NIRF", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("THE", tableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(Constants.baseColorTableHeader);

					table.addCell(hcell);

					int index = 0;
					for (int i = 0; i < progList.size(); i++) {
						// System.err.println("I " + i);
						InstituteAccredationReport prog = progList.get(i);

						index++;
						PdfPCell cell;
						cell = new PdfPCell(new Phrase(String.valueOf(index), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getInstituteName(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getNAAC(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getNBA(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getNIRF(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + prog.getTHE(), headFontData));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);

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
						rowData.add("NAAC");
						rowData.add("NBA");
						rowData.add("NIRF");
						rowData.add("THE");

						expoExcel.setRowData(rowData);
						exportToExcelList.add(expoExcel);
						int cnt = 1;
						for (int i = 0; i < progList.size(); i++) {
							expoExcel = new ExportToExcel();
							rowData = new ArrayList<String>();
							cnt = cnt + i;

							rowData.add("" + (i + 1));

							rowData.add("" + progList.get(i).getInstituteName());
							rowData.add("" + progList.get(i).getNAAC());
							rowData.add("" + progList.get(i).getNBA());
							rowData.add("" + progList.get(i).getNIRF());
							rowData.add("" + progList.get(i).getTHE());

							expoExcel.setRowData(rowData);
							exportToExcelList.add(expoExcel);

						}

						XSSFWorkbook wb = null;
						try {
 

							wb = ExceUtil.createWorkbook(exportToExcelList, "", reportName,
									"  ", "", 'F');

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
