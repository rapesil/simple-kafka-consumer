package com.rapesil.kafka.consumer;

import com.rapesil.kafka.consumer.consumer.CarConsumer;
import com.rapesil.kafka.consumer.DTO.CarDTO;
import com.rapesil.kafka.consumer.utils.FileUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Durations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
		partitions = 1,
		brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
		topics = {"embedded-test-topic"})
class ConsumerApplicationTests {

	Logger log = LoggerFactory.getLogger(ConsumerApplicationTests.class);

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private CarConsumer consumer;

	@Value("${test.topic}")
	private String topic;

	@BeforeEach
	public void setup() {
		FileUtils.deleteFile();
	}

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingToCarProducer_thenMessageReceived() throws Exception {
		// Create a simple CarDto object to send in message
		CarDTO carDtoMessage = new CarDTO();
		carDtoMessage.setId(UUID.randomUUID().toString());
		carDtoMessage.setColor("preto");
		carDtoMessage.setModel("hb20s");

		// Create a simple producer
		Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker.getBrokersAsString());
		log.info("props {}", producerProps);
		Producer<String, CarDTO> producerTest = new KafkaProducer(producerProps, new StringSerializer(), new JsonSerializer<CarDTO>());

		// Use the producer to send message
		producerTest.send(new ProducerRecord(topic, "", carDtoMessage));

		// Wait consumer read message and create a file
		await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
			assertThat(FileUtils.fileExists()).isTrue();
			assertThat(FileUtils.readFile()).contains("preto");
		});

		// Close producer
		producerTest.close();
	}

}
