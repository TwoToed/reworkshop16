package ssf.reworkshop16.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.reworkshop16.model.Mastermind;

@Repository
public class BoardGameRepository {
    @Autowired
    private RedisTemplate<String, Object> template;
    
    // save board game to redis db
    public int saveGame(final Mastermind m){
        //sets the key value pair in redis,
        //the key is masterminds id, and the pair is the string representation of the JSON
        template.opsForValue().set(m.getId(), m.toJSON().toString());
        //have to make it (String) because we don't know whate get could return
        String result = (String) template.opsForValue().get(m.getId());
        if(null != result){
            return 1;
        }
        return 0;
    }

    public Mastermind findById(final String mid) throws IOException{
        Mastermind m = null;
        String jsonVal =(String) template.opsForValue().get(mid);
        if(jsonVal !=null){
            m = Mastermind.create(jsonVal);
        }
        return m;
    }

    public int updateBoardGame(final Mastermind m){
        String result = (String)template.opsForValue().get(m.getId());
        System.out.println("updateBoardGame " + result);
        if(m.isUpSert()){
            System.out.println("updateBoardGame upsert> " + result);
            if(result != null){
                template.opsForValue().set(m.getId(), m.toJSON().toString());
            }else{
                System.out.println(" insert record ! ");
                m.setId(m.generateId(8));
                template.opsForValue().setIfAbsent(m.getId(), m.toJSON().toString());
            }
        }else{
            if(result != null){
                template.opsForValue().set(m.getId(), m.toJSON().toString());
            }
        }

        result= (String) template.opsForValue().get(m.getId());
        if(result != null)
            return 1;
        return 0;
    }
}