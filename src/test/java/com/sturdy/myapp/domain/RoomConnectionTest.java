package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomConnectionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomConnection.class);
        RoomConnection roomConnection1 = new RoomConnection();
        roomConnection1.setId(1L);
        RoomConnection roomConnection2 = new RoomConnection();
        roomConnection2.setId(roomConnection1.getId());
        assertThat(roomConnection1).isEqualTo(roomConnection2);
        roomConnection2.setId(2L);
        assertThat(roomConnection1).isNotEqualTo(roomConnection2);
        roomConnection1.setId(null);
        assertThat(roomConnection1).isNotEqualTo(roomConnection2);
    }
}
