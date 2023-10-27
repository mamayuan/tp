package swe.context.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class PhoneTest {
    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValid("")); // empty string
        assertFalse(Phone.isValid(" ")); // spaces only
        assertFalse(Phone.isValid("91")); // less than 3 numbers
        assertFalse(Phone.isValid("phone")); // non-numeric
        assertFalse(Phone.isValid("9011p041")); // alphabets within digits
        assertFalse(Phone.isValid("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValid("911")); // exactly 3 numbers
        assertTrue(Phone.isValid("93121534"));
        assertTrue(Phone.isValid("124293842033123")); // long phone numbers
    }
}
