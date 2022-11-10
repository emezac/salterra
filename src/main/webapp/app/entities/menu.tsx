import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/move">
        Move
      </MenuItem>
      <MenuItem icon="asterisk" to="/prize">
        Prize
      </MenuItem>
      <MenuItem icon="asterisk" to="/profile">
        Profile
      </MenuItem>
      <MenuItem icon="asterisk" to="/card">
        Card
      </MenuItem>
      <MenuItem icon="asterisk" to="/room">
        Room
      </MenuItem>
      <MenuItem icon="asterisk" to="/choice">
        Choice
      </MenuItem>
      <MenuItem icon="asterisk" to="/option">
        Option
      </MenuItem>
      <MenuItem icon="asterisk" to="/prize-pool">
        Prize Pool
      </MenuItem>
      <MenuItem icon="asterisk" to="/challenge">
        Challenge
      </MenuItem>
      <MenuItem icon="asterisk" to="/game-status">
        Game Status
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
