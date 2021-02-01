// Doug Lundin
// CSC 240 Secure Software Development
// January 30, 2021
//
// IDS01-J Non-compliant program attempts to validate a String before performing
// normalization.
// This example does NOT find an embedded script in a string because it's not
// looking for the Unicode small less than sign or small greater than sign.
// It only looks for the regular less than and greater than signs so it doesn't
// identify a black listed tag. It should throw an IllegalStateException.

package edu.arapahoe;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    public static String ValidateAndNormalize(String s) {
        // Validate
        Pattern pattern = Pattern.compile("[<>]"); // Check for angle brackets
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            // Found black listed tag
            throw new IllegalStateException();
        } else {
            System.out.println("Input valid");
        }
        // Normalize
        s = Normalizer.normalize(s, Normalizer.Form.NFKC);
        return s;
    }
    public static String NormalizeAndValidate(String s) {
        // Normalize
        s = Normalizer.normalize(s, Normalizer.Form.NFKC);
        // Validate
        Pattern pattern = Pattern.compile("[<>]"); // Check for angle brackets
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            // Found black listed tag
            throw new IllegalStateException();
        } else {
            System.out.println("Input valid");
        }
        return s;
    }
    public static void main(String[] args) {
        // String s may be user controllable
        // \uFE64 is normalized to small < and \uFE65 is normalized to small >
        // using the NFKC normalization form

        String s = "\uFE64" + "script" + "\uFE65";
        System.out.println("Non-compliant: Validate before normalizing");
        System.out.println("Unnormalized string:"+s);
        ValidateAndNormalize(s);
        System.out.println("Normalized string: "+s);

        s = "\uFE64" + "script" + "\uFE65";
        System.out.println("\nCompliant: Normalize before validating");
        System.out.println("Unnormalized string:"+s);
        NormalizeAndValidate(s);
        System.out.println("Normalized string: "+s);
    }
}