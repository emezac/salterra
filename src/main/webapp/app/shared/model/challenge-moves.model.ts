import dayjs from 'dayjs';
import { IChallenge } from 'app/shared/model/challenge.model';
import { IMove } from 'app/shared/model/move.model';

export interface IChallengeMoves {
  id?: number;
  moveDate?: string | null;
  challenge?: IChallenge | null;
  move?: IMove | null;
}

export const defaultValue: Readonly<IChallengeMoves> = {};
