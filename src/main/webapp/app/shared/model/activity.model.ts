import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { IRoom } from 'app/shared/model/room.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { TypeOfChallenge } from 'app/shared/model/enumerations/type-of-challenge.model';
import { FailureResult } from 'app/shared/model/enumerations/failure-result.model';
import { SuccessResult } from 'app/shared/model/enumerations/success-result.model';
import { SkipResult } from 'app/shared/model/enumerations/skip-result.model';

export interface IActivity {
  id?: number;
  successText?: string;
  failureText?: string;
  skipText?: string | null;
  challangeDifficulty?: Difficulty | null;
  challengeType?: TypeOfChallenge | null;
  failureResult?: FailureResult | null;
  successResult?: SuccessResult | null;
  skipResult?: SkipResult | null;
  sW?: string | null;
  e?: string | null;
  nW?: string | null;
  n?: string | null;
  sE?: string | null;
  s?: string | null;
  nE?: string | null;
  w?: string | null;
  activityMoves?: IActivityMoves[] | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IActivity> = {};
