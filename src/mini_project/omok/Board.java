package mini_project.omok;

import java.util.Scanner;

public class Board {
	int size;
	String[][] map;

	Board(int size) {
		this.size = size;
		map = new String[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				map[row][col] = ".";
			}
		}
	}

	public void print() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (col == 0) {
					System.out.printf("%2d", row);
				}
				System.out.print(" " + map[row][col]);
			}
			System.out.println();
		}
		for (int i = 65; i < 65 + size; i++) {
			if (i == 65) {
				System.out.print("   ");
			}
			System.out.print((char) i + " ");
		}
		System.out.println();
	}

	public void set(Player player) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(player.name+"> ");
		String[] xy = scanner.nextLine().split(" ");

		int x = Integer.parseInt(xy[1]);
		int y = ((xy[0].toUpperCase()).charAt(0)) - 65;
		if (x<0 || y <0 || x>=size || y >=size) {
			System.out.println("좌표가 범위를 벗어났습니다.");
			System.out.println("다시 입력해주세요");
			set(player);
			return; 
		}
		if (map[x][y] == "X" || map[x][y] == "O"){
			System.out.println("이미 돌이 놓여진 자리 입니다.");
			System.out.println("다시 입력해주세요");
			set(player);
			return;
		}
		map[x][y] = player.stone;
	}

	public boolean check(Player player) {
		int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };
		String nowStone = player.stone;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (nowStone.equals(map[x][y])) {
					for (int i = 0; i < 8; i++) {
						int cnt = 1;
						int nextx = dx[i] + x;
						int nexty = dy[i] + y;
						for (int k = 0; k < 4; k++) {

							if (nextx >= 0 && nexty >= 0 && nextx < size && nexty < size) {
								if (nowStone.equals(map[nextx][nexty])) {
									cnt++;
								}
							}
							nextx += dx[i];
							nexty += dy[i];
						}
						if (cnt == 5) {
							System.out.println("오목이 완성됐습니다.");
							System.out.println(player.name + "님이 승리하셨습니다.");
							System.out.println("게임이 종료됩니다.");
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
