ProtoJsonIndexedFormat
======================

ProtoJsonIndexedFormat is a Java class for transforming a Protocol Buffer message into a 
text representation in JSON, and viceversa.

It is a representation for the message as a list of fields. The first field is 
an index of all the fields in the message.

Thus for a message defined as:

    message User{
        required string uuid    = 1;
        required string domain  = 2;
    
        optional int32  number  = 8;
    }

    message Entry {
        required int32  id      = 1;
    
        optional User   author  = 4;
        optional string link    = 5;
    
        optional int32  updated  = 6;
        optional int32  publised = 7;
    
        optional User   owner    = 8;
    
        extensions 10 to 20;
        extensions 21 to 30;
    }

    message Feed{
        optional string id      = 1;
        required string title   = 2;
    
        repeated Entry  entry   = 3;
    }

    extend Entry{
        optional string source  = 10;
    }

The TextFormat representation is 255 chars long:

    id: "feed1"
    title: "Feed number 1"
    entry {
      id: 1
      link: "http://www.tid.es"
      owner {
        uuid: "orestes"
        domain: "bluevia"
      }
    }
    entry {
      id: 2
      link: "http://www.google.es"
      owner {
        uuid: "orestes"
        domain: "bluevia"
      }
      [com.tid.protojson.source]: "feed de origen"
    }

The ProtoJsonIndexedFormat representation is just 175 chars (for readability I included newlines):
    ["123","feed1","Feed number 1",
     [ ["158",1,"http://www.tid.es",["12","orestes","bluevia"]],
       ["158:",2,"http://www.google.es",["12","orestes","bluevia"],"feed de origen"]]]

Usage
======

To output a string from a message just call the printToString static method:

    // Get a message builder for message type Feed
    Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
    // Add fields
    ...
    // Build the message
    Feedmessage.Feed feed= builder.build();
    // Print the JSON String
    String sc= ProtoJsonIndexedFormat.printToString( feed);
    System.out.println( "json Format= " + sc);
    

To build a message from a string call the merge static method:
    
    Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
    String jsonFormat= ___ a JSON text string ___
    ProtoJsonIndexedFormat.merge( jsonFormat, builder);
    Feedmessage.Feed feed= builder.build();

Extensions
===========

In case you know the extensions you should provide them as a parameter to merge:

    Feedmessage.Feed.Builder builder = Feedmessage.Feed.newBuilder();
    String jsonFormat= ___ a JSON text string properly escaped ___
    // The extension registry
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add( Feedmessage.source);
    
    ProtoJsonIndexedFormat.merge( jsonFormat, registry, builder);
    Feedmessage.Feed feed= builder.build();


Credits
========


It is based on the original code of TextFormat and JsonFormat.

JsonFormat: http://code.google.com/p/protobuf-java-format/
TextFormat: http://code.google.com/apis/protocolbuffers/docs/reference/java/com/google/protobuf/TextFormat.html
