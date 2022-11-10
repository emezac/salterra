import { IChoice } from 'app/shared/model/choice.model';
import { IPrizePool } from 'app/shared/model/prize-pool.model';
import { IChallengeMoves } from 'app/shared/model/challenge-moves.model';
import { IRoom } from 'app/shared/model/room.model';
import { TypesOfChallenge } from 'app/shared/model/enumerations/types-of-challenge.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IChallenge {
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
  challengeMoves?: IChallengeMoves[] | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IChallenge> = {};
