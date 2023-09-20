package mini_project.omok;

import java.util.Scanner;

public class Player {
    String name;
    String stone;
    Player(String name, String stone) {
        this.name = name;
        this.stone = stone;
    }

    public void set(Board board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(name+"> ");
        String[] xy = scanner.nextLine().split(" ");

        try{
            int x = Integer.parseInt(xy[1]);
            int y = ((xy[0].toUpperCase()).charAt(0)) - 65;
            if (x<0 || y <0 || x>=board.size || y >=board.size) {
                System.out.println("좌표가 범위를 벗어났습니다.");
                System.out.println("다시 입력해주세요");
                set(board);
                return;
            }
            if ("X".equals(board.map[x][y]) || "O".equals(board.map[x][y])){
                System.out.println("이미 돌이 놓여진 자리 입니다.");
                System.out.println("다시 입력해주세요");
                set(board);
                return;
            }
            board.map[x][y] = stone;
        }
        catch (NumberFormatException e){
            System.out.println("좌표를 잘못 입력하셨습다.");
            System.out.println("다시 입력해주세요");
            set(board);
        }

    }
}