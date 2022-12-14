import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        List<Packet> packets = new ArrayList<>();

        packets.add(new Packet("[[2]]"));
        packets.add(new Packet("[[6]]"));

        for (var line : lines) {
            if (line.isEmpty())
                continue;
            packets.add(new Packet(line));
        }

        packets.sort(new PacketSorter());

        // the way this was written has them sorted the reverse of what is needed
        // this is the lazy fix
        Collections.reverse(packets);

        var result = 1;
        for (var i = 0; i < packets.size(); i++) {
            var packet = packets.get(i);
            System.out.println(packet);

            // lazy string checking!
            var pstring = packet.toString();
            if (pstring.equals("[[2]]") || pstring.equals("[[6]]")) {
                result *= i + 1;
            }
        }
        System.out.println(result);
    }
}

class PacketSorter implements Comparator<Packet> {
    public int compare(Packet a, Packet b) {
        var i = 0;

        while (true) {
            if (i == a.Data.size() && i == b.Data.size()) {
                return 0;
            }
            if (i > a.Data.size() - 1) {
                return 1;
            }
            if (i > b.Data.size() - 1) {
                return -1;
            }

            var left = a.Data.get(i);
            var right = b.Data.get(i);

            i++;

            if (!left.IsPacket && !right.IsPacket) {
                // System.out.printf("compare %d vs %d\n", left.data, right.data);
                if (left.data < right.data) {
                    return 1;
                } else if (left.data.equals(right.data)) {
                    continue;
                } else {
                    return -1;
                }
            } else if (left.IsPacket && right.IsPacket) {
                // System.out.printf("compare packets %s vs %s\n", left.packet, right.packet);
                var cmp = compare(left.packet, right.packet);
                if (cmp != 0) {
                    return cmp;
                }
            } else if (left.IsPacket && !right.IsPacket) {
                var rightData = new ArrayList<PacketEntry>();
                rightData.add(new PacketEntry(right.data));
                var rightPacket = new Packet(rightData);
                var cmp = compare(left.packet, rightPacket);
                if (cmp != 0) {
                    return cmp;
                }
            } else if (!left.IsPacket && right.IsPacket) {
                var leftData = new ArrayList<PacketEntry>();
                leftData.add(new PacketEntry(left.data));
                var leftPacket = new Packet(leftData);
                var cmp = compare(leftPacket, right.packet);
                if (cmp != 0) {
                    return cmp;
                }
            } else {
                throw new RuntimeException("unexpected state");
            }
        }
    }
}
