package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DungeonFloorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DungeonFloors.class);
        DungeonFloors dungeonFloors1 = new DungeonFloors();
        dungeonFloors1.setId(1L);
        DungeonFloors dungeonFloors2 = new DungeonFloors();
        dungeonFloors2.setId(dungeonFloors1.getId());
        assertThat(dungeonFloors1).isEqualTo(dungeonFloors2);
        dungeonFloors2.setId(2L);
        assertThat(dungeonFloors1).isNotEqualTo(dungeonFloors2);
        dungeonFloors1.setId(null);
        assertThat(dungeonFloors1).isNotEqualTo(dungeonFloors2);
    }
}
