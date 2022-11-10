import { IGameStatus } from 'app/shared/model/game-status.model';
import { IChallenge } from 'app/shared/model/challenge.model';

export interface IMove {
  id?: number;
  moveNumber?: string | null;
  gameStatus?: IGameStatus | null;
  challenges?: IChallenge[] | null;
}

export const defaultValue: Readonly<IMove> = {};
