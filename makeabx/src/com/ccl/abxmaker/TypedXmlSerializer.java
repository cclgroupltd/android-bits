package com.ccl.abxmaker;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 * Specialization of {@link XmlSerializer} which adds explicit methods to
 * support consistent and efficient conversion of primitive data types.
 *
 * @hide
 */
public interface TypedXmlSerializer extends XmlSerializer {
    /**
     * Functionally equivalent to {@link #attribute(String, String, String)} but
     * with the additional signal that the given value is a candidate for being
     * canonicalized, similar to {@link String#intern()}.
     */
    XmlSerializer attributeInterned( String namespace,  String name,
                                     String value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeBytesHex( String namespace,  String name,
                                     byte[] value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeBytesBase64( String namespace,  String name,
                                        byte[] value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeInt( String namespace,  String name,
                                int value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeIntHex( String namespace,  String name,
                                   int value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeLong( String namespace,  String name,
                                 long value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeLongHex( String namespace,  String name,
                                    long value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeFloat( String namespace,  String name,
                                  float value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeDouble( String namespace,  String name,
                                   double value) throws IOException;

    /**
     * Encode the given strongly-typed value and serialize using
     * {@link #attribute(String, String, String)}.
     */
    XmlSerializer attributeBoolean( String namespace,  String name,
                                    boolean value) throws IOException;
}

