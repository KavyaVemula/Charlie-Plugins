
package charlie.sidebet.view;

/**
 * This class implements the view for Side bet
 * @author kavyareddy
 */


import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;
import charlie.view.sprite.Chip;

import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SideBetView implements ISideBetView {
    private final Logger LOG = LoggerFactory.getLogger(SideBetView.class);
    
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    public final static String W="WIN";
    public final static String L="LOSE";
    int width;
    Random randomNumber= new Random();
    
    
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected Font font2 = new Font("Comic Sans MS", Font.ITALIC, 20);
    protected Font resultFont = new Font("Comic Sans MS", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);
    
    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);   

    protected List<ChipButton> buttons;
    protected List<charlie.view.sprite.Chip> chipsList = new ArrayList<>();
    protected int amt = 0;
    protected AMoneyManager moneyManager;
    private double payOff;

    public SideBetView() {
        LOG.info("side bet view constructed");
        moneyManager= new AMoneyManager();
    }
    
    /**
     * Sets the money manager.
     * @param moneyManager 
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }
    
    /**
     * Registers a click for the side bet.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;
        
        // Tests if any chip button has been pressed.
        for(ChipButton button: buttons) {
            if(button.isPressed(x, y)) {
                amt += button.getAmt();
                SoundFactory.play(Effect.CHIPS_IN);
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
                
                int length=chipsList.size();
                width = button.getImage().getWidth(null);
                int placeX = X + (length + 1) * width / 3 + randomNumber.nextInt(10) - 10;
                int placeY = Y + randomNumber.nextInt(15) + 15;
                charlie.view.sprite.Chip chip = new charlie.view.sprite.Chip(button.getImage(), placeX, placeY, amt);
                chipsList.add(chip);
            } 
        }
        
        if(oldAmt == amt) {
            if(x < (X+DIAMETER / 2) && x > (X-DIAMETER/2) && y < (Y+DIAMETER/2)
                    && y > (Y-DIAMETER/2)) {
                amt = 0;
                chipsList.clear();
                SoundFactory.play(Effect.CHIPS_OUT);
                LOG.info("B. side bet amount cleared");                
            }

        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();
        
        if(bet == 0)
            return;

        LOG.info("side bet outcome = "+bet);
        
        // Updates the bankroll
        moneyManager.increase(bet);
        payOff = bet;
        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
        payOff = 0;
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table
        g.setColor(Color.RED); 
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);
        
        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(""+amt, X-5, Y+5);
        
        // Draw Information regarding Side bets
        g.setFont(font2);
        g.setColor(Color.lightGray);
        g.drawString("SUPER 7 pays 3:1", X+42, Y-80);
        g.drawString("ROYAL MATCH pays 25:1", X+42, Y-55);
        g.drawString("EXACTLY 13 pays 1:1", X+42, Y-30);
        
        for (int i = 0; i < buttons.size(); i++) {
            ChipButton button = buttons.get(i);
            button.render(g);
        }

        for (int i = 0; i < chipsList.size(); i++) {
            Chip chip = chipsList.get(i);
            chip.render(g);
        }
        Graphics2D graphicsObject = g;
        renderResult(graphicsObject);
        
    }
    protected void renderResult(Graphics2D g) {
        try {
            String result;

            //does not display anything if the playOff is 0
            if (payOff == 0) {
                return;
            } 
            //If payOff is higher than or same as bet amount then it is, WIN
            else if (payOff >= amt) {
                result = W;
            } 
            //If payOff is less than bet amount than it is, LOSE.
            else {
                result = L;
            }

            //Setting background color for ovals
            if (result.equals(L)) {
                g.setColor(new Color(204, 0, 102));
            } else if (result.equals(W)) {
                g.setColor(new Color(0, 128, 255));
            }

             
            
            FontMetrics fm = g.getFontMetrics(resultFont);
            String resultText = " " + result.toUpperCase() + " ! ";
            //Calculates the height and width of the text that will be displayed
            int w = fm.charsWidth(resultText.toCharArray(), 0, resultText.length());
            int h = fm.getHeight();
            //Paints the oval with the background color based on calculated h, w
            g.fillRoundRect(X + 10, Y - h + 50, w, h, 5, 5);

            // Paints the outcome foreground            
            if (L.equals(result)) {
                g.setColor(Color.WHITE);
            } else if (W.equals(result)) {
                g.setColor(Color.BLACK);
            }

            //Draws the result text.
            g.setFont(resultFont);
            g.drawString(resultText, X + 10, Y + 45);
        } catch (Exception ex) {
            System.out.println("Error in SideBetView() renderState method:" + ex.getMessage());
        }
    }
}
