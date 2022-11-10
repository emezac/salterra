import { IChoice } from 'app/shared/model/choice.model';
import { IPrizePool } from 'app/shared/model/prize-pool.model';
import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { IRoom } from 'app/shared/model/room.model';
import { TypesOfChallenge } from 'app/shared/model/enumerations/types-of-challenge.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IActivity {
  id?: number;
  introText?: string;
  successText?: string;
  failureText?: string;
  skipText?: string | null;
  challengeName?: TypesOfChallenge | null;
  difficulty?: Difficulty | null;
  prizeNumber?: string | null;
  challangeRating?: string | null;
  sacrificeNumber?: string | null;
  skipResult?: string | null;
  choices?: IChoice[] | null;
  prizePools?: IPrizePool[] | null;
  activityMoves?: IActivityMoves[] | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IActivity> = {};
