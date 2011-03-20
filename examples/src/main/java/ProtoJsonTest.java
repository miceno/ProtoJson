

import com.tid.protojson.Feedmessage;
import com.tid.protojson.Feedmessage.Entry;

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

//import java.text.ParseException;

class ProtoJsonTest{
    public static void main(String[] args) throws IOException {

        ProtoGenerator generator= new ProtoGenerator( System.out);
        
        System.out.println( "Starting the example");

        Feedmessage.User user = Feedmessage.User.newBuilder()
                            .setUuid( "orestes")
                            .setDomain( "bluevia")
                            .build();

        Feedmessage.Entry entry1 = Feedmessage.Entry.newBuilder()
                            .setId( 1)
                            .setLink( "http://www.tid.es")
                            .setOwner( user)
                            .build();

        Feedmessage.Entry entry2 = Feedmessage.Entry.newBuilder()
                            .setId( 2)
                            .setLink( "http://www.google.es")
                            .setOwner( user)
                            .setExtension( Feedmessage.source, "feed de origen")
                            .build();
                            
        Feedmessage.Feed feed = Feedmessage.Feed.newBuilder()
            .setId( "feed1")
            .setTitle( "Feed number 1")
            .addEntry( entry1)
            .addEntry( entry2)
            .build();
                            
        System.out.println( "***** Testing output *****");
        test( entry1, "entry1");
        test( entry2, "entry2");
        test( feed, "feed");
        


        System.out.println( "***** Testing input *****");
        
        testTextFormat( feed);
        testTextFormatFile( new File( "textformat.txt"));
        
        try{
            // TextFormat test
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
            String textFormat = TextFormat.printToString( feed);

            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);
            
            // Read message from text
            TextFormat.merge(textFormat, registry, builder);
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


        try{
            // JsonFormat test
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
            String jsonFormat = JsonFormat.printToString( feed);
            
            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);

            // Read message from text
            JsonFormat.merge(jsonFormat, registry, builder);
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

        try{
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
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

        try{
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
            // Json Compressed Format test            
            String jsonFormat = ProtoJsonFormat.printToString( feed);
            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);

            // Read message from text
            ProtoJsonFormat.merge( jsonFormat, registry, builder);
            Message mc= builder.build();
            // generator.println( mc.getDescriptorForType());
            String sc= ProtoJsonFormat.printToString( mc);
            System.out.println( "ProtoJSON Format with extensions= " + sc);
        }
        catch (Exception e){
            System.out.println( e);
        }

        try{
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
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

    static void testTextFormat( Message feed) throws IOException{
        
        try{
            // TextFormat test
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
            String textFormat = TextFormat.printToString( feed);

            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);
        
            // Read message from text
            TextFormat.merge(textFormat, registry, builder);
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
    
    static void testTextFormatFile( File f) throws IOException {
        
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