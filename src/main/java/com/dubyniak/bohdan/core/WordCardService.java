package com.dubyniak.bohdan.core;

import com.dubyniak.bohdan.api.WordCard;
import com.dubyniak.bohdan.db.dao.WordCardDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class WordCardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordCardService.class);
    private static final String NO_WORD_CARD_MESSAGE = "There is no word card with id = %s.";

    @Autowired
    public WordCardService(WordCardDAO wordCardDAO) {
        this.wordCardDAO = wordCardDAO;
    }

    private final WordCardDAO wordCardDAO;

    public List<WordCard> getWordCards() {
        return wordCardDAO.getAll();
    }

    public WordCard getWordCard(Long id) {
        WordCard card = wordCardDAO.get(id);
        if (card == null) {
            LOGGER.error(String.format(NO_WORD_CARD_MESSAGE, id));
            throw new NotFoundException(String.format(NO_WORD_CARD_MESSAGE, id));
        }
        return card;
    }

    public WordCard createWordCard(WordCard wordCard) {
        Long id = wordCardDAO.create(wordCard);
        LOGGER.info("A word card with id = " + id + " has been successfully created.");
        return getWordCard(id);
    }

    public WordCard updateWordCard(Long id, WordCard wordCard) {
        if (getWordCard(id) == null) {
            LOGGER.error(String.format(NO_WORD_CARD_MESSAGE, id));
            throw new NotFoundException(String.format(NO_WORD_CARD_MESSAGE, id));
        }
        wordCardDAO.update(id, wordCard);
        LOGGER.info("A word card with id = " + id + " has been successfully updated.");
        return getWordCard(id);
    }

    public void deleteWordCard(Long id) {
        if (getWordCard(id) == null) {
            LOGGER.error(String.format(NO_WORD_CARD_MESSAGE, id));
            throw new NotFoundException(String.format(NO_WORD_CARD_MESSAGE, id));
        }
        wordCardDAO.delete(id);
        LOGGER.info("A word card with id = " + id + " has been successfully deleted.");
    }
}
