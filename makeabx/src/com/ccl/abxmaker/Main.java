package com.ccl.abxmaker;

import java.io.*;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
	// write your code here
        if(args.length < 1){
            String me = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getName();

            System.out.println();
            System.out.printf("USAGE: %s <input.xml>\n", me);
            System.out.println();
            System.out.println("Converts an XML file to an ABX file (the output will have the same path as the input");
            System.out.println("with an '.abx' extension added).");
            System.out.println();
            System.out.println("If one of the following suffixes are found on an attribute's name, they will be");
            System.out.println("treated as typed values:");
            System.out.println("-int, -inthex -long -longhex -float -double -bool -byteshex -base64");
            System.out.println("eg. <element myvalue-inthex='4a7f'>Attribute will be parsed as hex</element>");
            System.out.println();

            return;
        }

        String inputPath = args[0];
        FileReader reader = new FileReader(inputPath);
        byte[] result = Converter.Convert(reader);
        if(result == null){
            return;
        }
        String outputPath = inputPath + ".abx";
        if(new File(outputPath).exists()){
            System.out.printf("ERROR: '%s' already exists\n", outputPath);
        }
        FileOutputStream outStream = new FileOutputStream(outputPath);
        outStream.write(result);

        reader.close();
        outStream.close();
    }
}
