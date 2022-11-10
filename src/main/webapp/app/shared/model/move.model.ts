import { IChallengeMoves } from 'app/shared/model/challenge-moves.model';
import { IGameStatus } from 'app/shared/model/game-status.model';

export interface IMove {
  id?: number;
  moveNumber?: string | null;
  challengeMoves?: IChallengeMoves[] | null;
  gameStatus?: IGameStatus | null;
}

export const defaultValue: Readonly<IMove> = {};
