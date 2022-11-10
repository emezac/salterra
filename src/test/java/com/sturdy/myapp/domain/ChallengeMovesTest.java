package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChallengeMovesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChallengeMoves.class);
        ChallengeMoves challengeMoves1 = new ChallengeMoves();
        challengeMoves1.setId(1L);
        ChallengeMoves challengeMoves2 = new ChallengeMoves();
        challengeMoves2.setId(challengeMoves1.getId());
        assertThat(challengeMoves1).isEqualTo(challengeMoves2);
        challengeMoves2.setId(2L);
        assertThat(challengeMoves1).isNotEqualTo(challengeMoves2);
        challengeMoves1.setId(null);
        assertThat(challengeMoves1).isNotEqualTo(challengeMoves2);
    }
}
