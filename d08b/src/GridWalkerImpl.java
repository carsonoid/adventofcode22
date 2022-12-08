public class GridWalkerImpl implements GridWalker {
    public void HandleCell(int x, int y, int value) {
        System.out.printf("Handling (%d, %d) - %d\n", x, y, value);
    }
}
