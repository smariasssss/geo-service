package geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceTest {

    private GeoService geoServiceImpl;

    @BeforeEach
    void setUp(){
        geoServiceImpl = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void byIpTest(String ip, Location expected){
        Location actual = geoServiceImpl.byIp(ip);

        Assertions.assertEquals(expected.getCity(), actual.getCity());
        Assertions.assertEquals(expected.getCountry(), actual.getCountry());
        Assertions.assertEquals(expected.getStreet(), actual.getStreet());
        Assertions.assertEquals(expected.getBuiling(), actual.getBuiling());
    }

    @Test
    void byIpOther(){
        Location actual = geoServiceImpl.byIp("");
        Assertions.assertNull(actual);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of("172.16.2.13", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.45.1.10", new Location("New York", Country.USA, null,  0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.BRAZIL_IP,new Location("Sao Paulo",Country.BRAZIL,null,0)),
                Arguments.of(GeoServiceImpl.GERMANY_IP,new Location("Hamburg",Country.GERMANY,null,0))
        );
    }
}