import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/profile">
        Profile
      </MenuItem>
      <MenuItem icon="asterisk" to="/profile-game-status">
        Profile Game Status
      </MenuItem>
      <MenuItem icon="asterisk" to="/card">
        Card
      </MenuItem>
      <MenuItem icon="asterisk" to="/dungeon">
        Dungeon
      </MenuItem>
      <MenuItem icon="asterisk" to="/dungeon-floors">
        Dungeon Floors
      </MenuItem>
      <MenuItem icon="asterisk" to="/floor">
        Floor
      </MenuItem>
      <MenuItem icon="asterisk" to="/floor-rooms">
        Floor Rooms
      </MenuItem>
      <MenuItem icon="asterisk" to="/prize">
        Prize
      </MenuItem>
      <MenuItem icon="asterisk" to="/room">
        Room
      </MenuItem>
      <MenuItem icon="asterisk" to="/challenge">
        Challenge
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
