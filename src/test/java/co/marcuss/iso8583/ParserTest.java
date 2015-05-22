package co.marcuss.iso8583;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Marcus Sanchez sanchez.marcus@gmail.com<br/>
 *         <a ref="https://github.com/marcuss">https://github.com/marcuss</a> <br/>
 *         This class test the parsing of the following message<br/>
 *         Message ISO 8583 for finantial transactions.<br/>
 *         0210
 *         B238000102C080040000000000000002100000000000001700010814465469421614465701081100301000000N399915444303500019991544986020Invalid
 *         response!!!009000095492<br/>
 *         Message info:<br/>
 *         Message Type Indicator: {0210}<br/>
 *         version: ISO8583-1:1987<br/>
 *         Type: Issuer response to request for funds Further.<br/>
 *         Further reading: <a
 *         ref="http://en.wikipedia.org/wiki/ISO_8583">http:/
 *         /en.wikipedia.org/wiki/ISO_8583<a/><br/>
 */
public class ParserTest {
    private static final String DATA = "0210B238000102C080040000000000000002100000000000001700010814465469421614465701081100301000000N399915444303500019991544986020 Invalid response!!!009000095492";

    @Test
    public void parseValidMessage() {
        Message message = JPosParser.make("src/main/resources/ISOMSG.xml")
                .parse(this.DATA);
        assertThat(message, allOf(any(Message.class), notNullValue()));
        assertThat(message.getMti(), equalTo("0210"));
        assertThat(message.getBitmaps().size(), equalTo(13));
        System.out.println(message);
    }
    
    @Test
    public void validToStringMessageFormat() {
        Message message = JPosParser.make("src/main/resources/ISOMSG.xml")
                .parse(this.DATA);
        assertThat(message.toString(), startsWith(
                  "MTI : 0210\n"
                + "Field-32: 00301000000\n" 
                + "Field-3: 100000\n"
                + "Field-4: 000000001700\n" 
                + "Field-7: 0108144654\n"
                + "Field-39: N3\n" 
                + "Field-41: 99915444\n"
                + "Field-42: 303500019991544\n" 
                + "Field-11: 694216\n"
                + "Field-12: 144657\n" 
                + "Field-13: 0108\n" 
                + "Field-49: 986\n"
                + "Field-62:  Invalid response!!!\n" 
                + "Field-127: 000095492"));
    }

    @Test
    public void parserCreation_JposParserSuccess() {
        assertThat(
                JPosParser.make("src/main/resources/ISOMSG.xml"),
                any(Parser.class)
        );
    }

    @Test(expected = JPosParser.NoConfigurationFileException.class)
    public void parserCreationWithNoConfigFile_ShouldThrowException() {
        JPosParser.make("");
    }

    @Test(expected = JPosParser.ConfigurationException.class)
    public void parserCreationWithInvalidConfigFile_ShouldThrowException() {
        JPosParser.make("src/test/resources/ISOMSG.xml");
    }

    @Test(expected=JPosParser.ParseException.class)
    public void parseInvalidMessage_ShouldThrowException() {
        JPosParser.make("src/main/resources/ISOMSG.xml")
        .parse("InvalidMessage");
    }
}
