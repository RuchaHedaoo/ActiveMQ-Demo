import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp.Headers.Connect;
import org.apache.logging.log4j.LogManager;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.apache.logging.log4j.*;

/**
 * Created by lg on 23/9/16.
 */
public class ConsumerQueue implements ExceptionListener, Runnable
{
	public static final Logger logger = LogManager.getLogger("ConsumerQueue");

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

			MessageConsumer msgConsumer = session.createConsumer(destination);

			Message receivedMsg = msgConsumer.receive();

			System.out.print("Message Received: " + receivedMsg);

			msgConsumer.close();
			session.close();
			connection.close();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}


	}

	public void onException(JMSException e)
	{

	}
}
