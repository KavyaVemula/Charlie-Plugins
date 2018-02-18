/*
 * This class implements IAdvisor and extends BasicStrategy.
 * This class is responsible for advising the player in choosing the right move.
 * 
 */
package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

/**
 *
 * @author kavyareddy
 */
public class Advisor extends BasicStrategy implements IAdvisor {

    @Override
    public Play advise(Hand myHand, Card upCard) {
        //BasicStrategy bs= new BasicStrategy();
        return BasicStrategy.getPlay(myHand, upCard);
    }
    
}
