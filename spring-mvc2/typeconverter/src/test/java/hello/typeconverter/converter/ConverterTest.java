package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ConverterTest {

    @Test
    void stringToInteger(){
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToString(){
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(10);
        assertThat(result).isEqualTo("10");
    }

    @Test
    void StringToIpPort(){
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.0:8080";
        IpPort result = converter.convert(source);
        assertThat(result).isEqualTo(new IpPort("127.0.0.0", 8080)); //@EqualsAndHashCode를 사용해서 가능함!
//        assertThat(result.getIp()).isEqualTo("127.0.0.0");
//        assertThat(result.getPort()).isEqualTo(8080);
    }

    @Test
    void IpPortToString(){
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort ipPort = new IpPort("127.0.0.0", 8080);
        String result = converter.convert(ipPort);
        assertThat(result).isEqualTo("127.0.0.0:8080");
    }
}
