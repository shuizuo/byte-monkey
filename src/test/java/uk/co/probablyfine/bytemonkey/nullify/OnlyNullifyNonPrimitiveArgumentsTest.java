package uk.co.probablyfine.bytemonkey.nullify;

import org.junit.Test;
import uk.co.probablyfine.bytemonkey.testfiles.NullabilityTestPojo;

import java.io.IOException;

import static org.junit.Assert.assertNull;
import static uk.co.probablyfine.bytemonkey.TestUtils.installAgent;

public class OnlyNullifyNonPrimitiveArgumentsTest {

    @Test
    public void shouldOnlyNullifyObjects() throws IOException {
        installAgent(
            "mode:nullify,rate:1,filter:uk/co/probablyfine/bytemonkey/testfiles/NullabilityTestPojo/setName2ndArg"
        );

        NullabilityTestPojo pojo = new NullabilityTestPojo("foo");

        pojo.setName2ndArg(1, "bar");

        assertNull(pojo.getName());
    }

}