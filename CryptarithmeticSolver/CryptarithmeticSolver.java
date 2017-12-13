/*
    Jordan Merckling 951524113
    CIS 471
    Assignment 2
    Problem 3 (Cryptarithmetic puzzle solver)
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CryptarithmeticSolver {

    static Scanner inputString = new Scanner(System.in);

    static String minuend;
    static String subtrahend;
    static String difference;

    static int domain[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    static char[] minuendArr = new char[0];
    static char[] subtrahendArr = new char[0];
    static char[] differenceArr = new char[0];

    static ArrayList<Character> uniqueAL = new ArrayList<>();
    static ArrayList<Character> sharedAL = new ArrayList<>();
    static ArrayList<Character> allVariables = new ArrayList<>();

    static HashMap<Character, Integer> variableMap = new HashMap<>();
    static int firstInput, secondInput, thirdInput, assignment = 0;
    static boolean foundSolution = false;
    static ArrayList<ArrayList<Integer>> variations = new ArrayList<>();
    static ArrayList<Integer> result = new ArrayList<>();

    public static void main(String args[]) {
        getInput();
        variableTypes();
        checkVariables();
        System.out.println("Finding valid assignments...");
        findSolutions();
        System.out.println("DONE!");
    }

    public static void getInput() {
        System.out.print("Enter minuend input string: ");
        minuend = inputString.next();
        if (Pattern.matches("[a-zA-Z]+", minuend)) {
            minuend = minuend.toLowerCase();
        } else {
            System.out.println("ERROR: Enter a string");
            System.out.print("Enter minuend input string: ");
            minuend = inputString.next();
            minuend = minuend.toLowerCase();
        }

        System.out.print("Enter subtrahend input string: ");
        subtrahend = inputString.next();
        if (Pattern.matches("[a-zA-Z]+", subtrahend)) {
            subtrahend = subtrahend.toLowerCase();
        } else {
            System.out.println("ERROR: Enter a string");
            System.out.print("Enter subtrahend input string: ");
            subtrahend = inputString.next();
            subtrahend = subtrahend.toLowerCase();
        }

        System.out.print("Enter difference input string: ");
        difference = inputString.next();
        if (Pattern.matches("[a-zA-Z]+", difference)) {
            difference = difference.toLowerCase();
        } else {
            System.out.println("ERROR: Enter a string");
            System.out.print("Enter difference input string: ");
            difference = inputString.next();
            difference = difference.toLowerCase();
        }
    }

    //Separate variables into two list shared characters(appear more than once (MCV)) and unique characters(LCV)
    public static void variableTypes() {
        HashSet<Character> h1 = new HashSet<>(), h2 = new HashSet<>(), h3 = new HashSet<>();
        HashSet<Character> uniqueList = new HashSet<>(), sharedList = new HashSet<>();

        minuendArr = new char[minuend.length()];
        subtrahendArr = new char[subtrahend.length()];
        differenceArr = new char[difference.length()];

        for (int i = 0; i < minuend.length(); i++) {
            minuendArr[i] = minuend.charAt(i);
            h1.add(minuend.charAt(i));
        }
        for (int j = 0; j < subtrahend.length(); j++) {
            subtrahendArr[j] = subtrahend.charAt(j);
            h2.add(subtrahend.charAt(j));
        }
        for (int k = 0; k < difference.length(); k++) {
            differenceArr[k] = difference.charAt(k);
            h3.add(difference.charAt(k));
        }

        for (Character i : h1) {
            if (!h2.contains(i) || !h3.contains(i)) {
                uniqueList.add(i);
                uniqueAL.add(i);
            } else {
                sharedList.add(i);
                sharedAL.add(i);
            }
        }

        for (Character i : h2) {
            if (!h1.contains(i) || !h3.contains(i)) {
                if (!uniqueList.contains(i)) {
                    uniqueAL.add(i);
                    uniqueList.add(i);
                }
            } else {
                if (!sharedList.contains(i)) {
                    sharedAL.add(i);
                    sharedList.add(i);
                }
            }
        }
        for (Character i : h3) {
            if (!h1.contains(i) || !h2.contains(i)) {
                if (!uniqueList.contains(i)) {
                    uniqueList.add(i);
                    uniqueAL.add(i);
                }
            } else {
                if (!sharedList.contains(i)) {
                    sharedAL.add(i);
                    sharedList.add(i);
                }
            }
        }
//        System.out.println("Shared Variables" + sharedAL.toString());
//        System.out.println("Unique Variables" + uniqueAL.toString());
    }

    public static void checkVariables() {
        int totalVariableLength = sharedAL.size() + uniqueAL.size();

        int differenceLength = differenceArr.length;
        int longestString;
        if (minuendArr.length > subtrahendArr.length) {
            longestString = minuendArr.length;
        } else {
            longestString = subtrahendArr.length;
        }
        //Constrains MCV, LCV
        //Max Distinct variable from inputString and difference <= 10
        if (totalVariableLength > 10) {
            System.out.println("Error: There are more than 10 characters.");
            System.out.println("Exit.");
            System.exit(0);
        }

        //difference length should be equal to or -1 of the longest inputString
        if (!(differenceLength == longestString || differenceLength == (longestString - 1))) {
            System.out.println("Result String Length Error.");
            System.out.println("Exit.");
            System.exit(0);
        }
    }

    public static void findSolutions() {
        allVariables.addAll(uniqueAL);
        allVariables.addAll(sharedAL);
        Collections.sort(allVariables);

        variations(domain, 0);

        for (int i = 0; i < variations.size(); i++) {
            for (int j = 0; j < allVariables.size(); j++) {
                variableMap.put(allVariables.get(j), variations.get(i).get(j));
            }

            firstInput = getDigit(minuend);
            secondInput = getDigit(subtrahend);
            thirdInput = getDigit(difference);
            if (thirdInput == firstInput - secondInput &&
                    getIntLength(firstInput) == minuend.length() &&
                    getIntLength(secondInput) == subtrahend.length() &&
                    getIntLength(thirdInput) == difference.length()) {

                foundSolution = true;
                if (!result.contains(firstInput)) {
                    System.out.println(minuend + ":" + firstInput + "  " + subtrahend + ":" + secondInput + "  " + difference + ":" + thirdInput);
                    assignment++;
                }
                result.add(firstInput);
            }
        }
        System.out.println("Number of valid assignments: " + assignment);

        if (!foundSolution)
            System.out.println("No valid assignments exist");
    }

    public static void variations(int[] a, int k) {
        if (k == a.length) {
            ArrayList<Integer> perm = new ArrayList<>();
            for (int i = 0; i < a.length; i++) {
                perm.add(a[i]);
            }
            variations.add(perm);
        } else {
            for (int i = k; i < a.length; i++) {
                int temp = a[k];
                a[k] = a[i];
                a[i] = temp;
                variations(a, k + 1);
                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
    }

    public static int getIntLength(int i) {
        return String.valueOf(i).length();
    }

    public static int getDigit(String variable) {
        String temp = "";
        for (int i = 0; i < variable.length(); i++) {
            temp = temp + variableMap.get(variable.charAt(i));
        }
        return Integer.parseInt(temp);
    }
}