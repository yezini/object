import java.util.Random;

class Board {
    //전체 게임판(Cell 2차원 배열), 지뢰 배치, 셀 열기, 게임 승리 조건 판단
    //셀의 상태를 기반으로 로직을 구현, Board가 Cell의 메서드를 호출하면서 객체 간 메세지를 주고받는 객체지향 방식으로 구현

    private int rows, cols, mineCount; //행, 열, 지뢰 개수, private 선언(캡슐화)
    private Cell[][] cells;//셀 객체를 담는 2차원 배열, Board는 Cell 객체 배열을 멤버 변수로 가지고 있음 (합성)

    public Board(int rows, int cols, int mineCount) {
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;

        cells = new Cell[rows][cols];
        initBoard();//보드 초기화
    }

    private void initBoard() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j] = new Cell(); //셀 객체들 생성

        placeMines();//지뢰 배치
        countMines();//셀 주변 지뢰 개수 계산
    }

    private void placeMines() {//지뢰 배치
        Random rand = new Random();//중복없이 지뢰를 무작위로 배치
        int placed = 0;//현재까지 배치된 지뢰 개수
        while (placed < mineCount) {//지뢰 개수 10개가 될 때까지 반복
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!cells[r][c].isMine()) {//지뢰가 없는 칸이면
                cells[r][c].setMine(true);//지뢰 배치
                placed++;//배치된 지뢰 수 증가
            }
        }
    }

    private void countMines() {//셀 주변 지뢰 개수 계산
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (!cells[i][j].isMine())//지뢰가 없는 셀
                    cells[i][j].setCountMines(countMinesAround(i, j));//인접한 지뢰 개수 계산
    }

    private int countMinesAround(int r, int c) {//인접한 지뢰 개수 계산
        int count = 0;
        for (int dr = -1; dr <= 1; dr++)            //dr, dc는 행, 열의 변화량 (자기 자신 (0,0)을 기준으로 8칸)
            for (int dc = -1; dc <= 1; dc++)
                if (isMineAt(r + dr, c + dc))
                    count++;
        return count;
    }

    private boolean isMineAt(int r, int c) {
        return isValidPosition(r, c) && cells[r][c].isMine();
    }

    public boolean openCell(int r, int c) {//깃발 없고 아직 열리지 않은 셀에 대해서 open 하는 함수에 해당
        //true 반환 시 지뢰 밟음 (열려는 셀에 지뢰가 있음)
        //false 반환 시 지뢰 없음 (정상적으로 열거나 열지 않음)
        if (!isValidPosition(r, c) || cells[r][c].isOpened() || cells[r][c].isFlagged())
            //게임판 범위를 벗어나거나      이미 열렸거나               이미 깃발이 있거나
            return false;

        cells[r][c].setOpened(true);//해당 셀 오픈
        if (cells[r][c].isMine()) return true;//지뢰가 있으면 true 리턴 -> 게임 끝

        if (cells[r][c].getMines() == 0) { //현재 칸 주변에 지뢰가 하나도 없으면
            //주변 8칸 전부 열기 (재귀)
            //dr, dc는 행, 열의 변화량 (자기 자신 (0,0)을 기준으로 8칸)
            for (int dr = -1; dr <= 1; dr++)
                for (int dc = -1; dc <= 1; dc++)
                    openCell(r + dr, c + dc);//주변 셀에 대해 재귀 호출
        }
        return false;
    }

    public void toggleFlag(int r, int c) {//선택한 좌표에 깃발
        if (isValidPosition(r, c) && !cells[r][c].isOpened())
            //보드판 범위 내에 있고 열려있지 셀에 한하여
            cells[r][c].toggleFlag();
    }

    public boolean isValidPosition(int r, int c) {//코드 중복 제거
        //행과 열이 보드 범위 내에 있는지 확인
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public void printAll() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j].setOpened(true);//게임 종료 시 전체 보드 오픈
    }

    public void printBoard() {//보드 출력
        System.out.print("   ");
        for (char c = 'a'; c < 'a' + cols; c++) //열 수 만큼 알파벳 출력
        System.out.print(c + " ");
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.printf("%d  ", i + 1);//행 번호 출력(1부터 시작)
            for (int j = 0; j < cols; j++) {
                System.out.print(cells[i][j] + " ");//셀 객체를 문자열로 출력 (toString() 자동 호출)
                //□, ⚑, ☼, 숫자로 표현
            }
            System.out.println();
        }
    }

    public boolean isCleared() {
        //게임 승리인지 아닌지 판단, 즉 지뢰가 아닌 모든 칸을 열었는지 판단
        int openedCells = 0;//지금까지 열린 셀 개수
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)//이중 for문으로 전체 셀 반복
                if (cells[i][j].isOpened())
                    openedCells++;
        // rows * cols - mineCount :  지뢰가 아닌 셀 개수 = 열어야 할 셀 개수
        return openedCells == (rows * cols - mineCount);//true 이면 게임 승리
    }
}