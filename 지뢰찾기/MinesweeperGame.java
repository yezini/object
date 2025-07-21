import java.util.*;

public class MinesweeperGame {  //게임 전체 
    public static void main(String[] args) {
        Game game = new Game(8, 10, 10); // 8행 10열, 지뢰 10개
        game.start();
    }
}
