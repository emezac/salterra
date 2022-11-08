package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfileGameStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileGameStatus.class);
        ProfileGameStatus profileGameStatus1 = new ProfileGameStatus();
        profileGameStatus1.setId(1L);
        ProfileGameStatus profileGameStatus2 = new ProfileGameStatus();
        profileGameStatus2.setId(profileGameStatus1.getId());
        assertThat(profileGameStatus1).isEqualTo(profileGameStatus2);
        profileGameStatus2.setId(2L);
        assertThat(profileGameStatus1).isNotEqualTo(profileGameStatus2);
        profileGameStatus1.setId(null);
        assertThat(profileGameStatus1).isNotEqualTo(profileGameStatus2);
    }
}
