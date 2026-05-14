package com.example.demo.Security;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SecurityUtils {

    
    public static final PolicyFactory POLICY = Sanitizers.FORMATTING
            .and(Sanitizers.LINKS)
            .and(Sanitizers.BLOCKS);
            
            

    public static String sanitize(String html) {
        return POLICY.sanitize(html);
    }
}
