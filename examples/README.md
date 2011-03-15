A set of text formats to represent a protocol buffer in JSON.

JsoncFormat
===========
JsoncFormat is a new text representation of a protocol buffer in JSON.
Instead of representing the message as a set of pairs keyname:value, it
uses the field number id.

It is based on the original code of TextFormat and JsonFormat.

JsonFormat: http://code.google.com/p/protobuf-java-format/
TextFormat: http://code.google.com/apis/protocolbuffers/docs/reference/java/com/google/protobuf/TextFormat.html

JsoneFormat
===========

This is version 2 of the text representation. It is based on enhancing the
key of the JSON, using a list representation instead of an object
representation.

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

What is the difference of JsoncFormat vs. JsoneFormat? 

JsoncFormat is version 1 of TID Protocol Buffer JSON serialization.
JsoneFormat is version 2 of TID Protocol Buffer JSON serialization.

What does the "c" in JsoncFormat mean?

The "c" stands for "compact". It produces a more compact text representation
than the standard JSON object notation.

What does the "e" in JsoneFormat mean?

The "e" stands for "enhanced". It produces a JSON compact representation of
a Protocol Buffers message by using JSON lists of elements instead of 
JSON objects.

