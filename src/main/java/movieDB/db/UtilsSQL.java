package movieDB.db;

import java.sql.Date;
import java.util.Calendar;

public class UtilsSQL {


	public static Calendar getCalendarFromSQLDate(java.sql.Date date) {	
		return getCalendarFromSQLDate(date.toString());
	}

	// takes in a SQL date in the format YYYY-MM-DD and returns a Calendar representing that date
	public static Calendar getCalendarFromSQLDate(String sqlDate) {
		
		String[] splits = sqlDate.split("-");
		Calendar cal = Calendar.getInstance();
		
		// calendars have an offset of 1 for years and months (i.e. January is month 0, Year 1980 is stored in Calendar as 1979)
		cal.set(Calendar.YEAR, new Integer(splits[0]).intValue() - 1);
		cal.set(Calendar.MONTH, new Integer(splits[1]).intValue() - 1);
		cal.set(Calendar.DAY_OF_MONTH, new Integer(splits[2]).intValue());
		return cal;		
	}
	

	public static String getSQLDateFromCalendar(Calendar cal) {
		// calendars have an offset of 1 for years and months (i.e. January is month 0, Year 1980 is stored in Calendar as 1979)
		return (cal.get(Calendar.YEAR)+1) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" 
				+ cal.get(Calendar.DAY_OF_MONTH);
	}
	
	// TODO: 
	// need to use Calendar and DateFormat to reuse pre-existing classes. Figure out why date gives
	// a one-year offset compared to Calendar, when calling cal.getTime();
	// Then code calling this getHumanDate_DefaultStyle method no longer have to call it and can handle
	// the formatting and calendar-setting themselves
	
	public static String getHumanDate_DefaultStyle(Calendar cal) {
		int year = cal.get(Calendar.YEAR) + 1;
		int monthInt = cal.get(Calendar.MONTH);
		int dayOfWeek = cal.get(Calendar.DAY_OF_MONTH);
		
		String month;
		switch(monthInt) {
		case 0: month = "January";
				break;
		case 1: month = "February";
				break;
		case 2: month = "March";
				break;
		case 3: month = "April";
				break;
		case 4: month = "May";
				break;
		case 5: month = "June";
				break;
		case 6: month = "July";
				break;
		case 7: month = "August";
				break;
		case 8: month = "September";
				break;
		case 9: month = "October";
				break;
		case 10: month = "November";
				break;
		case 11: month = "December";
				break;
		default: month = "";
				break;
		}
		return month + " " + dayOfWeek + ", " + year;
	}
}
