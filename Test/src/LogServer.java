import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;


public class LogServer {

	/**
	 * 初始化logback
	 * @throws RunServerException 
	 */
	public static void initLogBack() throws Exception{
		ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
		LoggerContext lc = (LoggerContext) iLoggerFactory;
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(CommonUtils.USER_DIR+CommonUtils.SYSTEM_SEPARATE+"config"+CommonUtils.SYSTEM_SEPARATE+"logback.xml");
		} catch (JoranException e) {
			e.printStackTrace();
			throw new Exception("initLogBack exception!");
		}
	}
}
