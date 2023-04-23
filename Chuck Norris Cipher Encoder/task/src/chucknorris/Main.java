package chucknorris;

import java.io.IOError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }
    public StringBuilder encode(String bin) {

        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (i < bin.length()) {
            if (i < bin.length() && bin.charAt(i) == '0') {
                stringBuilder.append("00 ");
                while (i < bin.length() && bin.charAt(i) == '0') {
                    stringBuilder.append("0");
                    i++;
                }
                i--;
                stringBuilder.append(" ");
            }
            if (i < bin.length() && bin.charAt(i) == '1') {
                stringBuilder.append("0 ");
                while (i < bin.length() && bin.charAt(i) == '1') {
                    stringBuilder.append("0");
                    i++;
                }
                i--;
                stringBuilder.append(" ");
            }
            i++;
        }
        return stringBuilder;
    }
    public String decode(String str){
        StringBuilder decode = new StringBuilder();
        String[] str1 = str.toString().split("\\s", 0);
//        System.out.println(Arrays.toString(str1));
        boolean isOne = false;
        boolean isZero = false;
        if(!str1[0].equals("0") && !str1[0].equals("00"))
        {
            System.out.println("Encoded string is not valid.");
            return null;
        }
        int count = 0;
        for (String element : str1) {
            count ++;
//            System.out.println(element+"#");
            if(isZero){
//              System.out.println("add zero");
                decode.append("0".repeat(element.length()));
                isZero = false;
                continue;
            }else if(isOne){
//                System.out.println("add one");
                decode.append("1".repeat(element.length()));
                isOne = false;
                continue;
            }
            if(element.equals("00") && !isZero){
//                System.out.println("zero");
                isZero  = true;
            }
            else if(element.equals("0") && !isOne){
//                System.out.println("one");
                isOne = true;
            }

        }
        if ((count % 2) != 0){
            System.out.println("Encoded string is not valid.");
            return null;
        }
        return decode.toString();
    }
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        menu :
        while(true){
            System.out.println("Please input operation (encode/decode/exit):");
            String option = scanner.nextLine();
            switch (option){
                case "encode":{
                    System.out.println("Input string:");
                    String input = scanner.nextLine();
                    StringBuilder bin = new StringBuilder();
                    for (int i = 0; i < input.length(); i++) {
                        bin.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(input.charAt(i)))));
                    }
                    StringBuilder encode;
                    encode = main.encode(String.valueOf(bin));
                    System.out.println("Encoded string:");
                    System.out.println(encode.toString());
                    System.out.println();
                    break;
                }
                case "decode":{
                    boolean isValid = true;
                    System.out.println("Input encoded string:");
                    String input = scanner.nextLine();
                    for (int i = 0; i < input.length(); i++) {
                        if(input.charAt(i) != '0' && input.charAt(i) != ' ' && input.charAt(i) != '\n'){
                            isValid = false;
                            break;
                        }
                    }
                    if (!isValid){
                        System.out.println("Encoded string is not valid.");
                        break ;
                    }
                    String decode = main.decode(String.valueOf(input));
                    if(decode == null){
                        break;
                    }
                    if((decode.length()%7) != 0){
                        System.out.println("Encoded string is not valid.");
                        break;
                    }
                    List<String> str2 = splitEqually(decode.toString(),7);
                    System.out.println("Decoded string:");
                    try{
                        for (int i = 0; i < str2.size(); i++) {
//                System.out.println(str2.get(i)+"#");
                            Character character = (char) Integer.parseInt(str2.get(i),2);
                            System.out.printf("%s",character.toString());
                        }
                    }catch (IOError e){
                        e.printStackTrace();
                    }
                    System.out.println();
                    break;
                }
                case "exit":{
                    System.out.println("Bye!");
                    break menu;
                }
                default:{
                    System.out.println("There is no \'"+option+"\' operation");
                }
            }
        }
    }
}