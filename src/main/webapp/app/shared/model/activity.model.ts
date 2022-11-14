import { IChallengeOption } from 'app/shared/model/challenge-option.model';
import { IPrizePool } from 'app/shared/model/prize-pool.model';
import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { IRoom } from 'app/shared/model/room.model';
import { Choice } from 'app/shared/model/enumerations/choice.model';
import { TypesOfChallenge } from 'app/shared/model/enumerations/types-of-challenge.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IActivity {
  id?: number;
  introText?: string;
  successText?: string;
  failureText?: string;
  skipText?: string | null;
  typeChallenge?: Choice | null;
  challengeName?: TypesOfChallenge | null;
  difficulty?: Difficulty | null;
  challangeRating?: Difficulty | null;
  sacrificeNumber?: string | null;
  skipResult?: string | null;
  challengeOptions?: IChallengeOption[] | null;
  prizePools?: IPrizePool[] | null;
  activityMoves?: IActivityMoves[] | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IActivity> = {};
