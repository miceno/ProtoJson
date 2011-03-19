

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
    public static void main(String[] args) {

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
                            
        System.out.println( "***** output *****");
        test( entry1, "entry1");
        test( entry2, "entry2");
        test( feed, "feed");
        


        System.out.println( "****** input ******");
        try{
            // TextFormat test
            Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
            String textFormat = TextFormat.printToString( feed);
//            String jsonFormat = "{\"id\": \"feed1\",\"title\": \"Feed number 1\",\"entry\": [{\"id\": 1,\"link\": \"http://www.tid.es\",\"owner\": {\"uuid\": \"orestes\",\"domain\": \"bluevia\"}},{\"id\": 2,\"link\": \"http://www.google.es\",\"owner\": {\"uuid\": \"orestes\",\"domain\": \"bluevia\"},\"11\": \"feed de origen\"}]}";

            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);
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
//            String jsonFormat = "{\"id\": \"feed1\",\"title\": \"Feed number 1\",\"entry\": [{\"id\": 1,\"link\": \"http://www.tid.es\",\"owner\": {\"uuid\": \"orestes\",\"domain\": \"bluevia\"}},{\"id\": 2,\"link\": \"http://www.google.es\",\"owner\": {\"uuid\": \"orestes\",\"domain\": \"bluevia\"},\"11\": \"feed de origen\"}]}";
            
            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);
            JsonFormat.merge(jsonFormat, registry, builder);
            Message m= builder.build();
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
            ExtensionRegistry registry = ExtensionRegistry.newInstance();
            registry.add( Feedmessage.source);
            ProtoJsonFormat.merge( jsonFormat, registry, builder);
            Message mc= builder.build();
            String sc= ProtoJsonFormat.printToString( mc);
            System.out.println( "ProtoJSON Format= " + sc);
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
            String se= ProtoJsonIndexedFormat.printToString( me);
            System.out.println( "json indexed Format= " + se);
        }
        catch (Exception e){
            System.out.println( e);
        }
    }

    static void test( Message m, String display){
            System.out.println( "Printing prueba " + display);
            
            String stf = TextFormat.printToString( m);
            System.out.println( "textformat ***************\n" + stf);

            String jsonFormat = JsonFormat.printToString(m);
            System.out.println( "jsonFormat= " + jsonFormat);

            String protojsonFormat = ProtoJsonFormat.printToString(m);
            System.out.println( "ProtoJSON Format= " + protojsonFormat);

            String jsonindexedFormat = ProtoJsonIndexedFormat.printToString(m);
            System.out.println( "json indexed Format= " + jsonindexedFormat);
    }

}