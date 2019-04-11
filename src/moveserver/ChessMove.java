package moveserver;

import java.io.Serializable;

public class ChessMove implements Serializable
{
    private final String team;
    private final String piece;
    private final int fromRank;
    private final char fromFile;
    private final int toRank;
    private final char toFile;

    public ChessMove(String team, String piece, int fromRank, char fromFile, int toRank, char toFile)
    {
        this.team = team;
        this.piece = piece;
        this.fromRank = fromRank;
        this.fromFile = fromFile;
        this.toRank = toRank;
        this.toFile = toFile;
    }
    
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Chess Move - \n");
        builder.append("Team: " + team + "\n");
        builder.append("Piece: " + piece + "\n");
        builder.append("From - rank: " + fromRank + ", file: " + fromFile + "\n");
        builder.append("To  - rank: " + toRank + ", file: " + toFile + "\n");
        return builder.toString();
    }
}
