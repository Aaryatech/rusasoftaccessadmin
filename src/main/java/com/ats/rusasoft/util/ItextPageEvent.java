package com.ats.rusasoft.util;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItextPageEvent extends PdfPageEventHelper {

	private PdfTemplate t;
	private Image total;
	private String StrHeader, strTitle, strDate, footerText;

	public void onOpenDocument(PdfWriter writer, Document document) {
		t = writer.getDirectContent().createTemplate(30, 16);
		try {
			total = Image.getInstance(t);
			total.setRole(PdfName.ARTIFACT);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	public ItextPageEvent(String header, String title, String date, String footer) {
		super();
		this.StrHeader = header;
		this.strTitle = title;
		this.strDate = date;
		this.footerText = footer;

	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		//ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Hello"), 30, 800, 0);
		
		PdfContentByte pdfContByte=writer.getDirectContent();
		try {
			pdfContByte.setFontAndSize(BaseFont.createFont(), 10);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ColumnText.showTextAligned(writer.getDirectContent().setFontAndSize(BaseColor.LIGHT_GRAY, 10), Element.ALIGN_RIGHT, new Phrase(""+footerText), 550, 800,
				
				//0);
		Phrase p=new Phrase();
		//p.setFont(Font.FontStyle.OBLIQUE);
		
		FontSelector selector = new FontSelector();
		Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
		f1.setColor(BaseColor.LIGHT_GRAY);
		//Font f2 = FontFactory.getFont("MSung-Light",
		     //   "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
		//f2.setColor(BaseColor.RED);
		selector.addFont(f1);
		//selector.addFont(f2);
		Phrase ph = selector.process(footerText);
	ColumnText.showTextAligned(pdfContByte, Element.ALIGN_RIGHT, ph, 550, 800,
				
				0);
	
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		addHeader(writer);
		addFooter(writer);
	}

	private void addHeader(PdfWriter writer) {
		PdfPTable header = new PdfPTable(1);
		try {
			// set defaults
			header.setWidths(new int[] { 24 });
			header.setTotalWidth(527);
			header.setLockedWidth(true);
			header.getDefaultCell().setFixedHeight(40);
			header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			header.getDefaultCell().setBorderColor(BaseColor.BLACK);
			header.getDefaultCell().setBackgroundColor(BaseColor.PINK);

			// add text
			PdfPCell text = new PdfPCell();
			// text.setPaddingBottom(15);
			// text.setPaddingLeft(10);
			text.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			text.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			text.setBorder(Rectangle.NO_BORDER);
			text.addElement(new Phrase(StrHeader, new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.BOLD, BaseColor.BLUE)));
			// text.addElement(new Phrase("\n "+strTitle +" Institute : "+strDate, new
			// Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK)));
			text.addElement(new Phrase("\n        " + strTitle,
					new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.LIGHT_GRAY)));
			// PdfPCell text2 = new PdfPCell();
			// text2.addElement(new Phrase("\n "+strTitle , new
			// Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK)));

			header.addCell(text);

			// header.addCell(text2);
			// write content

			header.writeSelectedRows(0, -1, 100, 803, writer.getDirectContent());
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	private void addFooter(PdfWriter writer) {
		PdfPTable footer = new PdfPTable(4);
		try {
			// set defaults
			footer.setWidths(new int[] { 5, 2, 1, 5 });
			footer.setTotalWidth(527);
			footer.setLockedWidth(true);

			footer.getDefaultCell().setFixedHeight(20);
			footer.getDefaultCell().setBorder(Rectangle.TOP);
			footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

			// add copyright
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			String timeStamp = dateFormat.format(cal.getTime());
			footer.addCell(new Phrase("" + timeStamp, new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));

			// add current page count
			footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()),
					new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));

			// add placeholder for total page count
			PdfPCell totalPageCount = new PdfPCell(total);
			totalPageCount.setBorder(Rectangle.TOP);
			totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
			footer.addCell(totalPageCount);

			footer.addCell(new Phrase("" , new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));

			// write page
			PdfContentByte canvas = writer.getDirectContent();
			canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
			footer.writeSelectedRows(0, -1, 32, 20, canvas);

			canvas.endMarkedContentSequence();
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		int totalLength = String.valueOf(writer.getPageNumber()).length();
		int totalWidth = totalLength * 5;
		ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
				new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)), totalWidth,
				6, 0);
	}

}
