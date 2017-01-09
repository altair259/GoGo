package go.client.main;

import java.awt.*;
import java.util.ArrayList;

import go.game.engine.BoardFieldOwnership;
import go.game.engine.GameEngineStatus;
import go.game.engine.Player;

public class GoPlayer implements Player {

    private BoardFieldOwnership color;
    private GameEngineStatus gameStatus;

    public GoPlayer(BoardFieldOwnership color, GameEngineStatus status){
        this.color = color;
        gameStatus = status;
    }

    @Override
    public void stonePlaced(Point opponentPoint, BoardFieldOwnership player) {

    }

    @Override
    public void playerPassedTurn(BoardFieldOwnership player) {

    }

    @Override
    public void setColor(BoardFieldOwnership color) {
        this.color = color;
    }

    @Override
    public BoardFieldOwnership getColor() {
        return color;
    }

    @Override
    public void notifyGameStateChanged(GameEngineStatus newState) {
        gameStatus = newState;
    }

    @Override
    public void announceWinner(BoardFieldOwnership winner, int blackScore, int whiteScore) {

    }

    @Override
    public void territoryProposition(ArrayList<Point> blackTerritory, ArrayList<Point> whiteTerritory, BoardFieldOwnership player) {

    }
}
