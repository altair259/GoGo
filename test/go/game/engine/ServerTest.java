package go.game.engine;

import go.server.main.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Server goServer = null;
    private Socket testClientSocket;
    private BufferedReader inClient;
    private PrintWriter outClient;


    @Before
    public void initialize() throws IOException {
        goServer = new Server();
        testClientSocket = new Socket("localhost", 8080);
        goServer.connectClient();
        inClient = new BufferedReader(new InputStreamReader(
                testClientSocket.getInputStream()));
        outClient = new PrintWriter(testClientSocket.getOutputStream(), true);
    }


    @Test
    public void serverTest() throws IOException, InterruptedException {
        //testing get and update player name
        assertEquals("SUBMITNAME", inClient.readLine());
        outClient.println("PlayerName");
        assertEquals("UPDATE_NAMES PlayerName", inClient.readLine());


        //testing challange accepted and get player color
        outClient.println("YOUR_CHALLANGE_ACCEPTED opponentName");
        assertEquals("PLAYER_COLOR BLACK", inClient.readLine());


        //testing move
        outClient.println("MOVE 1 2");
        assertEquals("MOVE_OK 1 2", inClient.readLine());
        inClient.readLine();//that return MOVE_NOT_OK move becouse server cant send to opponent message



        //testing not your turn
        outClient.println("MOVE 12 5");
        assertEquals("MOVE_NOT_YOUR_TURN", inClient.readLine());


        //testing opponent pass
        outClient.println("OPPONENT_PASS_CHANGE_MOVE");
        outClient.println("MOVE 12 5");
        assertEquals("MOVE_OK 12 5", inClient.readLine());
        inClient.readLine();//that return MOVE_NOT_OK move becouse server cant send to opponent message



        //testing opponent move
        outClient.println("OPPONENT_MOVE 11 12");
        assertEquals("OPPONENT_MOVE_OK 11 12", inClient.readLine());



        //testing move in your piece
        outClient.println("MOVE 1 2");
        assertEquals("MOVE_NOT_OK", inClient.readLine());



        //testing move in opponent piece
        outClient.println("MOVE 11 12");
        assertEquals("MOVE_NOT_OK", inClient.readLine());



        //testing opponent init and choose territory
        outClient.println("OPPONENT_INIT_TERRITORY_MODE");
        outClient.println("OPPONENT_TERRITORY_CHOOSE 2 2");
        assertEquals("OPPONENT_TERRITORY_CHOOSE_OK 2 2", inClient.readLine());


        //testing opponent choose another territory
        outClient.println("OPPONENT_TERRITORY_CHOOSE 4 5");
        assertEquals("OPPONENT_TERRITORY_CHOOSE_OK 4 5", inClient.readLine());


        //testing opponent init the same territory
        outClient.println("OPPONENT_TERRITORY_CHOOSE 2 2");
        assertEquals("TERRITORY_CHOOSE_NOT_OK", inClient.readLine());





        //testing opponent resume game
        outClient.println("OPPONENT_RESUME_GAME");
        outClient.println("MOVE 11 17");
        assertEquals("MOVE_OK 11 17", inClient.readLine());
        inClient.readLine();//that return MOVE_NOT_OK move becouse server cant send to opponent message





        //testing result message
        outClient.println("SHOW_RESULT");
        assertEquals("SHOW_RESULT PlayerName won with 357 to 0", inClient.readLine());



    }

}