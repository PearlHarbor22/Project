package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.config.KafkaErrorLogger;
import com.bank.antifraud.dto.AuthDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bank.antifraud.util.Constants.TEST_ID;
import static com.bank.antifraud.util.Constants.TEST_ROLE_USER;
import static com.bank.antifraud.util.Constants.TEST_RUNTIME_EXCEPTION_MESSAGE;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AuthConsumerTest {
    @Mock
    KafkaErrorLogger kafkaErrorLogger;
    @InjectMocks
    AuthConsumer authConsumer;

    @Test
    void listen_success() {
        AuthDto authDto = new AuthDto();
        authDto.setId(TEST_ID);
        authDto.setRole(TEST_ROLE_USER);

        authConsumer.listen(authDto);

        verifyNoInteractions(kafkaErrorLogger);
        assert authConsumer.getCurrentAuthId().equals(TEST_ID);
    }

    @Test
    void listen_withNullAuthDto() {
        AuthDto authDto = null;

        authConsumer.listen(authDto);

        verify(kafkaErrorLogger).handleNullPointerException(any(NullPointerException.class), eq(null));
        verifyNoMoreInteractions(kafkaErrorLogger);
    }

    @Test
    void listen_withNullId() {
        AuthDto authDto = new AuthDto();
        authDto.setId(null);

        authConsumer.listen(authDto);


        verifyNoInteractions(kafkaErrorLogger);
        assert authConsumer.getCurrentAuthId() == null;
    }

    @Test
    void listen_withEmptyAuthDto() {
        AuthDto authDto = new AuthDto();

        authConsumer.listen(authDto);

        verifyNoInteractions(kafkaErrorLogger);
        assert authConsumer.getCurrentAuthId() == null;
    }

    @Test
    void listen_withOnlyId() {
        AuthDto authDto = new AuthDto();
        authDto.setId(TEST_ID);

        authConsumer.listen(authDto);

        verifyNoInteractions(kafkaErrorLogger);
        assert authConsumer.getCurrentAuthId().equals(TEST_ID);
    }

    @Test
    void listen_withRuntimeException() {
        AuthDto authDto = new AuthDto() {
            @Override
            public Long getId() {
                throw new RuntimeException(TEST_RUNTIME_EXCEPTION_MESSAGE);
            }
        };

        authConsumer.listen(authDto);

        verify(kafkaErrorLogger).handleRuntimeException(any(RuntimeException.class), eq(authDto));
        verifyNoMoreInteractions(kafkaErrorLogger);
    }
}
