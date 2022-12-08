public class GridScorer implements GridWalker {
    Grid grid;
    int best = 0;

    public GridScorer(Grid grid) {
        this.grid = grid;
    }

    public void HandleCell(int x, int y, int value) {
        // System.out.printf("Handling (%d, %d) - %d\n", x, y, value);
        var score = getScore(x, y, value);
        if (score > best) {
            best = score;
        }
    }

    public Integer GetBest() {
        return this.best;
    }

    public Integer getScore(int startX, int startY, int curSize) {
        var maxX = this.grid.getCols();
        var maxY = this.grid.getRows();

        // look to right
        var lookRight = 0;
        for (var x = startX + 1; x < maxX; x++) {
            var size = grid.getCell(x, startY);
            lookRight++;
            if (size >= curSize) {
                break;
            }
        }

        // look to left
        var lookLeft = 0;
        for (var x = startX - 1; x >= 0; x--) {
            var size = grid.getCell(x, startY);
            lookLeft++;
            if (size >= curSize) {
                break;
            }
        }

        // check top to bottom
        var lookDown = 0;
        for (var y = startY + 1; y < maxY; y++) {
            var size = grid.getCell(startX, y);
            lookDown++;
            if (size >= curSize) {
                break;
            }
        }

        // check bottom to top
        var lookUp = 0;
        for (var y = startY - 1; y >= 0; y--) {
            var size = grid.getCell(startX, y);
            lookUp++;
            if (size >= curSize) {
                break;
            }
        }

        // System.out.printf("U %d, L %d, D %d, R %d\n", lookUp, lookLeft, lookDown,
        // lookRight);

        return lookLeft * lookRight * lookUp * lookDown;
    }
}
