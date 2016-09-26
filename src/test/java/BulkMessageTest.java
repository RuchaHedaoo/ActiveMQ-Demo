import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

public class BulkMessageTest
{
	private static final Logger logger = LogManager.getLogger(ProducerConsumerTest.class);
	private Connection connection;


	@Before
	public void initialize() throws JMSException
	{

		logger.info("Starting client .. ");

		// Creating connection factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");

		// Creating and starting connection
		connection = connectionFactory.createConnection();
		connection.start();

	}

	@Test
	public void testProducer()
	{
		int count=0;
		try
		{
			//Creating session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Specifying destination for producer (Queue/topic)
			Destination destination = session.createQueue("location-request-queue");

			//Creating producer
			MessageProducer producer = session.createProducer(destination);

			//Sending Text Message to queue

			for (int i = 0; i < 5; i++)
			{
				count++;
				String txtMsg = UUID.randomUUID().toString();
				TextMessage sentmessage = session.createTextMessage(txtMsg);
				sentmessage.setIntProperty("count", i);
				producer.send(destination, sentmessage);
				logger.info(" "+txtMsg);
			}
			logger.info("Number of sent messages: "+count );


			//Closing connection
			session.close();
			connection.close();


		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void testConsumer()
	{
		int count=0;
		try
		{
			//Creating session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Specifying destination for producer (Queue/topic)
			Destination destination = session.createQueue("location-request-queue");

			//Creating consumer
			MessageConsumer consumer = session.createConsumer(destination);

			//Receiving Text Message from queue

			for (int i = 0; i < 5; i++)
			{

				Message receivedMsg = consumer.receive(2000);
				count++;

				if (receivedMsg instanceof TextMessage)
				{
					TextMessage textMessage = (TextMessage) receivedMsg;
					String text = textMessage.getText();
					logger.info("Received: " + text);

				}
				else
				{
					logger.info("Received: " + receivedMsg);
				}
			}
			logger.info("Number of received messages: "+count );
			Assert.assertEquals(5,count);

			//Closing connection
			consumer.close();
			session.close();
			connection.close();

		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}


}
