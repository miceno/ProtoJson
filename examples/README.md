A set of text formats to represent a protocol buffer in JSON.

ProtoJson
===========
ProtoJson is a new text representation of a protocol buffer in JSON.
Instead of representing the message as a set of pairs keyname:value, it
uses the tag number id.

It is based on the original code of TextFormat and JsonFormat.

JsonFormat: http://code.google.com/p/protobuf-java-format/
TextFormat: http://code.google.com/apis/protocolbuffers/docs/reference/java/com/google/protobuf/TextFormat.html

ProtoJsonIndexed
===========

This is version 2 of the text representation. It is based on using an index that
aggregates all the tag numbers of the fields in the message. In addition, we use a 
list representation instead of an object representation.

This representation is an improvement in space over ProtoJson, with an average reduction 
in size of 10%.

Take the following message in proto language representation:

    message Entry{
        required string id      = 1;
        required string title   = 2;
        required string summary = 3;
        
        optional string link    = 5;
        
        optional int32  updated  = 6;
        optional int32  published = 7;
    }

Taken the message is
    id= "myid"
    title= "the title"
    summary= "this is a summary"
    link= "http://www.tid.es"
    published= 123456777

The representation would be:

    [ "12357",  // index
      "myid", 
      "the title", 
      "this is a summary", 
      "http://www.tid.es", 
      123456777 ]

The index field is the concatenation of all the number tags of fields 
that are included in the message. Each tag is represented as an UTF-8 
character starting from ASCII char 48 ("0"). This way, tag numbers bellow
127 would be represented by a printable character in the ASCII range from
48-127. Tag numbers greater than 127 would be represented as 2-byte chars.


FAQ
===

What is the difference of ProtoJson vs. ProtoJsonIndexed? 

ProtoJson is version 1 of TID Protocol Buffer JSON serialization.
ProtoJsonIndexed is version 2 of TID Protocol Buffer JSON serialization.

What does the "indexed" in ProtoJsonIndexed mean?

The "indexed" version means we use a compact representation of the tag numbers
included in a message.
