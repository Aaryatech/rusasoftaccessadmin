package com.ats.rusaaccessweb.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class DateConvertor {

	public static String convertToYMD(String date) {

		String convertedDate = null;
		try {
			SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");
			Date dmyDate = dmySDF.parse(date);

			convertedDate = ymdSDF.format(dmyDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;

	}

	public static String convertToDMY(String utildate) {

		String convertedDate = null;
		try {
			SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat ymdSDF2 = new SimpleDateFormat("yyyy-mm-dd");

			SimpleDateFormat dmySDF = new SimpleDateFormat("dd-mm-yyyy");

			Date ymdDate = ymdSDF2.parse(utildate);

			convertedDate = dmySDF.format(ymdDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;

	}

	public static java.sql.Date convertToSqlDate(String date) {

		java.sql.Date convertedDate = null;
		try {
			SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");

			Date dmyDate = dmySDF.parse(date);

			// System.out.println("converted util date commons "+dmyDate);

			convertedDate = new java.sql.Date(dmyDate.getTime());
			// System.out.println("converted sql date commons "+convertedDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;

	}

	public static Calendar getTimePlus30Min() {

		int interval = 2;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Calendar cal30 = Calendar.getInstance();

		System.out.println("Time " + String.valueOf(df.format(cal30.getTime())));

		cal30.add(Calendar.MINUTE, interval);
		// return String.valueOf(df.format(cal30.getTime()));
		return cal30;
	}

	public static Calendar getCurTime() {

		int interval = 0;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Calendar curCal = Calendar.getInstance();

		System.out.println("Time " + String.valueOf(df.format(curCal.getTime())));

		curCal.add(Calendar.MINUTE, interval);
		// return String.valueOf(df.format(curCal.getTime()));
		return curCal;

	}

}
