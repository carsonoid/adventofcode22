import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        List<CPUInstruction> instructions = allLines.stream().map(CPUInstruction::FromLine)
                .collect(Collectors.toList());

        var screen = new SpriteScreen();
        var cpu = new CPU();
        cpu.AddInspector(screen);
        cpu.Run(instructions, 240);

    }
}
