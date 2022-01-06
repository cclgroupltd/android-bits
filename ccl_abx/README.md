# ccl_abx
`ccl_abx` is a Python module for reading Android Binary XML (ABX) files and converting them back to XML for processing. The module can also be executed at the command line to convert ABX files there.

## Command line usage
To convert an ABX file at the command line:

`ccl_abx.py file_path_here.xml`

The converted data will be outputted to `STDOUT`, so if you want to save the file you can redirect the output:

`ccl_abx.py file_path_here.xml > processed_file_path.xml`

If the file being converted has multiple roots (such as the `settings_secure.xml` file) you can add the `-mr` switch to account for these multple roots:

`ccl_abx.py file_path_here.xml -mr`

## Using the module in your scripts
Use of the module is fairly straight-forward, a minimal example which reads a file provided at the command line might look like:

```python
import sys
import pathlib
import ccl_abx

input_path = pathlib.Path(sys.argv[1])
with input_path.open("rb") as f:
    reader = ccl_abx.AbxReader(f)  # Pass a binary file-like object into the AbxReader constructor
    doc = reader.read()  # Call the reader object's read() function which returns an ElementTree Element which is the root of the document

```
