package com.bloethner.termproject.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import java.io.UnsupportedEncodingException;

/**
 * Create url utility class for redirecting to ensure proper path encoding
 */
public class UrlUtil {

    /**
     * Return url path segment
     */
    public static String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();

        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }

        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
            // do nothing here
        }

        return pathSegment;
    }
}
