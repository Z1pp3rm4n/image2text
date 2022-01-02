# **Image2Text**

Convert an image to Braille or ASCII representation \
Print to terminal if no output file is given

Commands:
> - braille [params] imageFile

imageFile: input image \
-f, --fill-gaps: (required)   fill blank braille with 1-dot braille\
-i, --invert:      invert color of braille string \
-o, --output-file: a file to print the result to \
-t, --threshold:  an 8-bit value (0-255) for thresholding image to binary \
-w, --string-width:width of output string
  
> - ascii [params] imageFile

-o, --output-file= 
     a file to print the result to \
     -w, --string-width=<stringWidth>
     width of output string 
    

Also contains library for custom image to grayscale conversion (linear, weighted, gamma correct, etc...)