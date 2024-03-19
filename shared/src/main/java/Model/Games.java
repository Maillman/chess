package Model;

import java.util.List;

public class Games {
    private final List<Game> games;

    public Games(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    @Override
    public String toString() {
        return "Games{" +
                "games=" + games +
                '}';
    }
}
