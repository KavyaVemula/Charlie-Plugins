/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bot.server;

import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import charlie.util.Play;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kavyareddy
 * This class defines and implements Huey Bot that follows Basic Strategy
 */
public class Huey implements IBot, Runnable{
    final int MAX_THINKING=5;
    Seat mine;
    Hand myHand;
    Dealer dealer;
    Hid hid;
    Hid dealerID;
    Card dealerUpCard;
    HashMap<Hid,Hand> hands = new HashMap<>();
    Random ran = new Random();
    
    @Override
    public Hand getHand() {
        return myHand;
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.dealer= dealer;
    }

    @Override
    public void sit(Seat seat) {
        mine= seat;
        hid= new Hid(seat);
        myHand= new Hand(hid);
                
    }
    /**
     * This method is used to find the dealer HID and to store player HID in a HashMap
     * @param hids List of hids of players on table
     * @param shoeSize Size of the shoe
     */
    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        //stores the list of hid and corresponding hand in a hashMap
        for(int i=0;i<hids.size();i++){
            hands.put(hids.get(i), new Hand(hids.get(i)));
            //finding dealer ID
            if(hids.get(i).getSeat()== Seat.DEALER)
                this.dealerID=hids.get(i);
                
        }
    }

    @Override
    public void endGame(int shoeSize) {
        
    }
    /**
     * This method checks for the bot's turn and starts a thread
     * @param hid 
     * @param card 
     * @param values 
     */
    @Override
    public void deal(Hid hid, Card card, int[] values) {
        if(card == null)
            return;
        //hand can be null, so we check if its null and initialize it to the curred hid
        Hand hand= hands.get(hid);
        if(hand == null){
            hand= new Hand(hid);
            hands.put(hid, hand);
        }
        hand.hit(card);
        if(hid.getSeat()!=mine || hand.isBroke())
            return;
        new Thread(this).start();        
        
    }

    @Override
    public void insure() {
        
    }

    @Override
    public void bust(Hid hid) {
        
    }

    @Override
    public void win(Hid hid) {
        
    }

    @Override
    public void blackjack(Hid hid) {
        
    }

    @Override
    public void charlie(Hid hid) {
        
    }

    @Override
    public void lose(Hid hid) {
        
    }

    @Override
    public void push(Hid hid) {
        
    }

    @Override
    public void shuffling() {
        
    }
    /**
     * This method checks for your turn and invokes a thread.
     * This method is invoked whenever the deal is invoked
     * @param hid 
     */
    @Override
    public void play(Hid hid) {
        if(hid.getSeat()!=mine)
            return;
        new Thread(this).start();
    }
    /**
     * This method invokes the advise method and uses Basic Strategy before 
     * making a move.
     */
    @Override
    public void run() {
        try{
            Advisor advice= new Advisor();
            //finding the dealer's upCard
            dealerUpCard= hands.get(dealerID).getCard(0);
            //using the BasicStrategy to decide the bot's move
            Play move= advice.advise(myHand, dealerUpCard);
            //calling the modifiedStrategy function to deal with splits and doubke_down
            modifiedStrategy(move);
            
            //using sleep method for thread to make a bot act like human
            int thinking = ran.nextInt(MAX_THINKING * 1000);
            Thread.sleep(thinking);
            
            Hid myHid= myHand.getHid();
            
            switch(move){
                case HIT:dealer.hit(this, myHid);
                         break;
                case DOUBLE_DOWN:dealer.hit(this, myHid);
                                 break;                        
                case STAY:dealer.stay(this, myHid);
                          break;
                default:dealer.stay(this, myHid);
                        break;
                          
            }
            
        }catch(Exception e){
            System.out.println("Exception caught in run() method");
        }
    }
    /**
     * This method modifies the Basic Startegy 
     * If it encounter a split, it will give the next possible advice.
     * If it encounters double_down, it will return HIT
     * @param play
     * @return 
     */
    public Play modifiedStrategy(Play play){
        if(play!= Play.SPLIT)
            return play;
        if(play == Play.SPLIT){
            if(myHand.getValue()<=12)
                return Play.HIT;
            if(myHand.getValue()>=17)
                return Play.STAY;
           // if(myHand.getValue()>12 && myHand.getValue()<17){
           //     if(dealerUpCard.value()+10<=16)
           //         return Play.STAY;
           //     if(dealerUpCard.value()+10>16)
           //         return Play.HIT;
           // }
        }
        if(play == Play.DOUBLE_DOWN)
            return Play.HIT;
        //Adding this condition as this part is not dealt in Basic Strategy
        if(myHand.getValue()==4)
            return Play.HIT;
        return Play.NONE;
    }
    
}
