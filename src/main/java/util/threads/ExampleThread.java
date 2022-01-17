package util.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleThread extends Thread {
	protected static Logger LOGGER = LoggerFactory.getLogger(ExampleThread.class);

	public ExampleThread() {
		super();
	}

	public void run() {
		LOGGER.info("THREAD BASLADI.");
		int x = 0;
		while (x++ < 10) {
			System.out.println("CANDAN");
		}
		LOGGER.info("THREAD BITTI.");
	}
}