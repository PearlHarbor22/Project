package com.bank.publicinfo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = KafkaConfig.class)
@TestPropertySource(properties = {
        KafkaConfigTest.KAFKA_BOOTSTRAP_PROPERTY,
        KafkaConfigTest.KAFKA_GROUP_ID_PROPERTY,
        KafkaConfigTest.KAFKA_TRUSTED_PACKAGES_PROPERTY,
        KafkaConfigTest.KAFKA_DEFAULT_TYPE_PROPERTY
})
class KafkaConfigTest {

    static final String BOOTSTRAP_SERVERS = "localhost:9092";
    static final String GROUP_ID = "test-group";
    static final String KAFKA_BOOTSTRAP_PROPERTY = "spring.kafka.bootstrap-servers=" + BOOTSTRAP_SERVERS;
    static final String KAFKA_GROUP_ID_PROPERTY = "spring.kafka.consumer.group-id=" + GROUP_ID;
    static final String KAFKA_TRUSTED_PACKAGES_PROPERTY = "spring.kafka.consumer.properties.spring.json.trusted.packages=*";
    static final String KAFKA_DEFAULT_TYPE_PROPERTY = "spring.kafka.consumer.properties.spring.json.value.default.type=java.lang.String";
    static final String AUTO_OFFSET_RESET = "earliest";

    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;

    @Autowired
    private ProducerFactory<String, Object> producerFactory;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void consumerFactory_ShouldContainConfiguredProperties() {
        var configs = consumerFactory.getConfigurationProperties();

        assertThat(configs.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo(BOOTSTRAP_SERVERS);
        assertThat(configs.get(ConsumerConfig.GROUP_ID_CONFIG)).isEqualTo(GROUP_ID);
        assertThat(configs.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo(AUTO_OFFSET_RESET);
    }

    @Test
    void producerFactory_ShouldContainConfiguredProperties() {
        var configs = producerFactory.getConfigurationProperties();

        assertThat(configs.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo(BOOTSTRAP_SERVERS);
        assertThat(configs.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class);
        assertThat(configs.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(JsonSerializer.class);
    }

    @Test
    void kafkaTemplate_ShouldBeCreated() {
        assertThat(kafkaTemplate).isNotNull();
    }
}