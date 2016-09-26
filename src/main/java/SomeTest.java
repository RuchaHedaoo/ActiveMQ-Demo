/**
 * Created by lg on 23/9/16.
 */
public class SomeTest
{
	public static void main(String[] args)
	{


		Thread threadProducer = new Thread(new ProducerQueue());
		threadProducer.start();

		Thread threadConsumer = new Thread(new ConsumerQueue());
		threadConsumer.start();


	}
}
