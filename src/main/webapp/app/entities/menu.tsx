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
      <MenuItem icon="asterisk" to="/choices">
        Choices
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity">
        Activity
      </MenuItem>
      <MenuItem icon="asterisk" to="/game-status">
        Game Status
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity-moves">
        Activity Moves
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
