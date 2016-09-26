import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;


public class ActiveMQUtils
{
	private ActiveMQConnectionFactory connectionFactory;
	private Connection                connection;
	private Session                   session;

	public Session getSession() throws JMSException
	{
		//Setup connection factory
		connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		//Create connection
		connection = connectionFactory.createConnection();
		connection.start();

		//Create Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		return session;
	}
}
