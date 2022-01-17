package util.scheduled;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.DateTimeUtil;

@Component
public class Tasks {

	protected static Logger LOGGER = LoggerFactory.getLogger(Tasks.class);

	public Tasks() {
	}

//		 "0 0 * * * *" = the top of every hour of every day.
//		 "*/10 * * * * *" = every ten seconds.
//		 "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//		 "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
//		 "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
//		 "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//		 "0 0 0 25 12 ?" = every Christmas Day at midnight
//		
//		Cron expression is represented by six fields:
//		second, minute, hour, day of month, month, day(s) of week


//	@Scheduled(fixedRate = 86400000) // --> 24 saatte 1
//	@Scheduled(cron = "0 6 14 * * *") // --> 14:06
	@Scheduled(cron = "0 0 6 * * *") // --> 06:00
	public void yazdir() {
		System.out.println("[" + DateTimeUtil.getDateString(new Date(), "dd/MM/yyyy HH:mm:ss") + "]'da çalıştım");
	}

}