import { IGameStatus } from 'app/shared/model/game-status.model';
import { IChallenge } from 'app/shared/model/challenge.model';

export interface IMove {
  id?: number;
  moveNumber?: string | null;
  move?: IGameStatus | null;
  moves?: IChallenge[] | null;
}

export const defaultValue: Readonly<IMove> = {};
