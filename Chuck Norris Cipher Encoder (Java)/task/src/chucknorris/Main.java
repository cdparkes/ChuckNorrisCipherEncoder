package chucknorris;

import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean restart = true;
        do {
            restart = menu();
            System.out.println();
        } while (restart);
    }

    private static boolean menu() {
        System.out.println("Please input operation (encode/decode/exit):");
        String input = scanner.nextLine();
        switch (input) {
            case "encode" -> {
                System.out.println("Input string:");
                input = scanner.nextLine();
                encriptMessage(input);
            }
            case "decode" -> {
                System.out.println("Input encoded string:");
                input = scanner.nextLine();
                decriptMessage(input);
            }
            case "exit" -> {
                System.out.println("Bye");
                return false;
            }
            default -> System.out.printf("There is no '%s' operation\n", input);
        }
        return true;
    }
    private static void decriptMessage(String input) {
        boolean failCondition = false;
        int firstPosition = 0, secondPositon = 1, repeatCounter = 0;
        String[] inputString = input.split(" ");

        // Validate format of the encoded input
        for(String inputSector : inputString){
            if(!inputSector.matches("0+")) {
                failCondition = true;
                break;
            }
        }

        if(inputString.length % 2 != 0 || !inputString[0].equals("0") && !inputString[1].equals("00")) {
            failCondition = true;
        }

        StringBuilder binaryString = new StringBuilder();
        StringBuilder outputString = new StringBuilder();
        // Decode binary string form encoded format
        while (!failCondition && secondPositon < inputString.length) {
            repeatCounter = inputString[secondPositon].length();

            if (inputString[firstPosition].equals("0")) {
                binaryString.append("1".repeat(repeatCounter));
            } else if (inputString[firstPosition].equals("00")) {
                binaryString.append("0".repeat(repeatCounter));
            } else {
                failCondition = true;
                break;
            }

            firstPosition += 2;
            secondPositon += 2;
        }

        // Check if binary length is divisible by 7, otherwise it's invalid
        if(binaryString.length() % 7 != 0) {
            failCondition = true;
        }

        if(failCondition) {
            System.out.println("Encoded string is not valid.");
            return;
        }

        // Decode each 7-bit segment to characters
        firstPosition = 0;
        secondPositon = 7;

        while(secondPositon <= binaryString.length()) {
            outputString.append((char) Integer.parseInt(binaryString.substring(firstPosition, secondPositon), 2));
            firstPosition += 7;
            secondPositon += 7;
        }

        // Print the required lines
        System.out.println("Decoded string:");
        System.out.println(outputString.toString());
    }


    public static void encriptMessage(String input) {
        int oneCount = 0, zeroCount = 0;
        StringBuilder characterBinary = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            int character = (int) input.charAt(i);
            String binaryString = String.format("%7s", Integer.toBinaryString(character)).replace(' ', '0');
            characterBinary.append(binaryString);
        }
        System.out.println("Encoded string:");
        for (int j = 0; j < characterBinary.length(); j++) {
            if (characterBinary.charAt(j) == '1') {
                oneCount++;
                if (j == characterBinary.length() - 1 || characterBinary.charAt(j + 1) != '1') {
                    System.out.printf("0 %s ", "0".repeat(oneCount));
                    oneCount = 0;
                }
            } else if (characterBinary.charAt(j) == '0') {
                zeroCount++;
                if (j == characterBinary.length() - 1 || characterBinary.charAt(j + 1) != '0') {
                    System.out.printf("00 %s ", "0".repeat(zeroCount));
                    zeroCount = 0;
                }
            }
        }
        System.out.println();
    }
}