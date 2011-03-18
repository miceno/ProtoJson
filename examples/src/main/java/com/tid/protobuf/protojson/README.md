
ProtoJsonFormat is a new text representation of a protocol buffer in JSON.
It is a representation for the message as a set of pairs keyname:value, 
where the key is the tag number of the field.

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

The ProtoJsonFormat representation is just 188 chars:
    {"1": "feed1","2": "Feed number 1",
     "3": [ {"1": 1,"5": "http://www.tid.es","8": {"1": "orestes","2": "bluevia"}},
            {"1": 2,"5": "http://www.google.es","8": {"1": "orestes","2": "bluevia"}}
            ]}

It is based on the original code of TextFormat and JsonFormat.

JsonFormat: http://code.google.com/p/protobuf-java-format/
TextFormat: http://code.google.com/apis/protocolbuffers/docs/reference/java/com/google/protobuf/TextFormat.html

