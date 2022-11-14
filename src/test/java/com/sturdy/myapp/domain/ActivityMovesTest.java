package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivityMovesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityMoves.class);
        ActivityMoves activityMoves1 = new ActivityMoves();
        activityMoves1.setId(1L);
        ActivityMoves activityMoves2 = new ActivityMoves();
        activityMoves2.setId(activityMoves1.getId());
        assertThat(activityMoves1).isEqualTo(activityMoves2);
        activityMoves2.setId(2L);
        assertThat(activityMoves1).isNotEqualTo(activityMoves2);
        activityMoves1.setId(null);
        assertThat(activityMoves1).isNotEqualTo(activityMoves2);
    }
}
