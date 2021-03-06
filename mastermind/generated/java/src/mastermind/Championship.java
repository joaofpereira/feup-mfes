package mastermind;

import org.overture.codegen.runtime.*;

import java.util.*;


@SuppressWarnings("all")
public class Championship {
    private Number numberOfRounds;
    private VDMSeq players = SeqUtil.seq();
    private VDMSeq currentPlayersInChampionship;
    private VDMSeq games;

    public Championship(final VDMSeq allPlayers) {
        cg_init_Championship_1(Utils.copy(allPlayers));
    }

    public Championship() {
    }

    public void cg_init_Championship_1(final VDMSeq allPlayers) {
        numberOfRounds = Utils.divide(MATH.log(allPlayers.size()).doubleValue(),
                MATH.log(2L).doubleValue());
        players = Utils.copy(allPlayers);
        currentPlayersInChampionship = Utils.copy(allPlayers);
        games = SeqUtil.seq();

        return;
    }

    public Number GetNumberOfRounds() {
        return numberOfRounds;
    }

    public VDMSeq GetPlayers() {
        return Utils.copy(players);
    }

    public VDMSeq GetCurrentPlayersOnChampionship() {
        return Utils.copy(currentPlayersInChampionship);
    }

    public VDMSeq GetGames() {
        return Utils.copy(games);
    }

    public void AddGames(final VDMSeq finishedGames) {
        games = SeqUtil.conc(Utils.copy(games), Utils.copy(finishedGames));

        for (Iterator iterator_6 = games.iterator(); iterator_6.hasNext();) {
            Game g = (Game) iterator_6.next();
            g.GetCodeMaker().SumMoves(g.GetMakerResults().size());
            g.GetCodeBreaker().SumMoves(g.GetBreakerMoves().size());
        }
    }

    public VDMSeq ShufflePlayers(final VDMSeq sequenceOfPlayers) {
        Number playerShuffled = 0L;
        VDMSeq selectedPlayers = SeqUtil.seq();

        for (Iterator iterator_7 = sequenceOfPlayers.iterator();
                iterator_7.hasNext();) {
            Player p = (Player) iterator_7.next();

            if (Utils.equals(selectedPlayers.size(), 0L)) {
                playerShuffled = MATH.rand(sequenceOfPlayers.size()).longValue() +
                    1L;
            } else {
                Boolean whileCond_1 = true;

                while (whileCond_1) {
                    Boolean andResult_2 = false;

                    if (SetUtil.inSet(playerShuffled,
                                SeqUtil.elems(Utils.copy(selectedPlayers)))) {
                        if (!(Utils.equals(selectedPlayers.size(),
                                    sequenceOfPlayers.size()))) {
                            andResult_2 = true;
                        }
                    }

                    whileCond_1 = andResult_2;

                    if (!(whileCond_1)) {
                        break;
                    }

                    playerShuffled = MATH.rand(sequenceOfPlayers.size())
                                         .longValue() + 1L;
                }
            }

            selectedPlayers = SeqUtil.conc(Utils.copy(selectedPlayers),
                    SeqUtil.seq(playerShuffled));
        }

        return Utils.copy(selectedPlayers);
    }

    public VDMSeq CreateGames(final VDMSeq sequenceOfPlayers) {
        VDMSeq playersOrder = ShufflePlayers(Utils.copy(sequenceOfPlayers));
        VDMSeq generatedGames = SeqUtil.seq();
        Game game = null;
        long toVar_1 = (long) Utils.divide((1.0 * sequenceOfPlayers.size()), 2L);
        long byVar_1 = 1L;

        for (Long i = 1L; (byVar_1 < 0) ? (i >= toVar_1) : (i <= toVar_1);
                i += byVar_1) {
            game = new Game(((Player) Utils.get(sequenceOfPlayers,
                        ((Number) Utils.get(playersOrder,
                            (i.longValue() * 2L) - 1L)))),
                    ((Player) Utils.get(sequenceOfPlayers,
                        ((Number) Utils.get(playersOrder, i.longValue() * 2L)))));
            generatedGames = SeqUtil.conc(Utils.copy(generatedGames),
                    SeqUtil.seq(game));
        }

        return Utils.copy(generatedGames);
    }

    public void PickAllWinnerPlayers(final VDMSeq finishedGames) {
        VDMSeq winnerPlayers = SeqUtil.seq();
        long toVar_2 = finishedGames.size();
        long byVar_2 = 1L;

        for (Long i = 1L; (byVar_2 < 0) ? (i >= toVar_2) : (i <= toVar_2);
                i += byVar_2) {
            winnerPlayers = SeqUtil.conc(Utils.copy(winnerPlayers),
                    SeqUtil.seq(
                        ((Game) Utils.get(finishedGames, i)).GetWinnerPlayer()));
        }

        currentPlayersInChampionship = Utils.copy(winnerPlayers);
    }

    public void PrintStats() {
        IO.print("\nThe winner of championship was ");
        IO.print(((Player) GetCurrentPlayersOnChampionship().get(0)).GetName());
        IO.print(" and has made ");
        IO.print(((Player) GetCurrentPlayersOnChampionship().get(0)).GetNumberOfMoves());
        IO.print(" moves.\n\n");
    }

    public String toString() {
        return "Championship{" + "numberOfRounds := " +
        Utils.toString(numberOfRounds) + ", players := " +
        Utils.toString(players) + ", currentPlayersInChampionship := " +
        Utils.toString(currentPlayersInChampionship) + ", games := " +
        Utils.toString(games) + "}";
    }
}
