package util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class DateTimeUtil {

	public static Date getDateClass(String tarih) {
		if (Util.empty(tarih))
			return null;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dateClass = null;

		if (Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("dd/MM/yyyy");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e1) {
				format = new SimpleDateFormat("MM/dd/yyyy");
				try {
					dateClass = (Date) format.parse(tarih);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		} else if (Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("dd-MM-yyyy");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e1) {
				format = new SimpleDateFormat("MM-dd-yyyy");
				try {
					dateClass = (Date) format.parse(tarih);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		} else if (Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("dd.MM.yyyy");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e1) {
				format = new SimpleDateFormat("MM.dd.yyyy");
				try {
					dateClass = (Date) format.parse(tarih);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		if (Pattern.compile("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e1) {
				format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
				try {
					dateClass = (Date) format.parse(tarih);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		} else if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("yyyy-MM-dd");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (Pattern.compile("^\\d{8}$").matcher(tarih).find()) {
			format = new SimpleDateFormat("ddMMyyyy");

			try {
				dateClass = (Date) format.parse(tarih);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return dateClass;
	}

	public static Time getTimeClass(String saat) {
		Time timeClass = null;
		try {
			if (Pattern.compile("^\\d{2}$").matcher(saat).find()) {
				timeClass = Time.valueOf(saat + ":00" + ":00");
			} else if (Pattern.compile("^\\d{2}:\\d{2}$").matcher(saat).find()) {
				timeClass = Time.valueOf(saat + ":00");
			} else if (Pattern.compile("^\\d{2}:\\d{2}:\\d{2}$").matcher(saat).find()) {
				timeClass = Time.valueOf(saat);
			}

		} catch (Exception e) {
		}
		return timeClass;
	}

	public static String getDateString(Date tarih, String pattern) {
		if (tarih == null) {
			return null;
		}
		if (Util.empty(pattern)) {
			pattern = "dd/MM/yyyy";
		}

		String dateString = null;
		try {
			dateString = (new SimpleDateFormat(pattern)).format(tarih);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateString;
	}

	public static String getTimeString(Time saat, String pattern) {
		if (saat == null) {
			return null;
		}
		if (Util.empty(pattern)) {
			pattern = "HH:mm";
		}

		String dateString = null;

		try {
			dateString = (new SimpleDateFormat(pattern)).format(saat).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateString;
	}

	public static String getYear(Date tarih) {
		if (tarih != null) {
			return (new SimpleDateFormat("yyyy")).format(tarih);
		} else {
			return (new SimpleDateFormat("yyyy")).format(new Date());
		}
	}

	public static String getMonth(Date tarih, String locale) {
		if (Util.empty(locale)) {
			locale = "tr";
		}

		if (tarih != null) {
			return (new SimpleDateFormat("MMMM", new Locale(locale))).format(tarih);
		} else {
			return (new SimpleDateFormat("MMMM", new Locale(locale))).format(new Date());
		}
	}

	public static long aradaKacGunVar(Calendar startDate, Calendar endDate) {
	    try {
	    	long end = endDate.getTimeInMillis();
		    long start = startDate.getTimeInMillis();
		    return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	    }
	    catch(Exception e) {
	    	return -1;
	    }
	}

}