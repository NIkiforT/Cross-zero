import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class TicTacToe {
    //блок настроек игры.
    private static char[][] map; //матрица игры.
    private static int SIZE = 3; //размерность поля.

    private static final char DOT_EMPTY = '⚫';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';

    private static final boolean SILLY_MODE = false;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true){
            humanTurn(); //ход человека
            if (isEndGame(DOT_X)){
                break;
            }

            computerTurn(); //ход компьютера
            if (isEndGame(DOT_O)){
                break;
            }
        }

        System.out.println("Игра закончена");

    }

    /**
     * метод подготовки ирового поля.
     */
    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    /**
     * Метод вывода игрового поля на экран.
     */
    private static void printMap(){
        for(int i = 0; i < SIZE; i++){
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++){
            System.out.print((i+1) + " ");
            for(int j = 0; j < SIZE; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }
    /**
     * Ход человека
     */
    private static void humanTurn(){
        int x, y;

        do {
            System.out.println("Введите координаты через пробел.");
            y = scanner.nextInt() - 1;
            x = scanner.nextInt() - 1;
        }while (!isCellValid(x, y));

        map[y][x] = DOT_X;
    }

    /**
     * Ход компьютера
     */
    private static void computerTurn() {
        int x = -1;
        int y = -1;

        if(SILLY_MODE){
            do{
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);

            }while (!isCellValid(x, y));
            System.out.println("Компьютер выбрал ячейку" + (y+1) + " " + (x + 1));
            map[y][x] = DOT_O;
        }
        else {
            for(int i = 0; i < SIZE; i++){
                for (int j = 0; j < SIZE; j++){
                    //проверяем клетки по направлениям.
                    if(map[i][j] == DOT_O){
                        if (i == 0){
                            if (j == 0){
                                if(map[++i][j] == DOT_O || map[i][++j] == DOT_O || map[++i][++j] == DOT_O){
                                    map[i][j] = DOT_O;
                                }
                            }
                            if (j == SIZE - 1){
                                if(map[++i][j] == DOT_O || map[i][--j] == DOT_O || map[++i][--j] == DOT_O){
                                    map[i][j] = DOT_O;
                                }
                            }
                            if(map[i][--j] == DOT_O || map[i][++j] == DOT_O || map[++i][j] == DOT_O) {
                                map[i][j] = DOT_O;
                            }

                        }
                        if (i == SIZE - 1) {
                            if (j == 0) {
                                if (map[i][++j] == DOT_O || map[--i][++j] == DOT_O || map[--i][j] == DOT_O) {
                                    map[i][j] = DOT_O;
                                }
                            }
                            if (j == SIZE - 1) {
                                if (map[--i][j] == DOT_O || map[i][--j] == DOT_O || map[--i][--j] == DOT_O) {
                                    map[i][j] = DOT_O;
                                }
                            }
                            if (map[i][--j] == DOT_O || map[i][++j] == DOT_O || map[--i][j] == DOT_O) {
                                map[i][j] = DOT_O;
                            }
                        }
                        if(j == 0){
                            if (map[--i][j] == DOT_O || map[++i][j] == DOT_O || map[i][++j] == DOT_O) {
                                map[i][j] = DOT_O;
                            }

                        }
                        if(j == SIZE - 1){
                            if(map[--i][j] == DOT_O || map[++i][j] == DOT_O || map[i][--j] == DOT_O) {
                                map[i][j] = DOT_O;
                            }

                        }
                    }

                    }
                }

        }

    }

    private static boolean isCellValid( int x, int y){
        boolean result = true;
//проверка координаты или заполненности.
        if(x < 0 || x >= SIZE || y < 0 || y >= SIZE){
            result = false;
        }
        if (map[y][x] != DOT_EMPTY){
            result = false;
        }
        return result;
    }

    private static boolean isEndGame(char playerSymbol){
        boolean result = false;

        printMap();
        //проверяем необходимость сл. хода.
        if(checkWin(playerSymbol)){
            System.out.println("Победили " + playerSymbol);
            result = true;

        }
        if(isMapFull()){
            System.out.println("Ничья");
            result = true;
        }

        return result;
    }

    /**
     * Проверка на 100% заполненность поля.
     */
    private static boolean isMapFull(){
        boolean result = true;

        for(int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                if(map[i][j] == DOT_EMPTY)
                    result = false;

            }
        }

        return result;
    }

    /**
     * Метод проверки выигрыша.
     */
    private static boolean checkWin(char playerSymbol){
        boolean result = false;
        if (
                (map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol) ||
                (map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol) ||
                (map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol) ||
                (map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol) ||
                (map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol) ||
                (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol) ||
                (map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol) ||
                (map[2][0] == playerSymbol && map[1][1] == playerSymbol && map[0][2] == playerSymbol)){
            result = true;
        }
        return result;
    }

}
