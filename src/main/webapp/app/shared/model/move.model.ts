import dayjs from 'dayjs';
import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { IGameStatus } from 'app/shared/model/game-status.model';

export interface IMove {
  id?: number;
  moveNumber?: string | null;
  moveDate?: string | null;
  activityMoves?: IActivityMoves[] | null;
  gameStatus?: IGameStatus | null;
}

export const defaultValue: Readonly<IMove> = {};
