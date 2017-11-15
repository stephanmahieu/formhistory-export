package com.formhistory.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URLEncoder;

public final class Utils {
    private static final String ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private Utils() {
    }

    public static String toDateString(long nanos) {
        final ZonedDateTime zonedDateTime = Instant.ofEpochMilli(nanos/1000).atZone(ZoneOffset.systemDefault());
        return DateTimeFormatter.ofPattern(ISO_PATTERN).format(zonedDateTime);
    }

    public static String getNowString() {
        return DateTimeFormatter.ofPattern(ISO_PATTERN).withZone(ZoneOffset.systemDefault()).format(Instant.now());
    }

    public static String selfCloseTags(String aString) {
        return aString
                .replaceAll("<id></id>", "<id/>")
                .replaceAll("<name></name>", "<name/>")
                .replaceAll("<formid></formid>", "<formid/>");
    }

    public static String encode(byte[] bytes) {
        try {
            return encode(new String(bytes, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            // should never happen
            System.out.println("WARN: Encoding UTF-8 failure, fallback to default");
            return encode(new String(bytes));
        }
    }

    public static String encode(String aString) {
        // use URLEncoder.encode() which can handle all international chars but
        // keep it somewhat readable by converting back some safe (for XML) chars
        try {
            return URLEncoder.encode(aString, StandardCharsets.UTF_8.name())
                /*encodeURIComponent(aString)*/
                 .replaceAll("\\+", " ")
              /* .replaceAll("%20", " ") */
                 .replaceAll("^ ", "%20") /* keep leading space(s)  */
                 .replaceAll(" $", "%20") /* keep trailing space(s) */
                 .replaceAll("%21", "!")
                 .replaceAll("%22", "\"")
                 .replaceAll("%23", "#")
                 .replaceAll("%24", "\\$")
                 /* do not replace %25 (%) */
                 .replaceAll("%26", "&")
                 .replaceAll("%27", "'")
                 .replaceAll("%28", "(")
                 .replaceAll("%29", ")")
                 .replaceAll("%7E", "~")
                 .replaceAll("%2B", "+")
                 .replaceAll("%2C", ",")
                 .replaceAll("%2F", "/")
                 .replaceAll("%3A", ":")
                 .replaceAll("%3B", ";")
                 .replaceAll("%3D", "=")
                 .replaceAll("%3F", "?")
                 .replaceAll("%40", "@")
                 .replaceAll("%5B", "[")
                 .replaceAll("%5C", "\\\\")
                 .replaceAll("%5D", "]")
                 .replaceAll("%5E", "^")
                 .replaceAll("%60", "`")
                 .replaceAll("%7B", "{")
                 .replaceAll("%7C", "|")
                 .replaceAll("%7D", "}")
                 .replaceAll("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            return aString;
        }
    }
}
