package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChallengeOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChallengeOption.class);
        ChallengeOption challengeOption1 = new ChallengeOption();
        challengeOption1.setId(1L);
        ChallengeOption challengeOption2 = new ChallengeOption();
        challengeOption2.setId(challengeOption1.getId());
        assertThat(challengeOption1).isEqualTo(challengeOption2);
        challengeOption2.setId(2L);
        assertThat(challengeOption1).isNotEqualTo(challengeOption2);
        challengeOption1.setId(null);
        assertThat(challengeOption1).isNotEqualTo(challengeOption2);
    }
}
