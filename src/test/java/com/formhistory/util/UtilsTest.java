package com.formhistory.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void replaceLeadingTrailingSpace() {
        String result = Utils.encode("  spaces  ");
        assertEquals("%20 spaces %20", result);
    }

    @Test
    public void replacePlus() {
        String result = Utils.encode("space space");
        assertEquals("space space", result);
    }

    @Test
    public void selfcloseTags() {
        String result = Utils.selfCloseTags("noop><formid></formid><noop");
        assertEquals("noop><formid/><noop", result);
    }

}
