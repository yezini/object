import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static String[][] board = new String[8][10];  //사용자에게 보여지는 게임판에 해당  
    private static Integer[][] landMineCounts = new Integer[8][10]; //해당 칸 주변에 지뢰가 몇개 있는지를 지정 
    private static boolean[][] landMines = new boolean[8][10]; //실제 지뢰가 있는 위치를 boolean으로 지정 
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
        // □ : 아직 열지 않은 칸 , ■ : 열었는데 주변에 지뢰가 하나도 없는 칸 , 숫자는 해당 숫자만큼 지뢰있음을 표시 

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 8; i++) {  //초기 보드판에 해당 
            for (int j = 0; j < 10; j++) {
                board[i][j] = "□"; //모든 셀에 "□"를 할당
            }
        }
        for (int i = 0; i < 10; i++) { //지뢰 10개를 랜덤으로 배치 
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            landMines[row][col] = true;
        }
        //각 셀 주변에 있는 지뢰 개수 세기 ,  landMines[i][j] == true 이면 해당 셀은 제뢰가 있는 셀
        for (int i = 0; i < 8; i++) { //헹 
            for (int j = 0; j < 10; j++) {   //열 
                int count = 0;
                if (!landMines[i][j]) { //현재 셀에 지뢰가 없다면 
                   //주변 8개 검사 
                    if (i - 1 >= 0 && j - 1 >= 0 && landMines[i - 1][j - 1]) {
                        count++;  //지뢰가 있으면 count 수행 
                    }
                    if (i - 1 >= 0 && landMines[i - 1][j]) {
                        count++;
                    }
                    if (i - 1 >= 0 && j + 1 < 10 && landMines[i - 1][j + 1]) {
                        count++;
                    }
                    if (j - 1 >= 0 && landMines[i][j - 1]) {
                        count++;
                    }
                    if (j + 1 < 10 && landMines[i][j + 1]) {
                        count++;
                    }
                    if (i + 1 < 8 && j - 1 >= 0 && landMines[i + 1][j - 1]) {
                        count++;
                    }
                    if (i + 1 < 8 && landMines[i + 1][j]) {
                        count++;
                    }
                    if (i + 1 < 8 && j + 1 < 10 && landMines[i + 1][j + 1]) {
                        count++;
                    }
                    landMineCounts[i][j] = count;  //지뢰 수 저장 
                    continue;
                } //현재 셀에 지뢰가 있다면 
                landMineCounts[i][j] = 0;
            }
        }
        while (true) {
            System.out.println("   a b c d e f g h i j");  //가로 줄 
            for (int i = 0; i < 8; i++) {
                System.out.printf("%d  ", i + 1);  //i는 행 번호 
                for (int j = 0; j < 10; j++) {
                    System.out.print(board[i][j] + " "); // i행 j열의 셀
                }
                System.out.println();  //다음 줄 
            }
            if (gameStatus == 1) {  //성공
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (gameStatus == -1) { //실패 
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            System.out.println();
            System.out.println("선택할 좌표를 입력하세요. (예: a1)"); //사용자가 열고 싶은 칸 지정  (문자+숫자 조합)
            String input = scanner.nextLine();
            System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
            String input2 = scanner.nextLine(); //사용자가 하는 행동을 저장하는 문자열 
            char c = input.charAt(0); //'3' → 2
            char r = input.charAt(1); // 'f' → 5 
                // 1a는 [0][0]를 의미 
            int col;
            switch (c) { 
                case 'a':  // a -1 -> 0   (인덱스 -1)
                    col = 0;
                    break;
                case 'b':  // b -1 -> 1
                    col = 1;
                    break;
                case 'c':
                    col = 2;
                    break;
                case 'd':
                    col = 3;
                    break;
                case 'e':
                    col = 4;
                    break;
                case 'f':
                    col = 5;
                    break;
                case 'g':
                    col = 6;
                    break;
                case 'h':
                    col = 7;
                    break;
                case 'i':
                    col = 8;
                    break;
                case 'j':
                    col = 9;
                    break;
                default:
                    col = -1;
                    break;
            }
            int row = Character.getNumericValue(r) - 1; //문자를 숫자로 바꿔 인덱스로 변환 (배열 인덱스)
            if (input2.equals("2")) { //좌표에 깃발 표시
                board[row][col] = "⚑"; //사용자가 해당 칸에 깃발 표시
                boolean open = true;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (board[i][j].equals("□")) {
                            open = false; //열리지 않은 칸 
                        }
                    }
                }
                if (open) {
                    gameStatus = 1; //게임 클리어 
                }
            } else if (input2.equals("1")) {  //선택한 좌표 칸 열기 
                if (landMines[row][col]) {
                    board[row][col] = "☼"; //사용자가 지뢰 밟음 
                    gameStatus = -1;   //게임 종료 
                    continue;  //다음 루프로 넘어감 
                } else {  //지뢰가 아닌 경우에 해당 
                    open(row, col);    //재귀함수 호출 
                }
                boolean open = true;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (board[i][j].equals("□")) {
                            open = false;  //열리지 않은 칸 
                        }
                    }
                }
                if (open) {
                    gameStatus = 1;
                }
            } else { //선택한 값이 1또는 2가 아닌 경우 
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }

    private static void open(int row, int col) {  //지뢰가 아닌 칸을 여는 함수에 해당 
        if (row < 0 || row >= 8 || col < 0 || col >= 10) { //배열 범위 벗어나면 종료 
            return;
        }
        if (!board[row][col].equals("□")) {  //이미 열려있는 칸이면 종료 
            return;
        }
        if (landMines[row][col]) { //지뢰이면 종료 (열지 않음)
            return;
        }
        if (landMineCounts[row][col] != 0) {  //주변에 지뢰의 개수를 숫자로 표시하고 종료 
            board[row][col] = String.valueOf(landMineCounts[row][col]);
            return;
        } else {//주변에 지뢰가 하나도 없으면 ■ 표시 
            board[row][col] = "■";
        }
        //주변 8개의 영역에 해당하는 칸 열기 (재귀함수)
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
