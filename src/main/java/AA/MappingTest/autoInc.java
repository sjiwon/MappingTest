package AA.MappingTest;

import javax.persistence.EntityManager;

public class autoInc {
    static String [] tables = {
            "users",
            "art",
            "hashtag",
            "art_hashtag",
            "auction",
            "auction_history",
            "like_art",
            "like_artist",
            "point_history",
            "purchase_history"
    };
    
    public void init(String [] tables){
        for (String table : tables) {
            
        }
    }
}
