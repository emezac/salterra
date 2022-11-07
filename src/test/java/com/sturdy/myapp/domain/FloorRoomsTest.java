package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FloorRoomsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FloorRooms.class);
        FloorRooms floorRooms1 = new FloorRooms();
        floorRooms1.setId(1L);
        FloorRooms floorRooms2 = new FloorRooms();
        floorRooms2.setId(floorRooms1.getId());
        assertThat(floorRooms1).isEqualTo(floorRooms2);
        floorRooms2.setId(2L);
        assertThat(floorRooms1).isNotEqualTo(floorRooms2);
        floorRooms1.setId(null);
        assertThat(floorRooms1).isNotEqualTo(floorRooms2);
    }
}
