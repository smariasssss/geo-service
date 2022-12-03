package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class MessageSenderTest {
    @Mock
    private GeoServiceImpl geoService;
    @Mock
    private LocalizationServiceImpl localizationService;

    private MessageSender messageSender;
    private Map<String, String> headers;

    @BeforeEach
    void setUp(){
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers = new HashMap<>();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void send(String ip, Location location, Country country, String expected){
        Mockito.when(geoService.byIp(ip)).thenReturn(location);
        Mockito.when(localizationService.locale(country)).thenReturn(expected);

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String actual = messageSender.send(headers);

        Assertions.assertEquals(expected,actual);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(
                        GeoServiceImpl.MOSCOW_IP,
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                        Country.RUSSIA,
                        "Добро пожаловать\n"),
                Arguments.of(
                        GeoServiceImpl.NEW_YORK_IP,
                        new Location("New York", Country.USA, " 10th Avenue", 32),
                        Country.USA,
                        "Welcome\n"),
                Arguments.of(
                        GeoServiceImpl.GERMANY_IP,
                        new Location("Hamburg",Country.GERMANY,null,0),
                        Country.GERMANY,"Guten Tag\n"),
                Arguments.of(
                        GeoServiceImpl.BRAZIL_IP,
                        new Location("Sao Paulo",Country.BRAZIL,null,0),
                        Country.BRAZIL,"Olá\n"));
    }
}