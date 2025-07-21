class Cell { //각 셀의 정보를 저장 (지뢰 여부, 열림 여부, 깃발 여부 등)
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
