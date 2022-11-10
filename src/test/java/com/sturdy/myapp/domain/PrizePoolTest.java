package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrizePoolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrizePool.class);
        PrizePool prizePool1 = new PrizePool();
        prizePool1.setId(1L);
        PrizePool prizePool2 = new PrizePool();
        prizePool2.setId(prizePool1.getId());
        assertThat(prizePool1).isEqualTo(prizePool2);
        prizePool2.setId(2L);
        assertThat(prizePool1).isNotEqualTo(prizePool2);
        prizePool1.setId(null);
        assertThat(prizePool1).isNotEqualTo(prizePool2);
    }
}
