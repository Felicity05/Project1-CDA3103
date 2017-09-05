
import java.util.Scanner;
import java.util.Stack;

class ToBinary {
    // Constructor

    public ToBinary() {
    }

    // Helper Function
    private String flipBits(String bits) {
        String result = "";
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '0') {
                result += '1';
            } else {
                result += '0';
            }
        }
        return result;
    }

    // Helper Function
    private String zeroPadFront(String s, int n) {
        String result = s;
        for (int i = s.length(); i < n; i++) {
            result = "0" + result;
        }
        return result;
    }

    // Helper Function
    private String zeroPadRear(String s, int n) {
        String result = s;
        for (int i = s.length(); i < n; i++) {
            result += "0";
        }
        return result;
    }

    // Unsigned
    public String toUnsigned(int value) {
        String result = "";
        Stack<Integer> values = new Stack<>();      //to storage the reminders values 
        do {
            int reminder = value % 2;
            value = value / 2;
            values.push(reminder);          //saves the reminders values
        } while (value != 0);

        while (!values.isEmpty()) {      //while the Stack if not empty 
            result += Integer.toString(values.pop()); //print the values in revert order (LIFO)
        }

        return result;
    }

    private boolean inSignedRange(int value, int numbits) {
        //************************************************************
        // PUT CODE HERE
        int minBits = this.toUnsigned(Math.abs(value)).length() + 1;
        /**
         * The minimum number of bits required to represent a signed number is
         * the minimum number of bits required to represent its unsigned version
         * plus one bit for the sign. For example: For a value of 15, the
         * minimum number of bits necessary to represent it in unsigned binary
         * is 4. Therefore, we need one more bit for the signed version. Thus, 5
         * bits are the minimum required for its signed version.
         */

        if (numbits < minBits) {
            return false;
        } else {
            return true;
        }
        // Return true if the passed value can be represented in the specified 
        //number of bits for Two's complement
        //************************************************************

    }

    // Two's Complement
    public String toSigned(int value, int numbits) {
        // Check if no overflow
        if (!inSignedRange(value, numbits)) {
            return "OVERFLOW";
        } else {
            boolean isNeg = false;
            // Stores whether or not the value is a negative number.

            //Checks if the value is a negative number
            if (value < 0) {
                value = value + 1;            //If yes, add 1 to the value and
                value = Math.abs(value);      //make the value positive
                isNeg = true;                 //The value is a negative number.
            }

            //Stores the unflipped signed binary version
            String result = zeroPadFront(toUnsigned(value), numbits);

            // Checks if the value was negative
            if (isNeg) {
                result = flipBits(result);    //if it was, the bits are flipped
            }

            return result;     //Returns the Signed Binary 
        }
    }

    // Floating Point
    public String toFloat(float value) {
        // Helpful variables
        String sign, mantissa, exponent;

        //SIGN*******************************************
        //1 bit (first) sign
        if (value >= 0) //Checks if the value is positive or negative
        {
            sign = "0";         //if positive, the sign becomes a "0"
        } else {                  //If negative, the sign becomes a "1" and use the absolute value
            sign = "1";
            value = Math.abs(value);
        }

        // Use toUnsigned() to get integer portion
        String integerPortion = toUnsigned((int) value);
        System.out.println("IntegerPortion: " + integerPortion);
        value -= (int) value;       //gets the decimal value
        System.out.println("Value(decimal part): " + value);

        float sum = 0;                  //The hypothetical sum which is 2^power
        int power = -1;                 //The current power of the decimal value
        String decimalPortion = "";     //Stores binary after the decimal point

        //DECIMAL PORTION*******************************************
        while (sum < value && (integerPortion.length() - 1) + decimalPortion.length() <= 23) {

            sum = (float) Math.pow(2, power);
            System.out.println("Sum: " + sum);

            while (value != sum) {
                if (value < sum) {
                    decimalPortion += "0";
                } else if (value > sum) {
                    value = value - sum;
                    decimalPortion += "1";
                }

                power--;
                sum = (float) Math.pow(2, power);

                if (value == sum) {
                    decimalPortion += "1";

                }

            }
        }
        System.out.println("decimalPortion: " + decimalPortion);

        //MANTISSA**************************************************
        //23 bits mantissa

        //the mantissa is the integer part plus the decimal part bouth in binary
        //then complete with "0" until length 23 is reached
        String mantissaParts = integerPortion + decimalPortion;
        System.out.println("mantissa parts: " + mantissaParts);
        System.out.println("index of \"1\" "+ mantissaParts.indexOf('1'));
        System.out.println("index of \"1\"  + 1: "+ mantissaParts.indexOf('1') + 1);
        System.out.println("substring of index of \"1\" "+ mantissaParts.substring(mantissaParts.indexOf('1')+1));
        System.out.println("substring of 1: "+ mantissaParts.substring(1));
        mantissa = zeroPadRear(mantissaParts.substring(mantissaParts.indexOf('1')+1), 23);
        
        
        //EXPONENT PART**********************************************
        //8 bits exponent biased by 127
        int exponentValue;
        
        if (!integerPortion.equals("0")) {
            exponentValue = integerPortion.length() - 1;
        } else {
            exponentValue = (decimalPortion.indexOf('1') + 1) * -1;
            System.out.println("exponent value " + exponentValue);
        }

        
        exponentValue = exponentValue + 127; //Add 127 to the exponentValue
        exponent = zeroPadFront(toUnsigned(exponentValue), 8);
        System.out.println("exponent value " + exponentValue);

        System.out.println("Exponent: " + exponent);

        //RESULT*****************************************************
        //sign + " " + mantissa  + " " + exponent;
        System.out.println("number of bits of the mantissa: " + mantissa.length());
        return mantissa;  //+ " " + exponent;
    }

    // Double Precision EXTRA CREDIT
    public String toDouble(double value) {

        // Helpful variables
        String sign, mantissa, exponent;

        //SIGN*******************************************
        //1 bit (first) sign
        if (value >= 0) //Checks if the value is positive or negative
        {
            sign = "0";         //if positive, the sign becomes a "0"
        } else {                  //If negative, the sign becomes a "1" and use the absolute value
            sign = "1";
            value = Math.abs(value);
        }

        // Use toUnsigned() to get integer portion
        String integerPortion = toUnsigned((int) value);
        System.out.println("IntegerPortion: " + integerPortion);
        value -= (int) value;       //gets the decimal value
        System.out.println("Decimal part: " + value);

        float sum = 0;                  //The hypothetical sum which is 2^power
        int power = -1;                 //The current power of the decimal value
        String decimalPortion = "";     //Stores binary after the decimal point

        //DECIMAL PORTION*******************************************
        while (sum < value && (integerPortion.length() - 1) + decimalPortion.length() <= 52) {

            sum = (float) Math.pow(2, power);
            System.out.println("Sum: " + sum);

            if (value == sum) {
                decimalPortion += "1";
            } else if (value < sum) {
                decimalPortion += "0";
            } else {
                value = value - sum;
                decimalPortion += "1";
            }

            power--;
            sum = (float) Math.pow(2, power);
        }
        System.out.println("decimalPortion: " + decimalPortion);

        //MANTISSA**************************************************
        //52 bits mantissa
        integerPortion = integerPortion.substring(1); // Delete the first number
        System.out.println("Integer Portion without the first bit: " + integerPortion);

        //the mantissa is the integer part plus the decimal part bouth in binary
        //then complete with "0" until length 23 is reached
        String mantissaParts = integerPortion + decimalPortion;
        mantissa = zeroPadRear(mantissaParts, 52);

        //EXPONENT PART**********************************************
        //11 bits exponent biased by 1023
        int exponentValue = 0;
        if (!integerPortion.equals("0")) {
            exponentValue = integerPortion.length();
        }
        // else if()
        exponentValue += 1023; //Add 127 to the exponentValue

        //Change the exponent to a binary value
        exponent = toUnsigned(exponentValue);
        System.out.println("Exponent: " + exponent);

        //RESULT*****************************************************
        //sign + " " + mantissa  + " " + exponent;
        System.out.println("number of bits of the mantissa: " + mantissa.length());
        return sign + " " + mantissa + " " + exponent;  //+ " " + exponent;

    }
}
