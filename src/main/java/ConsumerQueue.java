import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by lg on 23/9/16.
 */
public class ConsumerQueue
		implements ExceptionListener, Runnable
{
	public static final Logger logger = LogManager.getLogger("ConsumerQueue");
	ActiveMQUtils activeMQUtils = new ActiveMQUtils();

	public void run()
	{
		try
		{
			Session session = activeMQUtils.getSession();

			Destination destination = session.createQueue("location-request-queue");

			MessageConsumer msgConsumer = session.createConsumer(destination);

			Message receivedMsg = msgConsumer.receive();

			System.out.print("Message Received: " + receivedMsg);

			msgConsumer.close();

			activeMQUtils.close();
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
