import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Packet {
    public List<PacketEntry> Data;

    public Integer Size = 0;

    public Packet(String src) {
        this(src.toCharArray());
    }

    public Packet(char[] chars) {
        this.Size = 1;
        this.Data = new ArrayList<>();

        // a packet always starts with a '[' and goes until the next `]`
        if (chars[0] != '[') {
            throw new RuntimeException("packet did not start with '['");
        }

        String next = "";
        outer: for (var i = 1; i < chars.length;) {
            var c = chars[i];
            switch (c) {
                case '[':
                    var nested = new Packet(Arrays.copyOfRange(chars, i, chars.length));
                    this.Data.add(new PacketEntry(nested));
                    i += nested.Size;
                    this.Size += nested.Size;
                    break;
                case ']':
                    i++;
                    this.Size++;
                    break outer;
                case ',':
                    i++;
                    this.Size++;
                    if (!next.isEmpty()) {
                        var v = Integer.parseInt(next);
                        this.Data.add(new PacketEntry(v));
                        next = "";
                    }
                    break;
                default:
                    i++;
                    this.Size++;
                    next += c;
                    break;
            }
        }

        // add last value
        if (!next.isEmpty()) {
            var v = Integer.parseInt(next);
            this.Data.add(new PacketEntry(v));
        }
    }

    public String toString() {
        String ret = "[";
        for (var i = 0; i < this.Data.size(); i++) {
            var pe = this.Data.get(i);
            ret += pe.toString();
            if (i < this.Data.size() - 1) {
                ret += ",";
            }
        }
        ret += "]";
        return ret;
    }
}
