
import java.util.ArrayList;

class conversion {
 
 
public static void main(String[] args) {
    
    int[] test={ 1, 2, 3, 4, 65, 66, 67, 68, 69, 125, 128, 255, 256, 511, 512, 1023, 1024, 33333 };
/*
    List<Integer> test = new ArrayList<Integer>();
    for (int index = 0; index < ints.length; index++)
    {
        test.add( ints[index]);
    }
*/    
/*
    for (int index = 0; index < test.length; index++)
    {
        System.out.println( "char: " + test[ index]);
        System.out.println( "int: " + test[ index]);
        String s= (Character) new Integer( test[ index]);
        System.out.println( "string: " + s);
        System.out.println( "bytes: " + s.getBytes("UTF-8").toString());
    }

*/

try{
    
    /**
     *  It works
     
    StringBuilder s= new StringBuilder();
    for ( int index = 0; index < test.length; index++){
        System.out.println( "char: " + test[ index] + ", int: " + int2char( test[ index]));
        s.append( int2char( test[ index]));
    }
    
    System.out.println( "string index= " + s.toString());
    System.out.print( "bytes="); 
    printByteArray( s.toString().getBytes( "UTF-8"));
    
    String ss= s.toString();
    for( int index= 0; index< ss.length(); index++){
        System.out.println( "index:" + index 
            + ", codpoint: " + ss.codePointAt( index)
            + ", int: " + char2int( ss.charAt( index))
            );
    }
    */
    
    StringBuilder s= new StringBuilder();
    for ( int index = 0; index < test.length; index++){
        System.out.println( "char: " + test[ index] + ", int: " + int2char( test[ index]));
        s.appendCodePoint( test[ index] );
    }
    System.out.println( "string index= " + s.toString() 
      + ", size=" + s.length() 
      );
    byte b[]= s.toString().getBytes( "UTF-8");
    System.out.print( "bytes="); 
    printByteArray( b);
    System.out.println( "size= " + b.length);
    
    String ss= s.toString();
    for( int index= 0; index< ss.length(); index++){
        System.out.println( "index:" + index 
            + ", char: " + ss.charAt( index)
            + ", codpoint: " + ss.codePointAt( index)
            + ", int: " + (char2int( ss.charAt( index)))
            );
    }
    
    
}
catch( Exception e){
    
}

    
}   

static void printByteArray( byte [] b)
{
    System.out.print( "[");
    for( int i= 0; i< b.length; i++){
        System.out.print( Integer.toHexString( 0xFF & b[ i]) + ", ");
    }
    System.out.println( "]");
    
}


static int char2int( char c){
    return (int) c;
}


static char int2char( int i){
    return (char) i;
}

/**
 *  * converts an integer to a character in UTF8
 *   
 */
static Character toUtf8( int tag) {
    
    /*
    ByteBuffer b = ByteBuffer.allocate(4);
    b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
    b.putInt( tag);

    byte[] result = b.array();
    return new Character( i);
    */
    return (char) tag;
//    return tag as Character
//    
}

}