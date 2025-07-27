class Cell { //한 칸의 상태 (지뢰 유무, 깃발 여부, 열린 여부 등) 관리
    //외부는 Cell 객체의 내부 상태를 몰라도 toString()만으로 상태 출력 가능
    //내부 상태 숨기고 필요한 정보만 외부에 제공
    private boolean mine = false;
    private boolean opened = false;
    private boolean flagged = false;
    private int aroundMines = 0;//현재 칸 주변에 있는 지뢰 개수

    public boolean isMine() { return mine; }
    public void setMine(boolean mine) { this.mine = mine; }

    public boolean isOpened() { return opened; }
    public void setOpened(boolean opened) { this.opened = opened; }

    public boolean isFlagged() { return flagged; }
    public void toggleFlag() { this.flagged = !flagged; }

    public int getMines() { return aroundMines; }
    public void setCountMines(int count) { this.aroundMines = count; }

    @Override
    public String toString() {//다형성
        if (flagged) return "⚑";
        if (!opened) return "□";
        if (mine) return "☼";
        return aroundMines == 0 ? "■" : String.valueOf(aroundMines);
    }
}