package org.prograIII.ProyectoFinalPrograIII;

import umg.principal.EstructurasDeDatos.ColaDeTurnos;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProyectoFinalPrograIiiApplication implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Recv receiver;

	public ProyectoFinalPrograIiiApplication(Recv receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message...");
		rabbitTemplate.convertAndSend(Send.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}
}
