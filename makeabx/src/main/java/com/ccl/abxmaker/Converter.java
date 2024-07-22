package com.ccl.abxmaker;

import com.android.org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.xmlpull.v1.XmlPullParser.CDSECT;
import static org.xmlpull.v1.XmlPullParser.COMMENT;
import static org.xmlpull.v1.XmlPullParser.DOCDECL;
import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.ENTITY_REF;
import static org.xmlpull.v1.XmlPullParser.IGNORABLE_WHITESPACE;
import static org.xmlpull.v1.XmlPullParser.PROCESSING_INSTRUCTION;
import static org.xmlpull.v1.XmlPullParser.START_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class Converter {
    public static int nibbleToInt(char c){
        if(c >= '0' && c <= '9'){ return c - '0';}
        if(c >= 'A' && c <= 'F'){ return c - 'A' + 10;}
        if(c >= 'a' && c <= 'f'){ return c - 'a' + 10;}
        return -1;
    }

    public static byte[] hexToBytes(String hex){
        final int len = hex.length();
        if(len % 2 != 0){
            throw new IllegalArgumentException("invalid length for hex text");
        }
        byte[] result = new byte[len / 2];
        for(int i = 0; i < len; i += 2){
            int high = nibbleToInt(hex.charAt(i));
            int low = nibbleToInt(hex.charAt(i + 1));
            if(high == -1 || low == -1){
                throw new IllegalArgumentException("invalid characters for hex text");
            }
            result[i / 2] = (byte) ((high * 16) + low);
        }
        return result;
    }

    public static byte[] Convert(Reader reader) throws IOException {
        KXmlParser parser = new KXmlParser();
        //Reader reader = new StringReader(doc);
        try {
            parser.setInput(reader);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

        BinaryXmlSerializer serializer = new BinaryXmlSerializer();
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        serializer.setOutput(writer, null);
        serializer.startDocument(null, null);

        int event;
        do{
            try {
                event = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return null;
            }

            switch (event){
                case START_TAG:
                    serializer.startTag(null, parser.getName());
                    for(int i = 0; i < parser.getAttributeCount(); i++){
                        // TODO: Infer types for typed attributes?
                        String attributeName = parser.getAttributeName(i);
                        int dashIndex = attributeName.lastIndexOf("-");
                        if(dashIndex > 0){
                            String typeName = attributeName.substring(dashIndex + 1).toLowerCase(Locale.ROOT);
                            switch (typeName){
                                case "int":
                                    serializer.attributeInt(
                                            null,
                                            attributeName,
                                            Integer.parseInt(parser.getAttributeValue(i)));
                                    break;
                                case "inthex":
                                    serializer.attributeIntHex(
                                            null,
                                            attributeName,
                                            Integer.parseInt(parser.getAttributeValue(i), 16));
                                    break;
                                case "long":
                                    serializer.attributeLong(
                                            null,
                                            attributeName,
                                            Long.parseLong(parser.getAttributeValue(i)));
                                    break;
                                case "longhex":
                                    serializer.attributeLongHex(
                                            null,
                                            attributeName,
                                            Long.parseLong(parser.getAttributeValue(i), 16));
                                    break;
                                case "float":
                                    serializer.attributeFloat(
                                            null,
                                            attributeName,
                                            Float.parseFloat(parser.getAttributeValue(i)));
                                    break;
                                case "double":
                                    serializer.attributeDouble(
                                            null,
                                            attributeName,
                                            Double.parseDouble(parser.getAttributeValue(i)));
                                    break;
                                case "bool":
                                    if(parser.getAttributeValue(i).toLowerCase(Locale.ROOT) == "true"){
                                        serializer.attributeBoolean(null, attributeName, true);
                                    }else if(parser.getAttributeValue(i).toLowerCase(Locale.ROOT) == "false"){
                                        serializer.attributeBoolean(null, attributeName, false);
                                    }else{
                                        serializer.attribute(null,attributeName, parser.getAttributeValue(i));
                                    }
                                    break;
                                case "byteshex":
                                    serializer.attributeBytesHex(
                                            null,
                                            attributeName,
                                            hexToBytes(parser.getAttributeValue(i)));
                                    break;
                                case "base64":
                                    byte[] tmp = java.util.Base64.getDecoder().decode(parser.getAttributeValue(i));
                                    serializer.attributeBytesBase64(
                                            null,
                                            attributeName,
                                            java.util.Base64.getDecoder().decode(parser.getAttributeValue(i)));
                                    break;
                                default:
                                    serializer.attribute(null,attributeName, parser.getAttributeValue(i));
                            }
                        }else{
                            serializer.attribute(null,attributeName, parser.getAttributeValue(i));
                        }



                    }
                    break;
                case END_TAG:
                    serializer.endTag(null, parser.getName());
                    break;
                case TEXT:
                    serializer.text(parser.getText());
                    break;
                case END_DOCUMENT:
                    serializer.endDocument();
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }while (event != END_DOCUMENT);


        reader.close();
        byte[] result = writer.toByteArray();
        return result;
    }

}
