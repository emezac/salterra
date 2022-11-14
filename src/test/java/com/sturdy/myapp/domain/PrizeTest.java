package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrizeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prize.class);
        Prize prize1 = new Prize();
        prize1.setId(1L);
        Prize prize2 = new Prize();
        prize2.setId(prize1.getId());
        assertThat(prize1).isEqualTo(prize2);
        prize2.setId(2L);
        assertThat(prize1).isNotEqualTo(prize2);
        prize1.setId(null);
        assertThat(prize1).isNotEqualTo(prize2);
    }
}
