import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/application-user">
        Application User
      </MenuItem>
      <MenuItem icon="asterisk" to="/course">
        Course
      </MenuItem>
      <MenuItem icon="asterisk" to="/certificate">
        Certificate
      </MenuItem>
      <MenuItem icon="asterisk" to="/event">
        Event
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
