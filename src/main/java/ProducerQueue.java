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

	public void run()
	{
		try
		{
			logger.info("In Producer Queue");
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			logger.info("connection Started");

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue("location-request-queue");

			MessageProducer msgProducer = session.createProducer(destination);

			TextMessage message = session.createTextMessage("Ramnagar");

			msgProducer.send(message);
			logger.info("message sent");
			session.close();
			connection.close();

		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}

	}
}
