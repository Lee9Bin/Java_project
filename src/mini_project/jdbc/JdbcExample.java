package mini_project.jdbc;

import java.sql.*;
import java.util.Scanner;

public class JdbcExample {
    Connection conn = null; //디비와 연결된 세션 역할의 객체
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        JdbcExample boardList = new JdbcExample(); //생성자로 객체를 만들고 디비랑 연결
        boardList.list();
    }

    public JdbcExample() { //생성자를 통해서 만들어지면 연결하면 되잖아! 끊는건 나중에
        try {
            Class.forName("oracle.jdbc.OracleDriver"); //oracle.jar 파일로 받은 클래스를 로드 하기위해 Calss.forName을 통해 가져온다.

            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
                    "testuser",
                    "test1234"); // tcp용 어떤거를 쓸건지, ip주소, 포트번호, sid아이디
            System.out.println("연결 성공");
        }
        catch(SQLException e) {
            System.out.println("연결 실패");
        }
        catch(ClassNotFoundException e) {
            System.out.println("클래스 경로를 찾지 못했습니다.");
        }
        //데이터베이스 연결 완료
    }

    public void list() {
        System.out.println("[게시물 목록]");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer","date", "title");
        System.out.println("----------------------------------------------------");

        try {
            String sql = "" + "SELECT bno,bwriter, bdate, btitle " + "FROM boards " + "ORDER BY bno DESC";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBwriter(rs.getString("bwriter"));
                board.setBdate(rs.getDate("bdate"));
                board.setBtitle(rs.getString("btitle"));
                System.out.printf("%-6s%-12s%-16s%-40s\n", board.getBno(),board.getBwriter(), board.getBdate(),board.getBtitle());
            }
            rs.close();
            pstmt.close();
        }
        catch(SQLException e) {
            System.out.println("게시글을 가져오는데 실패했습니다.");
            System.out.println("강제 종료합니다.");
            exit();
        }
        mainmenu();

    }

    public void mainmenu() {
        while(true) {
            System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
            System.out.print("메뉴 선택: ");
            int selectNum = Integer.parseInt(scanner.nextLine());

            if(selectNum == 1) {
                create();
            }
            else if (selectNum == 2) {
                read();
            }
            else if (selectNum == 3) {
                clear();
            }
            else if (selectNum == 4) {
                exit();
            }
        }
    }

    public void create() {
        Board board = new Board();
        System.out.println("[새 게시물 입력]");
        System.out.print("제목: ");
        board.setBtitle(scanner.nextLine());
        System.out.print("내용 : ");
        board.setBconect(scanner.nextLine());
        System.out.print("글쓴이 : ");
        board.setBwriter(scanner.nextLine());

        System.out.println("----------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택");
        int selectNum = Integer.parseInt(scanner.nextLine());
        if(selectNum == 1) {
            try {
                String sql = "" + "INSERT INTO BOARDS (bno, btitle, bconect, bwriter, bdate) "
                        + "VALUES (SEQ_BNO.NEXTVAL,?,?,?,sysdate)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, board.getBtitle());
                pstmt.setString(2, board.getBconect());
                pstmt.setString(3, board.getBwriter());

                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("성공적으로 게시글이 저장 됐습니다.");
            }
            catch (SQLException e) {
                System.out.println("게시글을 저장하지 못했습니다");
                exit();
            }
        }
        list();
    }
    public void read() {
        System.out.println("[게시물 읽기]");
        System.out.print("bno: ");
        int selectNum = Integer.parseInt(scanner.nextLine());
        try {
            String sql = "" + "SELECT bno, btitle, bconect, bwriter, bdate " + "FROM boards " + "WHERE bno = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectNum);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBtitle(rs.getString("btitle"));
                board.setBconect(rs.getString("bconect"));
                board.setBwriter(rs.getString("bwriter"));
                board.setBdate(rs.getDate("bdate"));
                System.out.println("##########");
                System.out.println("제목: "+board.getBtitle());
                System.out.println("내용: "+ board.getBconect());
                System.out.println("글쓴이: "+ board.getBwriter());
                System.out.println("날짜: "+ board.getBdate());
                System.out.println("##########");

                System.out.println("----------------------------------------------------");
                System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
                System.out.print("메뉴 선택");
                selectNum = Integer.parseInt(scanner.nextLine());
                if (selectNum == 1) {
                    update(board);
                }
                else if(selectNum == 2) {
                    delete(board);
                }
            }
            rs.close();
            pstmt.close();

        }
        catch (Exception e) {
            System.out.println("게시물을 읽어 오지 못했습니다.");
            exit();
        }
        list();

    }

    public void update(Board board) {
        System.out.println("[수정 내용 입력]");
        System.out.print("제목: ");
        board.setBtitle(scanner.nextLine());
        System.out.print("내용 : ");
        board.setBconect(scanner.nextLine());
        System.out.print("글쓴이 : ");
        board.setBwriter(scanner.nextLine());

        System.out.println("----------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택");
        int selectNum = Integer.parseInt(scanner.nextLine());
        if (selectNum == 1) {
            try {
                String sql = "" + "UPDATE boards SET btitle =? , bconect =? , bwriter =? " + "WHERE bno = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, board.getBtitle());
                pstmt.setString(2, board.getBconect());
                pstmt.setString(3, board.getBwriter());
                pstmt.setInt(4, board.getBno());

                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("수정완료");
            }
            catch (Exception e) {
                System.out.println("게시물 수정 실패");
            }
        }
        list();

    }
    public void delete(Board board) {
        try {
            String sql = "" + "DELETE FROM boards WHERE bno = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, board.getBno());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch(Exception e) {
            System.out.println("게시글을 삭제하지 못했습니다.");
            exit();
        }

        list();
    }


    public void clear() {
        System.out.println("[게시물 전체 삭제]");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        int selectNum = Integer.parseInt(scanner.nextLine());
        if(selectNum==1) {
            try {
                String sql = "TRUNCATE TABLE boards";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("게시글 전체 삭제를 하지 못했습니다.");
                exit();
            }
        }
        list();
    }

    public void exit() {
        if(conn != null) {
            try {
                conn.close();
            }
            catch (Exception e) {

            }
        }
        System.out.println("시스템 종료");
        System.exit(0);
    }
}
