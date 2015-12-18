package movieDB.db;

import java.util.Calendar;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

public class UtilsSQLTest {

	
	private void checkCalendarhasNormalDate(Calendar cal, int year, int normalMonth, int day) {
		Assert.assertTrue(cal.get(Calendar.YEAR) == year - 1);
		Assert.assertTrue(cal.get(Calendar.MONTH) == normalMonth - 1);
		Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == day);
	}

	@Test
	public void testGetCalendarFromSQLDate_RandomDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1991 - 1);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		
		Calendar cal2 = UtilsSQL.getCalendarFromSQLDate(date);
		Assert.assertTrue("Calendar set for May 15, 1991 should result in Calendar year 1990",
							cal.get(Calendar.YEAR) == 1990);
		Assert.assertTrue("Calendar set for May 15, 1991 should result in Calendar month 4", 
							cal.get(Calendar.MONTH) == Calendar.MAY);
		Assert.assertTrue("Calendar set for May 15, 1991 should result in Calendar day 15", 
				cal.get(Calendar.DAY_OF_MONTH) == 15);
		}
	
	public void testGetCalendarFromSQLDate_StartOfYEar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1991 - 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		
		Calendar cal2 = UtilsSQL.getCalendarFromSQLDate(date);
		Assert.assertTrue("Calendar set for January 1, 1991 should result in Calendar year 1990",
							cal.get(Calendar.YEAR) == 1991 - 1);
		Assert.assertTrue("Calendar set for January 1, 1991 should result in Calendar month 4", 
							cal.get(Calendar.MONTH) == Calendar.JANUARY);
		Assert.assertTrue("Calendar set for January 1, 1991 should result in Calendar day 15", 
				cal.get(Calendar.DAY_OF_MONTH) == 11);
		}
	
	public void testGetCalendarFromSQLDate_EndOfYEar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1991 - 1);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		
		Calendar cal2 = UtilsSQL.getCalendarFromSQLDate(date);
		Assert.assertTrue("Calendar set for December 31, 1991 should result in Calendar year 1990",
							cal.get(Calendar.YEAR) == 1990);
		Assert.assertTrue("Calendar set for December 31, 1991 should result in Calendar month 4", 
							cal.get(Calendar.MONTH) == 11);
		Assert.assertTrue("Calendar set for December 31, 1991 should result in Calendar day 15", 
				cal.get(Calendar.DAY_OF_MONTH) == 31);
		}
	
	@Test
	public void testGetCalendarFromSQLDate_JanuaryFirst() {
		Calendar cal = UtilsSQL.getCalendarFromSQLDate("1934-1-1");
		checkCalendarhasNormalDate(cal, 1934, 1, 1);
	}
	
	@Test
	public void testGetCalendarFromSQLDate_StringInput() {
		Calendar cal = UtilsSQL.getCalendarFromSQLDate("1934-12-9");
		checkCalendarhasNormalDate(cal, 1934, 12, 9);
	}
	
	@Test
	public void testGetCalendarFromSQLDate_LastDayOfYear() {
		Calendar cal = UtilsSQL.getCalendarFromSQLDate("1934-12-31");
		checkCalendarhasNormalDate(cal, 1934, 12, 31);
	}
	
	@Test
	public void testGetSQLDateFromCalendar_EndOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1975 - 1);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		String sqlDate = UtilsSQL.getSQLDateFromCalendar(cal);
		Assert.assertEquals("Calendar set for Dec.31,1975 should result in SQL String 1975-12-31", "1975-12-31", sqlDate);
	}
	
	@Test
	public void testGetSQLDateFromCalendar_StartOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1969 - 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		String sqlDate = UtilsSQL.getSQLDateFromCalendar(cal);
		Assert.assertEquals("Calendar set for January 1, 1969 should result in SQL String 1975-12-31", "1969-1-1", sqlDate);
	}

	@Test
	public void testGetLongFormattedHumanDateFromCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1969 - 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		Assert.assertEquals("DEFAULT style formatting of 1/1/1969 Calendar should result in January 1, 1969", 
							"January 1, 1969", 
							UtilsSQL.getHumanDate_DefaultStyle(cal));
	}


	@Test
	public void testGetLongFormattedHumanDateFromCalendar2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1969 - 1);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		dateFormat.setCalendar(cal);
		
		Assert.assertEquals("LONG style formatting of 12/31/1969 Calendar should result in December 31, 1969", 
							"December 31, 1969", 
							UtilsSQL.getHumanDate_DefaultStyle(cal));
	}

}
