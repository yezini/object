class Game {
    private Board board;
    private Scanner scanner = new Scanner(System.in);
    private boolean isGameOver = false;

    public Game(int rows, int cols, int mineCount) {
        board = new Board(rows, cols, mineCount);
    }

    public void start() {
        System.out.println("지뢰찾기 게임 시작!");

        while (!isGameOver) {
            board.printBoard();
            System.out.print("좌표를 입력하세요 (예: a1): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.length() < 2) {
                System.out.println("입력 형식이 잘못되었습니다.");
                continue;
            }

            int col = input.charAt(0) - 'a';
            int row = Character.getNumericValue(input.charAt(1)) - 1;

            if (!board.isValidPosition(row, col)) {
                System.out.println("잘못된 좌표입니다.");
                continue;
            }

            System.out.print("행동을 선택하세요 (1: 오픈, 2: 깃발): ");
            String action = scanner.nextLine();

            if (action.equals("1")) {
                if (board.openCell(row, col)) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    board.revealAll();
                    board.printBoard();
                    isGameOver = true;
                } else if (board.isCleared()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    board.printBoard();
                    isGameOver = true;
                }
            } else if (action.equals("2")) {
                board.toggleFlag(row, col);
                if (board.isCleared()) {
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
