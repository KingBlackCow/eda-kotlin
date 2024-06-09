package com.example.api.config

import com.fasterxml.jackson.core.JsonProcessingException
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.*
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.ExponentialBackOff
import java.util.concurrent.atomic.AtomicReference

@Configuration
@EnableKafka
class KafkaConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.kafka")
    fun kafkaProperties(): KafkaProperties {
        return KafkaProperties()
    }

    @Bean
    @Primary
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "-1"
        props[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = "true"
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    @Primary
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG] = "false"
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    @Primary
    fun errorHandler(): CommonErrorHandler {
        val cseh = CommonContainerStoppingErrorHandler()
        val consumer2 = AtomicReference<Consumer<*, *>>()
        val container2 = AtomicReference<MessageListenerContainer>()

        val errorHandler = object : DefaultErrorHandler({ rec, ex ->
            cseh.handleRemaining(ex, listOf(rec), consumer2.get(), container2.get())
        }, generateBackOff()) {
            override fun handleRemaining(
                thrownException: Exception,
                records: List<ConsumerRecord<*, *>>,
                consumer: Consumer<*, *>,
                container: MessageListenerContainer
            ) {
                consumer2.set(consumer)
                container2.set(container)
                super.handleRemaining(thrownException, records, consumer, container)
            }
        }

        errorHandler.addNotRetryableExceptions(JsonProcessingException::class.java)
        return errorHandler
    }

    @Bean
    @Primary
    fun kafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, *> {
        return KafkaTemplate(producerFactory(kafkaProperties))
    }

    private fun generateBackOff(): BackOff {
        val backOff = ExponentialBackOff(1000, 2.0)
        backOff.maxAttempts = 3
        return backOff
    }
}