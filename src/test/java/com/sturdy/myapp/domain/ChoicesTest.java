package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChoicesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Choices.class);
        Choices choices1 = new Choices();
        choices1.setId(1L);
        Choices choices2 = new Choices();
        choices2.setId(choices1.getId());
        assertThat(choices1).isEqualTo(choices2);
        choices2.setId(2L);
        assertThat(choices1).isNotEqualTo(choices2);
        choices1.setId(null);
        assertThat(choices1).isNotEqualTo(choices2);
    }
}
