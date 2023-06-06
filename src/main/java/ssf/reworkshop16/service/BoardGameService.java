package ssf.reworkshop16.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.reworkshop16.model.Mastermind;
import ssf.reworkshop16.repository.BoardGameRepository;

@Service
public class BoardGameService {
    @Autowired
    private BoardGameRepository repo;

    public int saveGame(final Mastermind m){
        return this.repo.saveGame(m);
    }

    public Mastermind findById(final String mid) throws IOException{
        return this.repo.findById(mid);
        
    }
    
    public int updateBoardGame(final Mastermind m) {
        return this.repo.updateBoardGame(m);
    }
}
