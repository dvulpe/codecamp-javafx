package test.ro.codecamp.converter.test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.codecamp.taskdashboard.util.ConversionService;

import java.math.BigDecimal;

public class ConversionServiceTest {

    private ConversionService conversionService;

    @Before
    public void setUp() throws Exception {
        conversionService = new ConversionService(Lists.<Function>newArrayList(new Function<Number, String>() {

            @Override
            public String apply(Number number) {
                return number.toString();
            }
        }));
    }

    @Test
    public void testCanConvertInteger() throws Exception {
        Assert.assertEquals("1", conversionService.convert(1, String.class));
    }

    @Test
    public void testCanConvertDouble() throws Exception {
        Assert.assertEquals("2.0", conversionService.convert(2.0, String.class));
    }

    @Test
    public void testCanConvertBigDecimal() throws Exception {
        Assert.assertEquals("2.0", conversionService.convert(new BigDecimal("2.0"), String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowException() throws Exception {
        conversionService.convert("1", Integer.class);
        Assert.fail();
    }

}
