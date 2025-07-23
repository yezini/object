public class MinesweeperGame { //Game 객체 생성 및 게임 시작
    public static void main(String[] args) {
        Game game = new Game(8, 10, 10); // 8행 10열, 지뢰 10개
        game.start(); //게임 시작
    }
}

