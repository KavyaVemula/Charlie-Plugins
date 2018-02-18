/*
 * This class implements the Basic Strategy table
 * 
 */
package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import java.util.*;
        

/**
 *
 * @author kavyareddy
 */
public class BasicStrategy {
    static String CardOneValue;
    static String CardTwoValue;
    static String DealerCardValue;
    static Play Result[];

    protected static HashMap<String, Play[]> sameHashTable = new HashMap<>();
    protected static HashMap<String, Play[]> AceHashTable = new HashMap<>();
    protected static HashMap<String, Play[]> ValueHashTable = new HashMap<>();
        
    public BasicStrategy(){
        SameTableValues();
        AceTableValues();
        ValueTableValues();
    }
        
    /**This method stores values of Section 4 into the Hash Table
     * 
     */
    public static void SameTableValues(){
        Play[] playVal1= { Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, 
        Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};  
        sameHashTable.put("2,2", playVal1);
        sameHashTable.put("3,3", playVal1);
        playVal1= new Play[]{ Play.HIT, Play.HIT, Play.HIT, Play.SPLIT, Play.SPLIT, 
        Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        sameHashTable.put("4,4", playVal1);
        playVal1= new Play[]{ Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, 
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT, Play.HIT};
        sameHashTable.put("5,5", playVal1);
        playVal1= new Play[]{ Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT,
        Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT}; 
        sameHashTable.put("6,6", playVal1);
        playVal1= new Play[]{ Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT,
        Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        sameHashTable.put("7,7", playVal1);
        playVal1= new Play[]{ Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT,
        Play.SPLIT, Play.STAY, Play.SPLIT, Play.SPLIT, Play.STAY, Play.STAY};
        sameHashTable.put("9,9", playVal1);
        playVal1= new Play[]{ Play.STAY, Play.STAY, Play.STAY, Play.STAY, 
        Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY};
        sameHashTable.put("10,10", playVal1);
        playVal1= new Play[]{ Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT,
        Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT};
        sameHashTable.put("8,8", playVal1);
        sameHashTable.put("A,A", playVal1);
    }
    
    /**This method stores values into Hash Table when it encounters an Ace card
     * 
     */
    public static void AceTableValues(){
        Play[] playVal2= {Play.HIT, Play.HIT, Play.HIT, Play.DOUBLE_DOWN, 
        Play.DOUBLE_DOWN, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT}; 
        AceHashTable.put("A,2", playVal2);
        AceHashTable.put("A,3", playVal2);
        playVal2= new Play[]{ Play.HIT, Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
        Play.DOUBLE_DOWN, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        AceHashTable.put("A,4", playVal2);
        AceHashTable.put("A,5", playVal2);
        playVal2= new Play[]{ Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, 
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        AceHashTable.put("A,6", playVal2);
        playVal2= new Play[]{ Play.STAY, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, 
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT};
        AceHashTable.put("A,7", playVal2);
        playVal2= new Play[]{ Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY,
        Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY};
        AceHashTable.put("A,8", playVal2);
        AceHashTable.put("A,9", playVal2);
        AceHashTable.put("A,10", playVal2);
    }
    
    /**This method stores values into Hash Table when the total hand value is between 5 and 11
     * 
     */
    public static void ValueTableValues(){
        Play[] playVal3= { Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, 
        Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        ValueHashTable.put("5", playVal3);
        ValueHashTable.put("6", playVal3);
        ValueHashTable.put("7", playVal3);
        ValueHashTable.put("8", playVal3);
        playVal3= new Play[]{ Play.HIT, Play.DOUBLE_DOWN,Play.DOUBLE_DOWN,Play.DOUBLE_DOWN,
        Play.DOUBLE_DOWN, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        ValueHashTable.put("9", playVal3);
        playVal3= new Play[]{ Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, 
        Play.DOUBLE_DOWN, Play.HIT, Play.HIT};
        ValueHashTable.put("10", playVal3);
        playVal3= new Play[]{ Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, 
        Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT};
        ValueHashTable.put("11", playVal3);
        playVal3= new Play[]{ Play.HIT, Play.HIT, Play.STAY, Play.STAY, Play.STAY,
        Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        ValueHashTable.put("12", playVal3);
        playVal3= new Play[]{ Play.STAY, Play.STAY, Play.STAY, Play.STAY, 
        Play.STAY, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT};
        ValueHashTable.put("13", playVal3);
        ValueHashTable.put("14", playVal3);
        ValueHashTable.put("15", playVal3);
        ValueHashTable.put("16", playVal3);
        playVal3= new Play[]{ Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY,
        Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY};
        ValueHashTable.put("17", playVal3);
        ValueHashTable.put("18", playVal3);
        ValueHashTable.put("19", playVal3);
        ValueHashTable.put("20", playVal3); 
    
    }

        
    public static Play getPlay(Hand myHand, Card upCard){
        //Checking Validations
        if(myHand.getValue()>0 && myHand.getValue()<21 && myHand.size()==2 && upCard!=null){
            Card CardOne= myHand.getCard(0);
            Card CardTwo= myHand.getCard(1);
            //Converting the Card values to String and storing them.            
            if(CardOne.isAce())
                CardOneValue= "A";
            else
                CardOneValue= Integer.toString(CardOne.value());
            if(CardTwo.isAce())
                CardTwoValue= "A";
            else
                CardTwoValue= Integer.toString(CardTwo.value());
            if(upCard.isAce())
                DealerCardValue= "A";
            else
                DealerCardValue= Integer.toString(upCard.value());
            int size= myHand.size();
            
            //Extracting the row from the Basic Strategy by comparing myHand Values.
            //Play Result[];
            //Section 3
            if(CardOneValue.equals(CardTwoValue) && size==2){
                Result = sameHashTable.get(CardOneValue+","+CardTwoValue);
            }
            
            //Section 2
            if(CardOneValue.equals("A") && !CardTwoValue.equals("A") || CardTwoValue.equals("A") && !CardOneValue.equals("A") && size==2){
               String OtherCard= CardOne.isAce() ? CardTwoValue : CardOneValue;
               Result = AceHashTable.get("A,"+OtherCard);
            }
                       
            //Section 1
            else{
                String MyHandTotal= Integer.toString(myHand.getValue());
                Result = ValueHashTable.get(MyHandTotal);
            }
            
            //Comparing with Dealer's upCard and returning the Advisable Action.
            if(DealerCardValue.equals("A"))
                return Result[9];
            else {
                return Result[Integer.parseInt(DealerCardValue)-2];
            }
        }
        else {
            return Play.STAY;
        }
    }
}
