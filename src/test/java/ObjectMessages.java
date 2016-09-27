import model.Address;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

public class ObjectMessages
{
	private static final Logger logger = LogManager.getLogger(ProducerConsumerTest.class);
	private Connection connection;


	@Before
	public void initialize() throws JMSException
	{

		logger.info("Starting client .. ");

		// Creating connection factory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");

		connectionFactory.setTrustAllPackages(true);


		// Creating and starting connection
		connection = connectionFactory.createConnection();
		connection.start();

	}

	@Test
	public void testProducer()
	{
		try
		{
			//Creating session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Specifying destination for producer (Queue/topic)
			Destination destination = session.createQueue("location-request-queue");

			//Creating producer
			MessageProducer producer = session.createProducer(destination);

			//Sending Text Message to queue
			Address add1 = new Address("Shashtri road", "Lokmanya nagar", 440001);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(add1);
			producer.send(destination, objectMessage);
			logger.info("Message sent : "+  add1 );

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
		try
		{
			//Creating session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Specifying destination for producer (Queue/topic)
			Destination destination = session.createQueue("location-request-queue");

			//Creating consumer
			MessageConsumer consumer = session.createConsumer(destination);

			//Receiving Text Message from queue
			Message receivedMsg = consumer.receive(2000);

			if (receivedMsg instanceof ObjectMessage)
			{
				ObjectMessage objectMessage = (ObjectMessage) receivedMsg;
				Address obj =  (Address)objectMessage.getObject();
				logger.info("Received: " + obj);

			}else
			{
				logger.info("Received: "+receivedMsg);
			}

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
