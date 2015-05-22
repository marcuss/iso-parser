package co.marcuss.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

/**
 * @author Marcus Sanchez sanchez.marcus@gmail.com<br/>
 *         <a ref="https://github.com/marcuss">https://github.com/marcuss</a>
 */
public class JPosParser implements Parser {

    private final GenericPackager packager;

    public static Parser make(String configuration) {
        return new JPosParser(configuration);
    }

    private JPosParser(final String  configuration) {
        if (configuration == null || configuration.isEmpty()) { throw new NoConfigurationFileException(); }
        try {
            this.packager = new GenericPackager(configuration);
        } catch (ISOException e) {
            e.printStackTrace();
            throw new ConfigurationException();
        }
    }

    @Override
    public final Message parse(final String data) {
        return parse(data.getBytes());
    }
    
    @Override
    public final Message parse(final byte[] data) {
        try {
            final ISOMsg jposParsedMessage = jPosParse(data);
            return buildResponse(jposParsedMessage);
        } catch (ISOException e) {
            e.printStackTrace();
            throw new JPosParser.ParseException();
        }
    }

    private ISOMsg jPosParse(final byte[] data) throws ISOException {
        final ISOMsg jposParsedMessage = new ISOMsg();
        jposParsedMessage.setPackager(this.packager);
        jposParsedMessage.unpack(data);
        return jposParsedMessage;
    }

    private Message buildResponse(final ISOMsg jposParsedMessage) throws ISOException {
        final Message message = new Message();
        message.setMti(jposParsedMessage.getMTI());
        for (int i = 1; i <= jposParsedMessage.getMaxField(); i++) {
            if (jposParsedMessage.hasField(i)) {
                message.addBitmap(i, jposParsedMessage.getString(i));
            }
        }
        return message;
    }

    public class NoConfigurationFileException extends RuntimeException {
    }

    public class ConfigurationException extends RuntimeException {
    }

    public class ParseException extends RuntimeException {
    }

}
