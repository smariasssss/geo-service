package i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceTest {

    private LocalizationService localizationServiceImpl;

    @BeforeEach
    void setUp(){
        localizationServiceImpl = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void localeTest(Country country, String expected){
        String actual = localizationServiceImpl.locale(country);
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Olá"),
                Arguments.of(Country.GERMANY, "Guten Tag"));
    }
}