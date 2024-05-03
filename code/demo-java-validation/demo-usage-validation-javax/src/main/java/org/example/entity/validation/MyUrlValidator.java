package org.example.entity.validation;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MyUrlValidator implements ConstraintValidator<MyUrl, String> {

    // https://www.geeksforgeeks.org/how-to-validate-a-domain-name-using-regular-expression/
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$");

    // https://www.geeksforgeeks.org/how-to-validate-an-ip-address-using-regular-expressions-in-java/
    private static final Pattern IP_PATTERN = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isIp(value)
                || isDomain(value)
                || isUrl(value);
    }

    boolean isDomain(String value) {
//        return DOMAIN_PATTERN.matcher(value).find();
        return DomainValidator.getInstance(true).isValid(value); // || "localhost".equals(value);
    }

    boolean isIp(String value) {
//        return IP_PATTERN.matcher(value).find();
        return InetAddressValidator.getInstance().isValidInet4Address(value);
    }

    boolean isUrl(String value) {
        java.net.URL url = null;
        try {
            url = new java.net.URL(value);
        } catch (MalformedURLException e) {
            return false;
        }
        String[] validProtocols = new String[] {
                "http",
                "https"
        };
        if (!Arrays.asList(validProtocols).contains(url.getProtocol())) {
            return false;
        }
        return true;
    }
}
