

import com.tid.protojson.Feedmessage;
import com.tid.protojson.Feedmessage.Entry;

import com.google.protobuf.*;

import com.tid.protobuf.jsonc.*;

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

        Message someProto = Feedmessage.Entry.newBuilder()
                            .setId( "1")
                            .setTitle( "titulo")
                            .setSummary( "resumen")
                            .build();
                            
        String stf = TextFormat.printToString( someProto);
        System.out.println( "textformat= " + stf);
        
        String jsonFormat = JsonFormat.printToString(someProto);
        System.out.println( "jsonFormat= " + jsonFormat);

        String jsoncFormat = JsoncFormat.printToString(someProto);
        System.out.println( "json Compressed Format= " + jsoncFormat);

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
        }
        catch (JsonFormat.ParseException p){            
        }
        catch (Exception e){
            System.out.println( e);
        }
    }

}