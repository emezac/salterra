package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameStatus.class);
        GameStatus gameStatus1 = new GameStatus();
        gameStatus1.setId(1L);
        GameStatus gameStatus2 = new GameStatus();
        gameStatus2.setId(gameStatus1.getId());
        assertThat(gameStatus1).isEqualTo(gameStatus2);
        gameStatus2.setId(2L);
        assertThat(gameStatus1).isNotEqualTo(gameStatus2);
        gameStatus1.setId(null);
        assertThat(gameStatus1).isNotEqualTo(gameStatus2);
    }
}
