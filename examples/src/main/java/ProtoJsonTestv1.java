

import com.tid.protojson.Feedmessagev1;
import com.tid.protojson.Feedmessagev1.*;

import com.google.protobuf.*;

import com.tid.protobuf.protojson.*;
import com.tid.protobuf.protojsonindexed.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;

import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;

import java.nio.charset.Charset;

import java.io.BufferedReader;
import java.io.BufferedInputStream;

//import java.text.ParseException;

/**
 * ProtoJsonTestv1: test class for using version 1 of the protocol
 */

class ProtoJsonTestv1{
    
    final private static String UTF8= "UTF-8";
    
    private static Message buildFeed(){
        Feedmessagev1.Entry entry1 = Feedmessagev1.Entry.newBuilder()
                            .setId( 1)
                            .setLink( "http://www.tid.es")
                            .build();
        Feedmessagev1.Entry entry2 = Feedmessagev1.Entry.newBuilder()
                            .setId( 2)
                            .setLink( "http://www.google.es")
                            .build();

        Feedmessagev1.Feed feed = Feedmessagev1.Feed.newBuilder()
            .setId( "feed1")
            .setTitle( "Feed number 1")
            .addEntry( entry1)
            .addEntry( entry2)
            .build();
    
        return feed;    
        
    }
    public static void main(String[] args) throws IOException {


        System.out.println( "***** Testing output *****");
        Message feed= buildFeed();
        
        test( feed, "feed");
        
        System.out.println( "***** Testing input *****");
                
        testTextFormat( );
        testTextFormatFile( new File( "textformat.txt"));
        
        testJsonFormat();
        
        testProtoJson();

        testProtoJsonExtensions();

        testProtoJsonIndexed();

    }

    static void testProtoJsonIndexed( ) throws IOException{
        ProtoGenerator generator= new ProtoGenerator( System.out);
        
        Message feed= buildFeed();
        try{
            Feedmessagev1.Feed.Builder builder = Feedmessagev1.Feed.newBuilder();
            // Json indexed Format test            
            String jsonindexedFormat = ProtoJsonIndexedFormat.printToString( feed);
            ProtoJsonIndexedFormat.merge( jsonindexedFormat, builder);
            Message me= builder.build();
            generator.println( me.getDescriptorForType());
            String se= ProtoJsonIndexedFormat.printToString( me);
            System.out.println( "json indexed Format= " + se);
        }
        catch (Exception e){
            System.out.println( e);
        }
    }

    static void testProtoJsonExtensions( ) throws IOException{
        ProtoGenerator generator= new ProtoGenerator( System.out);
        
        Message feed= buildFeed();
        try{
            Feedmessagev1.Feed.Builder builder = Feedmessagev1.Feed.newBuilder();
            // Json Compressed Format test            
            String jsonFormat = ProtoJsonFormat.printToString( feed);

            // Read message from text
            ProtoJsonFormat.merge( jsonFormat, builder);
            Message mc= builder.build();
            // generator.println( mc.getDescriptorForType());
            String sc= ProtoJsonFormat.printToString( mc);
            System.out.println( "ProtoJSON Format with extensions= " + sc);
        }
        catch (Exception e){
            System.out.println( e);
        }
    }

    static void testProtoJson( ) throws IOException{
        ProtoGenerator generator= new ProtoGenerator( System.out);
        
        Message feed= buildFeed();
        try{
            Feedmessagev1.Feed.Builder builder = Feedmessagev1.Feed.newBuilder();
            // Json Compressed Format test            
            String jsonFormat = ProtoJsonFormat.printToString( feed);

            // Read message from text
            ProtoJsonFormat.merge( jsonFormat, builder);
            Message mc= builder.build();
            // generator.println( mc.getDescriptorForType());
            String sc= ProtoJsonFormat.printToString( mc);
            System.out.println( "ProtoJSON Format without extensions= " + sc);
        }
        catch (Exception e){
            System.out.println( e);
        }
    }

    static void testJsonFormat( ) throws IOException{
        ProtoGenerator generator= new ProtoGenerator( System.out);
        
        Message feed= buildFeed();
        try{
            // JsonFormat test
            Feedmessagev1.Feed.Builder builder = Feedmessagev1.Feed.newBuilder();
            String jsonFormat = JsonFormat.printToString( feed);
            
            // Read message from text
            JsonFormat.merge(jsonFormat, builder);
            Message m= builder.build();
            // generator.println( m.getDescriptorForType());
            String s= JsonFormat.printToString( m);
            System.out.println( "jsonFormat= " + s);
        }
        catch (JsonFormat.ParseException p){            
            System.out.println( p);
        }
        catch (Exception e){
            System.out.println( e);
        }
    }


    static void testTextFormat( ) throws IOException{
        ProtoGenerator generator= new ProtoGenerator( System.out);
        
        Message feed= buildFeed();
        try{
            // TextFormat test
            Feedmessagev1.Feed.Builder builder = Feedmessagev1.Feed.newBuilder();
            String textFormat = TextFormat.printToString( feed);

            // Read message from text
            TextFormat.merge(textFormat, builder);
            Message m= builder.build();
            String s= TextFormat.printToString( m);
            System.out.println( "textFormat= " + s);
        }
        catch (TextFormat.ParseException p){            
            System.out.println( p);
        }
        catch (Exception e){
            System.out.println( e);
        }
    }
    
    static private String readFileToString( File file) throws java.io.FileNotFoundException, IOException {
      String result= null;
      try{
        FileInputStream stream = new FileInputStream(file);
        try {
          FileChannel fc = stream.getChannel();
          MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
          /* Instead of using default, pass in a decoder. */
          // Old code: return Charset.defaultCharset().decode(bb).toString();
          result= Charset.forName( UTF8).decode(bb).toString();
        }
        finally {
          stream.close();
        }
      }
      catch( java.io.FileNotFoundException e){
          System.out.println( "exception: file not found");
          result= null;
      }
      return result;
    }

    static private String readFileToString2( File file) throws java.io.FileNotFoundException, IOException {
        byte[] buffer = new byte[(int) file.length()];
        BufferedInputStream f = null;
      try{
        try {
            f = new BufferedInputStream(new FileInputStream(file));
            f.read(buffer);
        } finally {
            if (f != null) try { f.close(); } catch (IOException ignored) { }
        }
      }
      catch( java.io.FileNotFoundException e){
          System.out.println( "exception2: file not found");          
      }
        return new String(buffer);
    }
    
    static void testTextFormatFile( File file) throws IOException {
        
        String str1= readFileToString( file);
        String str2= readFileToString2( file);
        
        System.out.println( "str1=" + str1);
        System.out.println( "str2=" + str2);
        
    }
    
    static void test( Message m, String display) throws IOException{
        ProtoGenerator g= new ProtoGenerator( System.out);
            g.println( "Printing " + display);

            g.indent();
            
            String stf = TextFormat.printToString( m);
            g.println( "**** Showing TextFormat output ****");
            g.println( stf);
            
            String jsonFormat = JsonFormat.printToString(m);
            g.println( "**** Showing JsonFormat output ****");
            g.println( jsonFormat);
            
            String protojsonFormat = ProtoJsonFormat.printToString(m);
            g.println( "**** Showing ProtoJSON output ****");
            g.println( protojsonFormat);

            String jsonindexedFormat = ProtoJsonIndexedFormat.printToString(m);
            g.println( "**** Showing ProtoJsonIndexedFormat output ****");
            g.println( jsonindexedFormat);

            g.outdent();
    }

}