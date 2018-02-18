/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bs.section4;

import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author kavyareddy
 */
public class Test01_22_2 {
    
    public Test01_22_2() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test() {
    System.out.println("My Hand- 5,5; Dealer Upcard- 2");
        Card Card1= new Card(5, Card.Suit.CLUBS);
        Card Card2= new Card(5, Card.Suit.DIAMONDS);
        Card upCard=new Card(2, Card.Suit.CLUBS);
        
        Hid myId=new Hid(Seat.NONE);
        Hand myHand=new Hand(myId);
        
        myHand.hit(Card1);
        myHand.hit(Card2);
        
        Advisor myAdvisor=new Advisor();
        Play result= myAdvisor.advise(myHand, upCard);
        
        Assert.assertEquals(Play.DOUBLE_DOWN, result);}
}
