
import com.google.protobuf.*;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;


class ProtoGenerator{

    private final Appendable output;
    private final StringBuilder indent = new StringBuilder();
    private boolean atStartOfLine = true;

    public ProtoGenerator(Appendable output) {
        this.output = output;
    }

    /**
     * Indent text by two spaces. After calling Indent(), two spaces will be inserted at the
     * beginning of each line of text. Indent() may be called multiple times to produce deeper
     * indents.
     */
    public void indent() {
        indent.append("  ");
    }

    /**
     * Reduces the current indent level by two spaces, or crashes if the indent level is zero.
     */
    public void outdent() {
        int length = indent.length();
        if (length == 0) {
            throw new IllegalArgumentException(" Outdent() without matching Indent().");
        }
        indent.delete(length - 2, length);
    }
        
    public void println(CharSequence data) throws IOException {
        this.print( data + "\n");
    }
    

    /**
     * Print text to the output stream.
     */
    public void print(final CharSequence text) throws IOException {
        final int size = text.length();
        int pos = 0;
        
        for (int i = 0; i < size; i++) {
            if (text.charAt(i) == '\n') {
                // System.out.println( "pos="+ pos + " i=" + i + ", size=" + size);
                write(text.subSequence(pos, i+1), i - pos + 1);
                pos = i + 1;
                // System.out.println( "pos="+ pos + " i=" + i + ", size=" + size);
                atStartOfLine = true;
            }
        }
        write(text.subSequence(pos, size), size - pos);
    }

    private void write(final CharSequence data, int size) throws IOException {
        // System.out.println( "size=" + size);
        
        if (size == 0) {
            return;
        }
        if (atStartOfLine) {
            // System.out.println( "at start of line");
            atStartOfLine = false;
            output.append(indent);
        }
        output.append(data);
        // output.append( "*debug*");
    }
    
    
    void println( String name, List<?> l)throws IOException{
        // Show unindented
        println(name, l, false);
    }

    void println( String name, List<?> l, boolean indent)throws IOException{
        println( "Printing list " + name);
        indent();
        for (Object s: l) {
            println( "element: " + s);
        }
        outdent();
        println( "End Printing list " + name);
    }

    void showFields( String name, List<Descriptors.FieldDescriptor> l)throws IOException{
        println( "Printing Fields for " + name);

        indent();
        for (Descriptors.FieldDescriptor s: l) {
            println( "fullname: " + s.getFullName());
            
            indent();
            println( "name: " + s.getName());
            println( "index: " + s.getIndex());
            println( "number: " + s.getNumber());
            println( "isExtension: " + s.isExtension());
            println( "isRequired: " + s.isRequired());
            println( "isOptional: " + s.isOptional());
            println( "isPackable: " + s.isPackable());
            println( "isPacked: " + s.isPacked());
            println( "isRepeated: " + s.isRepeated());
            outdent();
            
            if( s.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE){
                indent();
                println( s.getMessageType());
                outdent();
            }
        }
        outdent();
        println( "End Printing list " + name);
    }
    
    void println( Descriptors.Descriptor d)throws IOException{
        println(" *****  begin show  *****");

        indent();
        println( "fullname: " + d.getFullName());
        println( "name: " + d.getName());
        println( "index: " + d.getIndex());
        println( "name: " + d.getName());
 
        println( "containing type: " + d.getContainingType());
        println( "enum types: ", d.getEnumTypes());
        println( "extensions: ", d.getExtensions());
        println( "NestedTypes: ", d.getNestedTypes());
        showFields( "fields: ", d.getFields());
        outdent();
        println(" *****  end show  *****");
    }

}