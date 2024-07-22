# makeabx
java module for compiling XML files to the abx format

## Command line usage

`java -jar makeabx.jar <input.xml> <optional output.xml>`

```
Converts an XML file to an ABX file (the output will have the same path as the input if none was specified
with an '.abx' extension added).

If one of the following suffixes are found on an attribute's name, they will be
treated as typed values:
-int, -inthex -long -longhex -float -double -bool -byteshex -base64
eg. <element myvalue-inthex='4a7f'>Attribute will be parsed as hex</element>

```

## Building
`mvn clean install

## Already built version
available at the "dist" directory
