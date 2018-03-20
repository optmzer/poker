/**
 * TODO:
 * Find out how I can sort a Hand by both 
 * 1 - group_type and then by card rank.
 */
package poker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
    GROUP_OF_2,
    GROUP_OF_3,
    GROUP_OF_4,
 *  GROUP_OF_5,
    KICKER;
 * @author Alexander Frolov
 */
public class CardGroup implements Comparable<CardGroup>{
    
    private List<Card> card_group;
    private Enum group_type;
    
    public CardGroup(List<Card> card_group, Enum group_type){
        this.setCardGroup(card_group);
        this.setGroupType(group_type);
        this.sortCardGroup();
    }
    
    //sort cards inside the group
    private void sortCardGroup(){
        if(this.group_type == CardGroupType.KICKER || this.group_type == CardGroupType.GROUP_OF_5){
            Collections.sort(this.card_group, (Card card1, Card card2) -> {
                return card1.compareTo(card2);
            });
        }
    }//sortCardGroup()
    
    public final void setCardGroup(List<Card> group){
        this.card_group = group;
    }
    
    public final void setGroupType(Enum group_type){
        this.group_type = group_type;
    }
    
    public Enum getGroupType(){
        return this.group_type;
    }
    
    public List<Card> getCardGroup(){
        return this.card_group;
    }

//    public void sortCardGroupByCardRank(){
//        
//    }
    
    @Override
    public int compareTo(CardGroup other_group) {
        return this.getGroupType().compareTo(other_group.getGroupType());
    }
    
}//class
