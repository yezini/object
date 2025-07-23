import java.util.Scanner;
class Game {
    private Board board; //게임판 객체 생성
    private Scanner scanner = new Scanner(System.in);//사용자 입력
    private boolean isGameOver = false; //게임 종료 여부 판단

    public Game(int rows, int cols, int mineCount) {// 생성자 생성
        board = new Board(rows, cols, mineCount);//행, 열, 지뢰 개수를 전달하여 게임판 초기화
    }

    public void start() {//게임 시작
        System.out.println("지뢰찾기 게임 시작!");

        while (!isGameOver) {//게임이 끝나지 않는 동안 반복 실행
            board.printBoard();//보드 출력
            System.out.print("좌표를 입력하세요 (예: a1): ");//좌표 입력
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.length() < 2) {
                System.out.println("입력 형식이 잘못되었습니다.");
                continue;
            }
            //e2
            int col = input.charAt(0) - 'a'; //열 계산 , 4
            int row = Character.getNumericValue(input.charAt(1)) - 1;//행 계산 , 1
            //인덱스 0부터 시작 -> (2,5)

            if (!board.isValidPosition(row, col)) {//보드 범위 안에 있는 좌표인지 확인
                System.out.println("잘못된 좌표입니다.");
                continue;
            }

            System.out.print("행동을 선택하세요 (1: 오픈, 2: 깃발): ");
            String action = scanner.nextLine();

            if (action.equals("1")) { //선택한 좌표 오픈
                if (board.openCell(row, col)) {//openCell true 반환 시 지뢰 밟음
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    board.printAll();
                    board.printBoard();
                    isGameOver = true;
                } else if (board.isCleared()) {//지뢰 제외 좌표 오픈 완료
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    board.printBoard();
                    isGameOver = true;
                }
            } else if (action.equals("2")) {//선택한 좌표에 깃발
                board.toggleFlag(row, col);
                if (board.isCleared()) {//게임 승리 조건 판단
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    board.printBoard();
                    isGameOver = true;
                }
            } else {
                System.out.println("잘못된 행동입니다.");
            }
        }
    }
}