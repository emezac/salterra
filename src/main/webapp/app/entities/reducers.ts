import move from 'app/entities/move/move.reducer';
import prize from 'app/entities/prize/prize.reducer';
import profile from 'app/entities/profile/profile.reducer';
import card from 'app/entities/card/card.reducer';
import room from 'app/entities/room/room.reducer';
import choices from 'app/entities/choices/choices.reducer';
import activity from 'app/entities/activity/activity.reducer';
import gameStatus from 'app/entities/game-status/game-status.reducer';
import activityMoves from 'app/entities/activity-moves/activity-moves.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  move,
  prize,
  profile,
  card,
  room,
  choices,
  activity,
  gameStatus,
  activityMoves,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
