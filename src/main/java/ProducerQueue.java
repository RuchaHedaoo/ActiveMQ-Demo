import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;


/**
 * Created by lg on 23/9/16.
 */
public class ProducerQueue implements Runnable
{
	public static final Logger logger = LogManager.getLogger("ProducerQueue");
	ActiveMQUtils activeMQUtils = new ActiveMQUtils();

	public void run()
	{
		try
		{
			Session session = activeMQUtils.getSession();

			Destination destination = session.createQueue("location-request-queue");

			MessageProducer msgProducer = session.createProducer(destination);

			TextMessage message = session.createTextMessage("Ramnagar");

			msgProducer.send(message);
			logger.info("message sent");

			activeMQUtils.close();

		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}

	}
}
