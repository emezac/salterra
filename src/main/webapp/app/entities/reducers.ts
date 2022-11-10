import move from 'app/entities/move/move.reducer';
import prize from 'app/entities/prize/prize.reducer';
import profile from 'app/entities/profile/profile.reducer';
import card from 'app/entities/card/card.reducer';
import room from 'app/entities/room/room.reducer';
import choice from 'app/entities/choice/choice.reducer';
import option from 'app/entities/option/option.reducer';
import prizePool from 'app/entities/prize-pool/prize-pool.reducer';
import challenge from 'app/entities/challenge/challenge.reducer';
import gameStatus from 'app/entities/game-status/game-status.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  move,
  prize,
  profile,
  card,
  room,
  choice,
  option,
  prizePool,
  challenge,
  gameStatus,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
