package charlie.bot.client;

/**
 * file: Logan.java 
 * author: Kavya Reddy Vemula  
 * assignment: Assignment 4 
 */
 
import charlie.actor.Courier;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.ILogan5;
import charlie.util.Play;
import charlie.view.AMoneyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import charlie.advisor.Advisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * This class implements the Logan.
 * @author kavyareddy
 */

public class Logan5 implements ILogan5 {

    protected final Logger LOG = LoggerFactory.getLogger(Logan5.class);
    protected final int THINKING_TIME = 5;
    private Courier courier;
    protected Hand dealerHand, myHand;
    protected Hid dealerHid, myHid;
    private AMoneyManager moneyManager;
    protected Seat mine;
    protected Random ran = new Random();
    protected boolean isMyTurn = false;
    protected Advisor advice = new Advisor();
    protected int runningCount = 0;
    protected int trueCount, betAmount, totalBet, meanBet, countDecks;
    protected double numOfDecks;
    protected static final int MINIMUM_BET = 5;
    boolean firstTime = true;
    protected int maxBet = MINIMUM_BET;
    protected int bjCount = 0;
    protected int charlieCount = 0;
    protected int winCount = 0;
    protected int breakCount = 0;
    protected int loseCount = 0;
    protected int pushCount = 0;
    protected int gamesPlayed = 0;
    protected double startTime;
    protected double currentTime;
    protected double minutesPlayed;
    public final static int X = 10;
    public final static int Y = 250;
    protected Font displayFont = new Font("MS COMIC SANS", Font.BOLD, 17);

    /**
     * This method indicates the bot to place a bet and start the game
     */
    @Override
    public void go() {
        currentTime = System.currentTimeMillis();
        if (firstTime) {
            startTime = System.currentTimeMillis();
            betAmount = MINIMUM_BET;
            moneyManager.upBet(betAmount);
            firstTime = false;
        } else {
            trueCount = runningCount / countDecks;
            betAmount = Math.max(MINIMUM_BET, MINIMUM_BET * trueCount + MINIMUM_BET);
            if (betAmount > maxBet) {
                maxBet = betAmount;
            }
            moneyManager.clearBet();
            int numOf100s = betAmount / 100;
            int numOf25s = (betAmount % 100) / 25;
            int numOf5s = (betAmount % 100 % 25) / 5;
            //To put 100 chips on the table
            for (int i = 0; i < numOf100s; i++) {
                moneyManager.upBet(100);
            }
            //To put 25 chips on the table
            for (int i = 0; i < numOf25s; i++) {
                moneyManager.upBet(25);
            }
            //To put 5 chips on the table
            for (int i = 0; i < numOf5s; i++) {
                moneyManager.upBet(5);
            }
        }
        totalBet += betAmount;
        courier.bet(betAmount, 0);
    }

    /**
     * This method sets the courier 
     *
     * @param courier Courier
     */
    @Override
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    /**
     * Sets the money manager for managing bets.
     *
     * @param moneyManager Money manager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    /**
     * Updates the bot.
     */
    @Override
    public void update() {

    }

    /**
     * This method is used to Render the bot
     * and display the information on the table
     *
     * @param g Graphics context.
     */
    @Override
    public void render(Graphics2D g) {
        minutesPlayed = (currentTime - startTime) / 60000;
        DecimalFormat timeFormat = new DecimalFormat("##");
        DecimalFormat shoeFormat = new DecimalFormat("##.##");
        g.setFont(displayFont);
        g.setColor(Color.WHITE);
        g.drawString("Counting Strategy: Hi-Lo", X, Y - 25);
        g.drawString("True count:  " + trueCount, X, Y - 10);
        g.drawString("Shoe size:  " + shoeFormat.format(numOfDecks), X, Y + 5);
        g.drawString("Running count:  " + runningCount, X, Y + 20);
        g.drawString("Max bet amount:  " + maxBet, X, Y + 35);
        g.drawString("Mean bet amount per game:  " + meanBet, X, Y + 50);
        g.drawString("Number of Minutes played: " + timeFormat.format(minutesPlayed), X, Y + 65);
        g.drawString("Number of Games played: " + gamesPlayed, X, Y + 80);
        g.drawString("BlackJack: " + bjCount, X + 520, Y - 25);
        g.drawString("Charlie: " + charlieCount, X + 520, Y - 10);
        g.drawString("Wins: " + winCount, X + 520, Y + 5);
        g.drawString("Breaks: " + breakCount, X + 520, Y + 20);
        g.drawString("Loses: " + loseCount, X + 520, Y + 35);
        g.drawString("Pushes: " + pushCount, X + 520, Y + 50);
    }

    /**
     * This method starts the game. 
     * Shoesize is divided by 52 to get number of decks
     *
     * @param hids Hand ids
     * @param shoeSize Starting shoe size
     */
    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        isMyTurn = false;
        for (Hid h : hids) {
            if (h.getSeat() == Seat.DEALER) {
                this.dealerHand = new Hand(new Hid(Seat.DEALER));
                dealerHand = new Hand(h);
                dealerHid = dealerHand.getHid();
                continue;
            }
            if (h.getSeat() == Seat.YOU) {
                myHand = new Hand(h);
                mine = h.getSeat();
                myHid = new Hid(mine);
            }
        }
    }

    /**
     * Ends a game. Charlie invokes go after this method.
     * The bet amount is calculated in this method
     *
     * @param shoeSize Shoe size
     */
    @Override
    public void endGame(int shoeSize) {
        numOfDecks = shoeSize / 52.0;
        countDecks = (int) Math.round(numOfDecks);
        if (countDecks < 1) {
            countDecks = 1;
        }
        gamesPlayed += 1;
        meanBet = (int) Math.round((double) totalBet / gamesPlayed);
        isMyTurn = false;
    }

    /**
     * Deals a card.
     *
     * @param hid Hand id which might not necessarily belong to player.
     * @param card Card being dealt
     * @param values Hand values, literal and soft
     */
    @Override
    public void deal(Hid hid, Card card, int[] values) {
        if (card == null) {
            return;
        }
        if (hid.getSeat() == Seat.DEALER) {
            dealerHand.hit(card);
        }
        if (hid.getSeat() == Seat.YOU) {
            myHand.hit(card);
            if (isMyTurn) {
                playGame(hid);
            }
        }
        // Running count increases if lower rank card is dealt
        if (card.getRank() >= 2 && card.getRank() < 7) {
            runningCount += 1;
        }
        // Running Count decreases if a higher rank card is dealt
        if (card.isFace() || card.isAce() || card.getRank() == 10) {
            runningCount -= 1;
        }
    }

    /**
     * Insures a hand
     */
    @Override
    public void insure() {
    }

    /**
     * Busts hand.
     *
     * @param hid Hand id
     */
    @Override
    public void bust(Hid hid) {
        breakCount += 1;
        isMyTurn = false;
    }

    /**
     * Wins hand.
     *
     * @param hid Hand id
     */
    @Override
    public void win(Hid hid) {
        winCount += 1;
        isMyTurn = false;
    }

    /**
     * Gets blackjack with hand.
     *
     * @param hid Hand id
     */
    @Override
    public void blackjack(Hid hid) {
        bjCount += 1;
        isMyTurn = false;
    }

    /**
     * Gets charlie with hand.
     *
     * @param hid Hand id
     */
    @Override
    public void charlie(Hid hid) {
        charlieCount += 1;
        isMyTurn = false;
    }

    /**
     * Loses hand.
     *
     * @param hid Hand id
     */
    @Override
    public void lose(Hid hid) {
        loseCount += 1;
        isMyTurn = false;
    }

    /**
     * Pushes hand.
     *
     * @param hid Hand id
     */
    @Override
    public void push(Hid hid) {
        pushCount += 1;
        isMyTurn = false;
    }

    /**
     * Shuffles hand before next game starts.
     */
    @Override
    public void shuffling() {
        runningCount = 0;
    }

    /**
     * Starts turn.
     *
     * @param hid Hand id
     */
    @Override
    public void play(Hid hid) {
        if (hid.getSeat() != mine) {
            isMyTurn = false;
            return;
        }
        isMyTurn = true;
        playGame(hid);
    }

    /*
     * Implement split for basic strategy
     * @param play play outcome
     */
    public Play correction(Play play) {
        if (play == Play.HIT || play == Play.STAY) {
            return play;
        }
        if (play == Play.DOUBLE_DOWN) {
            if (myHand.size() == 2) {
                myHand.dubble();
                return Play.DOUBLE_DOWN;
            }
            return Play.HIT;
        }
        
        if (myHand.getValue() >= 18) {
            return Play.STAY;
        }
        return Play.HIT;
    }

    /*
     * playing according to basic strategy
     * @param hid Hand id
     */
    public void playGame(Hid hid) {
        Card upCard = dealerHand.getCard(0);
        Play play = correction(advice.advise(myHand, upCard));
        int thinking = ran.nextInt(THINKING_TIME) * 500;
        try {
            Thread.sleep(thinking);   
        } catch (InterruptedException e) {
            LOG.error(e + "occured");
        }
        
        if (play == Play.HIT) {
            courier.hit(hid);
        }
        
        if (play == Play.DOUBLE_DOWN) {
            courier.dubble(hid);
            isMyTurn = false;
        }
        
        if (play == Play.STAY) {
            courier.stay(hid);
            isMyTurn = false;
        }
    }
}

