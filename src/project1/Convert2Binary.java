package project1;



import java.util.Scanner;

public class Convert2Binary {
   public static void main(String[] args) {
      int selection = 0;
      ToBinary converter = new ToBinary();
      Scanner scan = new Scanner(System.in);

      while (selection != 4){ 
        // Make selection
        System.out.println("**************************************************");
        System.out.println("* 1. Unsigned                                    *");
        System.out.println("* 2. Signed Two's Complement                     *");
        System.out.println("* 3. Floating Point                              *");
        System.out.println("* 4. Quit                                        *");
        System.out.println("* 5. Extra Credit                                *");
        System.out.println("**************************************************");
        System.out.print("ENTER SELECTION (1-5):");
        selection = scan.nextInt();
        while (selection < 1 || selection > 5) {
           System.out.print("ENTER SELECTION (1-5):");
           selection = scan.nextInt();
        }

        // Unsigned, just accept one integer value
        if (selection == 1) {
           System.out.print("ENTER A NON-NEGATIVE INTEGER: ");
           int value = scan.nextInt();
           while (value < 0) {
              System.out.print("ENTER A NON-NEGATIVE INTEGER:");
              value = scan.nextInt();
           }
           System.out.println("UNSIGNED: "+converter.toUnsigned(value));
        }

        // Two's complement, need the number of bits and the value
        else if (selection == 2) {
           System.out.print("ENTER AN INTEGER: ");
           int value = scan.nextInt();
           System.out.print("HOW MANY BITS?");
           int numbits = scan.nextInt();
           while (numbits <= 0) {
              System.out.print("ENTER A POSITIVE NUMBER OF BITS:");
              numbits = scan.nextInt();
           }
           System.out.println("SIGNED: "+converter.toSigned(value, numbits));
        }

        // Floating point, just accept one float value
        else if (selection == 3) {
           System.out.print("ENTER A FLOATING POINT VALUE: ");
           float value = scan.nextFloat();
           System.out.println("IEEE 32-BIT FLOATING POINT VALUE: "+converter.toFloat(value));
        }

        else if (selection == 5) {
           System.out.print("ENTER A DOUBLE PRECISION VALUE: ");
           double value = scan.nextDouble();
           System.out.println("IEEE 64-BIT DOUBLE-PRECISION VALUE: "+converter.toDouble(value));
        }
       
   }
 }
}