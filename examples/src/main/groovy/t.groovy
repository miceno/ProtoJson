
import java.nio.ByteBuffer;

/*

String s= "aeiouáéíóú"
def bytes= s.getBytes( "UTF8")

println "size= ${s.size()}"

def i=10
s.each{ println "char ${i++}: $it"}

i=10
bytes.each{
//    println "byte ${i++}: ${it >0? it: it+128}"
//Integer numero= it as Integer
    println "byte ${i++}: ${Integer.toBinaryString( it)}"
}

s.each{ 
def value= it.toCharacter() as Integer
println value
}
*/
// println "enteros " +  value


def test=[ 65, 66, 67, 68, 69, 125, 128, 255, 256, 511, 512, 1023, 1024, 33333 ]

/*
// Test conversion of ints to array of bytes
test.each{
  def b = ByteBuffer.allocate(4).putInt( it).array();
  
  s= new String( b, "UTF8")
  println "num: $it, array: $b, string: ${s.getBytes("UTF8")}"
}

def integerToString( def number){
    def b = ByteBuffer.allocate(2).putChar( number as Character);

    return new String( b.array(), "UTF8")
}

def secondtest= test
def result= []
secondtest.each{
  s= integerToString( it)
  print "num: $it"
  print "; array: "
  print s.getBytes( "UTF8")*.collect{ Integer.toHexString( it) }.join( ",")
      print "; string: ${s}\n"
  // result.add( b)
}
*/
/*
test.each{
    Integer i= it
    def c= it as Character
    def template= "num: $it, int: $i, hex: ${Integer.toHexString( it)},  char: $c"
    print template
    String s= c
    println ", numeric: ${Character.getNumericValue( c)}, string: $s, bytes: ${s.getBytes("UTF8")}"
}
*/

/**
 * converts an integer to a character in UTF8
 */
Character toUtf8( Integer tag) {
    return tag as Character
}

def identifier= ""
test.each{
    Character u= toUtf8( it)
    String f= u
    Integer n= u
    println "char: $u, int: $n, string: $f, bytes: ${f.getBytes("UTF8")}"
    identifier += u
}

println "identifier: $identifier, array: ${identifier.getBytes("UTF8")}, size: ${identifier.getBytes("UTF8").size()}"

println "size= ${identifier.size()}"
(0..(identifier.size()-1)).each{
    Character c= identifier.getAt(it) as Character
    println """char: ${c}, int: ${c as Integer}"""
}