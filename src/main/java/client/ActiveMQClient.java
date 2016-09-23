package client;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public class ActiveMQClient
{

	public static void main(String[] args)

	{

		try
		{
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
			Connection connection = connectionFactory.createConnection();
			connection.start();


		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}

	}


}
