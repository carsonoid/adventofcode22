import java.util.HashMap;
import java.util.Map;

public class DirEntry {
    String Name;
    Boolean IsDir;
    Integer Size;
    Map<String, DirEntry> Children;
    DirEntry Parent;

    public DirEntry(DirEntry parent, String line) {
        this.Parent = parent;
        this.Children = new HashMap<>();

        var parts = line.split(" ");
        // why didn't this work?
        // if (parts[0] =="dir") {
        if (parts[0].equals("dir")) {
            this.Name = parts[1];
            this.IsDir = true;
            this.Size = 0;
        } else {
            this.Name = parts[1];
            this.IsDir = false;
            this.Size = Integer.parseInt(parts[0]);
        }
    }

    public DirEntry CD(String path) {
        if (path.equals("..")) {
            if (this.Parent == null) {
                throw new RuntimeException("Cannot go up from root");
            }
            return this.Parent;
        }

        if (this.Children.containsKey(path)) {
            return this.Children.get(path);
        }
        var child = new DirEntry(this, "dir " + path);
        this.Children.put(path, child);
        return child;
    }

    public String toString() {
        if (this.IsDir) {
            return String.format("- %s (dir)", this.Name);
        }
        return String.format("- %s (file, size=%d)", this.Name, this.Size);
    }

    public void PrintAll() {
        this.PrintAll(0);
    }

    public void PrintAll(Integer indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        System.out.println(this);
        for (var child : this.Children.values()) {
            child.PrintAll(indent + 2);
        }
    }

    // TODO figure out why paths returned can have a bunch of leading slashes
    public String GetPath() {
        if (this.Parent == null) {
            return this.Name;
        }
        return this.Parent.GetPath() + "/" + this.Name;
    }

    public Integer GetSize() {
        if (this.IsDir) {
            var size = 0;
            for (var child : this.Children.values()) {
                size += child.GetSize();
            }
            return size;
        }
        return this.Size;
    }
}
