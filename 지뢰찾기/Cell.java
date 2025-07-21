class Cell { //셀의 상태 저장 
    private boolean mine = false;
    private boolean opened = false;
    private boolean flagged = false;
    private int adjacentMines = 0;

    public boolean isMine() { return mine; }
    public void setMine(boolean mine) { this.mine = mine; }

    public boolean isOpened() { return opened; }
    public void setOpened(boolean opened) { this.opened = opened; }

    public boolean isFlagged() { return flagged; }
    public void toggleFlag() { this.flagged = !flagged; }

    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int count) { this.adjacentMines = count; }

    @Override
    public String toString() {
        if (flagged) return "⚑";
        if (!opened) return "□";
        if (mine) return "☼";
        return adjacentMines == 0 ? "■" : String.valueOf(adjacentMines);
    }
}
