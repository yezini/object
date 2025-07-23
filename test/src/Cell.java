class Cell { //한 칸의 상태 (지뢰 유무, 깃발 여부, 열린 여부 등) 관리
    private boolean mine = false;
    private boolean opened = false;
    private boolean flagged = false;
    private int adjacentMines = 0;//현재 칸 주변에 있는 지뢰 개수

    public boolean isMine() { return mine; }
    public void setMine(boolean mine) { this.mine = mine; }

    public boolean isOpened() { return opened; }
    public void setOpened(boolean opened) { this.opened = opened; }

    public boolean isFlagged() { return flagged; }
    public void toggleFlag() { this.flagged = !flagged; }

    public int getMines() { return adjacentMines; }
    public void setCountMines(int count) { this.adjacentMines = count; }

    @Override
    public String toString() {
        if (flagged) return "⚑";
        if (!opened) return "□";
        if (mine) return "☼";
        return adjacentMines == 0 ? "■" : String.valueOf(adjacentMines);
    }
}