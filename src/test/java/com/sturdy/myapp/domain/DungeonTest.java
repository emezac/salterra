package com.sturdy.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sturdy.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DungeonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dungeon.class);
        Dungeon dungeon1 = new Dungeon();
        dungeon1.setId(1L);
        Dungeon dungeon2 = new Dungeon();
        dungeon2.setId(dungeon1.getId());
        assertThat(dungeon1).isEqualTo(dungeon2);
        dungeon2.setId(2L);
        assertThat(dungeon1).isNotEqualTo(dungeon2);
        dungeon1.setId(null);
        assertThat(dungeon1).isNotEqualTo(dungeon2);
    }
}
