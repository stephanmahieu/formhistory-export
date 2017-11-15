package com.formhistory.util;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.io.Writer;


public class XmlPrettyPrintWriter extends PrettyPrintWriter {
    private static final char[] NULL = "&#x0;".toCharArray();
    private static final char[] AMP = "&amp;".toCharArray();
    private static final char[] LT = "&lt;".toCharArray();
    private static final char[] GT = "&gt;".toCharArray();
    private static final char[] CR = "&#xd;".toCharArray();
//    private static final char[] QUOT = "&quot;".toCharArray();
//    private static final char[] APOS = "&apos;".toCharArray();

    public XmlPrettyPrintWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void writeText(QuickWriter writer, String text) {
        int length = text.length();

        for(int i = 0; i < length; ++i) {
            char c = text.charAt(i);
            switch(c) {
                case '\u0000':
                    writer.write(NULL);
                    break;
                case '\t':
                case '\n':
                    writer.write(c);
                    break;
                case '\r':
                    writer.write(CR);
                    break;
                case '&':
                    writer.write(AMP);
                    break;
                case '<':
                    writer.write(LT);
                    break;
                case '>':
                    writer.write(GT);
//                case '"':
//                    writer.write(QUOT);
//                    break;
//                case '\'':
//                    writer.write(APOS);
//                    break;

                default:
                    if (Character.isDefined(c) && !Character.isISOControl(c)) {
                        if (c > '\ud7ff' && c < '\ue000') {
                            throw new StreamException("Invalid character 0x" + Integer.toHexString(c) + " in XML stream");
                        }
                        writer.write(c);
                    } else {
                        if (c < '\t' || c == 11 || c == '\f' || c == 14 || c >= 15 && c <= 31) {
                            throw new StreamException("Invalid character 0x" + Integer.toHexString(c) + " in XML 1.0 stream");
                        }

                        if (c == '\ufffe' || c == '\uffff') {
                            throw new StreamException("Invalid character 0x" + Integer.toHexString(c) + " in XML stream");
                        }

                        writer.write("&#x");
                        writer.write(Integer.toHexString(c));
                        writer.write(';');
                    }
                    break;
            }
        }
    }
}
