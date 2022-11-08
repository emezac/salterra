import profile from 'app/entities/profile/profile.reducer';
import profileGameStatus from 'app/entities/profile-game-status/profile-game-status.reducer';
import card from 'app/entities/card/card.reducer';
import dungeon from 'app/entities/dungeon/dungeon.reducer';
import dungeonFloors from 'app/entities/dungeon-floors/dungeon-floors.reducer';
import floor from 'app/entities/floor/floor.reducer';
import floorRooms from 'app/entities/floor-rooms/floor-rooms.reducer';
import prize from 'app/entities/prize/prize.reducer';
import room from 'app/entities/room/room.reducer';
import challenge from 'app/entities/challenge/challenge.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  profile,
  profileGameStatus,
  card,
  dungeon,
  dungeonFloors,
  floor,
  floorRooms,
  prize,
  room,
  challenge,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
