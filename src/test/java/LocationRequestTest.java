import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;


public class LocationRequestTest
		implements MessageListener
{
	private static final Logger logger = LogManager.getLogger(LocationRequestTest.class);

	private Connection      connection;
	private Session         session;
	private MessageProducer producer;
	private MessageConsumer consumer;

	@Before
	public void initialize() throws JMSException
	{
		logger.info("Starting client .. ");

		// Creating connection factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.220.43:61616");

		// Creating and starting connection
		connection = connectionFactory.createConnection();
		connection.start();

		//Creating session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		//Creating location request queue
		// Destination destination = session.createQueue("location-request-queue-3");
		Destination destination = session.createQueue("gmlc-request-queue-3");

		//Creating producer
		producer = session.createProducer(destination);

		// Creating consumer
		consumer = session.createConsumer(destination);
		consumer.setMessageListener(this);
	}

	@Test
	public void testLocationRequest() throws JMSException
	{
		for (int i = 0; i < 10_000; i++)
		{


			final String requestId = UUID.randomUUID().toString().replace("-", "");
			final String responseQueue = "location-response-queue";

			final StringBuilder requestBuilder = new StringBuilder(100);

		/*
			Request-Id Response-Queue-Name
			<Parameter-Id:Parameter-Value>
			<Parameter-Id:Parameter-Value>
			<Parameter-Id:Parameter-Value>
			<Parameter-Id:Parameter-Value>
		*/

			requestBuilder.append(requestId).append(" ").append(responseQueue)
			              .append("\n").append("1:").append("919923012345")
			              .append("\n").append("2:").append("1");

			final ActiveMQTextMessage message = new ActiveMQTextMessage();
			message.setText(requestBuilder.toString());

			producer.send(message);
		}

		try
		{
			Thread.sleep(100000);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	private volatile long requestCount = 0;
	private volatile long time         = System.currentTimeMillis();

	public void onMessage(Message message)
	{
		TextMessage textMessage = (TextMessage) message;

		try
		{
			requestCount++;

			if (requestCount % 1000 == 0)
			{
				// logger.info("Received : {}", textMessage.getText());

				long t = System.currentTimeMillis() - time;
				logger.info("{}ms to process {} requests @ {}tps", (t), requestCount, (int) (requestCount * 1000 / t));
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}


	@After
	public void cleanup() throws JMSException
	{
		// Closing producer
		producer.close();

		// Closing consumer
		consumer.close();

		// Closing session
		session.close();

		// Closing connection
		connection.close();
	}


}
