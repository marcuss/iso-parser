package co.marcuss.iso8583;

/**
 * @author Marcus Sanchez sanchez.marcus@gmail.com<br/>
 *         <a ref="https://github.com/marcuss">https://github.com/marcuss</a> <br/>
 *         The implementations of this interface, parses ISO8583 messages.<br/>
 *         Further reading: <a ref="http://en.wikipedia.org/wiki/ISO_8583">
 *         http://en.wikipedia.org/wiki/ISO_8583<a/><br/>
 */
public interface Parser {

    Message parse(String data);

    Message parse(byte[] data);

}