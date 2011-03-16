

import com.tid.protojson.Feedmessage;
import com.tid.protojson.Feedmessage.Entry;

import com.google.protobuf.*;

import com.tid.protobuf.jsonc.*;
import com.tid.protobuf.jsone.*;

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

        Message someProto = Feedmessage.Entry.newBuilder()
                            .setId( "1")
                            .setTitle( "titulo")
                            .setSummary( "esto es el resumen")
                            .setLink( "http://www.tid.es")
                            .setOwner( user)
                            .build();
                            
                            
        String stf = TextFormat.printToString( someProto);
        System.out.println( "textformat= " + stf);
        
        String jsonFormat = JsonFormat.printToString(someProto);
        System.out.println( "jsonFormat= " + jsonFormat);

        String jsoncFormat = JsoncFormat.printToString(someProto);
        System.out.println( "json Compressed Format= " + jsoncFormat);

        String jsoneFormat = JsoneFormat.printToString(someProto);
        System.out.println( "json Enhanced Format= " + jsoneFormat);

        Feedmessage.Entry.Builder builder = Feedmessage.Entry.newBuilder();
        try{
            // JsonFormat test
            JsonFormat.merge(jsonFormat, builder);
            Message m= builder.build();
            String s= JsonFormat.printToString( m);
            System.out.println( "jsonFormat= " + jsonFormat);

            // Json Compressed Format test            
            JsoncFormat.merge( jsoncFormat, builder);
            Message mc= builder.build();
            String sc= JsoncFormat.printToString( mc);
            System.out.println( "json Compressed Format= " + sc);

            // Json Enhanced Format test            
            JsoneFormat.merge( jsoneFormat, builder);
            Message me= builder.build();
            String se= JsoneFormat.printToString( me);
            System.out.println( "json Enhanced Format= " + se);
        }
        catch (JsonFormat.ParseException p){            
        }
        catch (Exception e){
            System.out.println( e);
        }
    }

}