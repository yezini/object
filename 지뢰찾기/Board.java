class Board {//전체 게임판(Cell 2차원 배열), 지뢰 배치, 셀 열기, 승리 조건 검사
    private int rows, cols, mineCount;
    private Cell[][] cells;

    public Board(int rows, int cols, int mineCount) {
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        cells = new Cell[rows][cols];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j] = new Cell();

        placeMines();
        countAdjacentMines();
    }

    private void placeMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < mineCount) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!cells[r][c].isMine()) {
                cells[r][c].setMine(true);
                placed++;
            }
        }
    }

    private void countAdjacentMines() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (!cells[i][j].isMine())
                    cells[i][j].setAdjacentMines(countMinesAround(i, j));
    }

    private int countMinesAround(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++)
                if (isMineAt(r + dr, c + dc))
                    count++;
        return count;
    }

    private boolean isMineAt(int r, int c) {
        return isValidPosition(r, c) && cells[r][c].isMine();
    }

    public boolean openCell(int r, int c) {
        if (!isValidPosition(r, c) || cells[r][c].isOpened() || cells[r][c].isFlagged())
            return false;

        cells[r][c].setOpened(true);
        if (cells[r][c].isMine()) return true;

        if (cells[r][c].getAdjacentMines() == 0) {
            for (int dr = -1; dr <= 1; dr++)
                for (int dc = -1; dc <= 1; dc++)
                    openCell(r + dr, c + dc);
        }
        return false;
    }

    public void toggleFlag(int r, int c) {
        if (isValidPosition(r, c) && !cells[r][c].isOpened())
            cells[r][c].toggleFlag();
    }

    public boolean isValidPosition(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public void revealAll() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j].setOpened(true);
    }

    public void printBoard() {
        System.out.print("   ");
        for (char c = 'a'; c < 'a' + cols; c++) System.out.print(c + " ");
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.printf("%d  ", i + 1);
            for (int j = 0; j < cols; j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isCleared() {
        int openedCells = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (cells[i][j].isOpened())
                    openedCells++;
        return openedCells == (rows * cols - mineCount);
    }
}
