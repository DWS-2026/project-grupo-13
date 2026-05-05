package com.example.demo.Security;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SecurityUtils {

    // Política de sanitización permitiendo formato básico, listas, enlaces y bloques
    public static final PolicyFactory POLICY = Sanitizers.FORMATTING
            .and(Sanitizers.LINKS)
            .and(Sanitizers.BLOCKS);
            
            

    public static String sanitize(String html) {
        return POLICY.sanitize(html);
    }
}
