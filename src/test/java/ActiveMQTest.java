import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

public class ActiveMQTest
{
	private static final Logger logger = LogManager.getLogger(ActiveMQTest.class);
	private Connection connection;
	private Session session;


	@Test
	public void testActiveMQ() throws JMSException
	{
		logger.info("Starting client .. ");

		// Creating connection factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");

		// Creating and starting connection
		final Connection connection = connectionFactory.createConnection();
		connection.start();

		// Closing connection
		connection.close();
	}

	@Test
	public void testActiveMQReconnection() throws JMSException
	{
		logger.info("Starting client .. ");


		// Creating connection factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://0.0.0.0:61616)");

		// Creating and starting connection
		final Connection connection = connectionFactory.createConnection();
		connection.start();

		int i = 15;

		while (i-- > 0)
		try
		{
			Thread.sleep(2000);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}


		// Closing connection
		connection.close();
	}


}
