
package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the side bet rule for
 * Super 7, Exactly 13 and Royal Match
 */
public class SideBetRule implements ISideBetRule {
    private final Logger LOG = LoggerFactory.getLogger(SideBetRule.class);
    private final Double payoffSuper7 = 3.0;
    private final Double payoffRoyalMatch = 25.0;
    /**
     * Applies rule to the hand and return the payout if the rule matches and the
     * negative bet if the rule does not match.
     *
     * @param hand Hand to analyze.
     * @return
     */
    @Override
    public double apply(Hand hand) {

        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = " + bet);

        if (bet == 0) {
            return 0.0;
        }

        LOG.info("side bet rule applying hand = " + hand);
        Double payOutSuper7=0.0, payOutRoyalMatch=0.0, payOutExactly13=0.0;
        Card cardOne = hand.getCard(0);
        Card cardTwo = hand.getCard(1);
        //Checking for Super 7
        if (cardOne.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches");
            payOutSuper7 = bet * payoffSuper7;
        }
        //Checking for Royal Match
        if(cardOne.getSuit().equals(cardTwo.getSuit())) {
            if(cardOne.getName().equals("K") && cardTwo.getName().equals("Q") ||
                    cardOne.getName().equals("Q") && cardTwo.getName().equals("K")) {
                LOG.info("side bet ROYAL MATCH matches");
                payOutRoyalMatch = bet * payoffRoyalMatch;
            }
        }
        //Checking for Exactly 13
        if(cardOne.getRank() + cardTwo.getRank()== 13) {
            LOG.info("side bet EXACTLY 13 matches");
            //Since,PayOff rate for Exactly 13 is 1:1,bet amount will be returned
            payOutExactly13 = bet;
        }
        //Checks if any of the side bet is matched
        if (payOutRoyalMatch == 0.0 && payOutSuper7 == 0.0 && payOutExactly13 == 0.0) {
            LOG.info("side bet rule no match");
            return -bet;

        } // Decreases the bet amount if none of the side bets are matched
        else {
            if (payOutRoyalMatch == 0.0) {
                if (payOutSuper7 > payOutExactly13) {
                    return payOutSuper7;
                } 
                else {
                    return payOutExactly13;
                }
            } 
            else {
                return payOutRoyalMatch;
            }
        }
    }
}
