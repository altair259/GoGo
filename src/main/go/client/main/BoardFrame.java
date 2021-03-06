package go.client.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static java.lang.System.exit;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import go.game.engine.BoardFieldOwnership;
import go.game.engine.GameBoard;
import go.game.exception.IncorrectMoveException;


public class BoardFrame {

    private final int BOARD_SIZE = 19;
    private boolean onClose = true;
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JFrame frame = new JFrame("GoGame");
    private ImagePanel[][] fields = new ImagePanel[BOARD_SIZE][BOARD_SIZE];
    private JPanel board;
    public GameBoard g = new GameBoard();
    private GoClient player;
    private JButton resignButton = new JButton("Resign");
    private JButton passButton = new JButton("Pass");
    private JButton suggestTerritoryButton = new JButton("Suggest territory");
    private JLabel playerNick;
    private JLabel playerColor;
    private boolean territoryMode = false;
    private JToolBar tools = new JToolBar();
    private JLabel capturedStones = new JLabel("Captured Stones: 0");

    BoardFrame(GoClient client, String nick) throws IOException, URISyntaxException {
        playerNick = new JLabel("NICK: " + nick);
        initializeGui();
        player = client;
    }

    /**
     * Initialize GUI of the board and give tasks for buttons
     * @throws IOException
     * @throws URISyntaxException
     */
    public final void initializeGui() throws IOException, URISyntaxException {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        tools.setFloatable(false);
        resignButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                player.handleResignGame();
            }
        });
        passButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                try {
                    player.sendPass();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        suggestTerritoryButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                try {
                    player.suggestTerritory();
                    suggestTerritoryButton.setVisible(false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(passButton); // TODO - add functionality

        tools.addSeparator();
        tools.add(resignButton);
        tools.addSeparator();
        tools.addSeparator();
        suggestTerritoryButton.setVisible(false);
        tools.add(suggestTerritoryButton);
        tools.addSeparator();
        tools.addSeparator();
        tools.add(playerNick);
        tools.addSeparator();
        tools.add(capturedStones);
        tools.addSeparator();
        tools.addSeparator();

        board = new JPanel(new GridLayout(0, BOARD_SIZE));
        board.setBorder(new LineBorder(Color.BLACK));
        gui.add(board);

        createBoard();

        frame.add(gui);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);

        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
        frame.setSize(700,720);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(onClose){
                    JOptionPane.showConfirmDialog(
                            frame,
                            "You can't close this window. To exit please resign first.",
                            "Exit info",
                            DEFAULT_OPTION,
                            INFORMATION_MESSAGE
                    );
                }
            }
        });

    }

    /**
     * Place image on one field
     * @param image name of file with image
     * @param field coordinate of field
     */
    private void setFieldBackground(String image, Point field) throws URISyntaxException, IOException {
        fields[field.x][field.y].
                setBackground(ImageIO.
                        read(new File(System.getProperty("user.dir") + "/src/resources/" + image)));
    }

    /**
     * Set empty image in one field
     * @param field coordinate of field
     */
    private void setEmptyFieldBackground (Point field)  throws IOException, URISyntaxException{
        int i = field.y;
        int j = field.x;
        if ((i > 0 && i < 18) && (j > 0 && j < 18)) {
            setFieldBackground("fieldEmpty.png", new Point(j,i));
        }
        else if (j == 0 && (i > 0 && i < 18))
            setFieldBackground("fieldEmptyBorderWest.png", new Point(j,i));
        else if (j == 18 && (i > 0 && i < 18))
            setFieldBackground("fieldEmptyBorderEast.png", new Point(j,i));
        else if (i == 0 && (j > 0 && j < 18))
            setFieldBackground("fieldEmptyBorderNorth.png", new Point(j,i));
        else if (i == 18 && (j > 0 && j < 18))
            setFieldBackground("fieldEmptyBorderSouth.png", new Point(j,i));
        else if (j == 0 && i == 0)
            setFieldBackground("fieldEmptyCornerNorthWest.png", new Point(j,i));
        else if (j == 0 && i == 18)
            setFieldBackground("fieldEmptyCornerSouthWest.png", new Point(j,i));
        else if (j == 18 && i == 0)
            setFieldBackground("fieldEmptyCornerNorthEast.png", new Point(j,i));
        else if (j == 18 && i == 18)
            setFieldBackground("fieldEmptyCornerSouthEast.png", new Point(j,i));

    }
    /**
     * Create board and send handle clicked field to client
     */
    private void createBoard() throws IOException, URISyntaxException {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                fields[j][i] = new ImagePanel();
                setEmptyFieldBackground(new Point (j,i));
                int finali = i;
                int finalj = j;
                fields[j][i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(territoryMode){
                            player.sendTerritoryField(new Point(finali, finalj));
                        }
                        else{
                            player.sendMove(new Point(finali, finalj));
                        }

                    }
                });
                board.add(fields[j][i]);
            }
        }
    }

    /**
     * Show dialog with information for client
     * @param msg String message to show
     */
    public void infoDialog(String msg){
        JOptionPane.showConfirmDialog(
                frame,
                msg,
                "Info",
                DEFAULT_OPTION,
                INFORMATION_MESSAGE
        );
    }
    /**
     * Update board after each move
     * @param boardFields actual boardField
     * @return true if move was successfully executed, false otherwise
     * @throws IOException
     * @throws URISyntaxException
     */
    public void updateBoard(HashMap<Point, BoardFieldOwnership> boardFields) throws IOException, URISyntaxException {
        for(Point h: boardFields.keySet()){
            if(boardFields.get(h).equals(BoardFieldOwnership.BLACK)){
                setFieldBackground("blackPiece.png", new Point(h.y , h.x ));
            }
            else if(boardFields.get(h).equals(BoardFieldOwnership.WHITE)){
                setFieldBackground("whitePiece.png", new Point(h.y, h.x));
            }
            else if(boardFields.get(h).equals(BoardFieldOwnership.BLACK_TERRITORY)){
                setFieldBackground("fieldTerritoryBlack.png", new Point(h.y, h.x));
            }
            else if(boardFields.get(h).equals(BoardFieldOwnership.WHITE_TERRITORY)){
                setFieldBackground("fieldTerritoryWhite.png", new Point(h.y, h.x));
            }
            else if(boardFields.get(h).equals(BoardFieldOwnership.WHITE_PIECE_NOT_ALIVE)){
                setFieldBackground("whitePieceNotAlive.png", new Point(h.y, h.x));
            }
            else if(boardFields.get(h).equals(BoardFieldOwnership.BLACK_PIECE_NOT_ALIVE)){
                setFieldBackground("blackPieceNotAlive.png", new Point(h.y, h.x));
            }
            else{
                setEmptyFieldBackground(new Point(h.y, h.x));
            }
        }
    }
    /**
     * Close frame
     */
    public void closeFrame(){
        onClose = false;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    /**
     * Set player color
     * @param s String - player color
     */
    public void setPlayerColor(String s){
        playerColor = new JLabel("COLOR: " + s);
        tools.add(playerColor);
    }

    /**
     * Set territory mode
     * @param territoryMode
     */
    public void setTerritoryMode(boolean territoryMode){
        this.territoryMode = territoryMode;
    }

    /**
     * Dialog if opponent pass
     */
    public void showOpponentPassDialog() throws IOException, URISyntaxException {
        if(JOptionPane.showOptionDialog(null,
                "Your opponent clicked pass, " +
                        "you can continue playing or " +
                        "pass and suggest your territory",
                "Opponent pass",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Continue playing", "Pass and suggest your territory"},
                "default") == 1){
            System.out.println("player want Pass and suggest your territory");
            territoryMode = true;
            tools.addSeparator();
            tools.addSeparator();
            suggestTerritoryButton.setVisible(true);
//            tools.add(suggestTerritoryButton);
            player.initTerritoryMode();
        }
        else{
            System.out.println("player want Continue playing");
        }
    }

    /**
     * Not your move dialog (pass)
     */
    public void passImpossiblyDialog(){
        JOptionPane.showConfirmDialog(
                frame,
                "Your can't pass - it's not your move!",
                "Opponent pass info",
                DEFAULT_OPTION,
                INFORMATION_MESSAGE
        );
    }

    /**
     * Territory suggestion dialog, handle user choose
     */
    public void showTerritorySuggestDialog() throws IOException, URISyntaxException {
        int a = JOptionPane.showOptionDialog(null,
                "Your opponent suggested territory. " +
                        "You can accept, suggest your territory " +
                        "or resume game",
                "Opponent pass",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Accept", "Suggest your territory", "Resume game"},
                "default");
        switch (a){
            case 0:
                if(playerColor.equals("WHITE")){
                    player.showResult();
                }
                else{
                    player.showResult();
                }
                break;
            case 1:
                territoryMode = true;
                tools.addSeparator();
                tools.addSeparator();
                suggestTerritoryButton.setVisible(true);
                player.initTerritoryMode();
                break;
            case 2:
                territoryMode = false;
                player.resumeGame();
                break;

        }
    }

    /**
     * Make possible resume game
     */
    public void resGame(){
        territoryMode = false;
    }

    /**
     * Change captured stones label
     */
    public void changeCapturesStones(int n){
        capturedStones.setText("Captured Stones: " + n);
    }

    /**
     * Show finish dialog
     */
    public void finishDialog(String s){
        JOptionPane.showConfirmDialog(
                frame,
                s,
                "Finish result",
                DEFAULT_OPTION,
                INFORMATION_MESSAGE
        );
        exit(0);
    }
}