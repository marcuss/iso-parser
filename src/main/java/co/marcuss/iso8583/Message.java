package co.marcuss.iso8583;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcus Sanchez sanchez.marcus@gmail.com<br/>
 *         <a ref="https://github.com/marcuss">https://github.com/marcuss</a>
 */
public final class Message {

    private String mti;

    private final Map<Integer, String> bitmaps;

    public Message() {
        this.mti ="";
        this.bitmaps = new HashMap<>();
    }

    public String getMti() {
        return this.mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public void addBitmap(int num, String content) {
        this.bitmaps.put(num, content);
    }

    public Map<Integer, String> getBitmaps() {
        return Collections.unmodifiableMap(new HashMap<>(this.bitmaps));
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("MTI : ").append(getMti()).append("\n");
        for (Map.Entry<Integer, String> entry : this.bitmaps.entrySet()) {
            builder.append(String.format(
                    "Field-%d: %s\n",
                    entry.getKey(),
                    entry.getValue())
            );
        }
        return builder.toString();
    }

}
