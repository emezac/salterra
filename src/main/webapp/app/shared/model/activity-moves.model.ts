import dayjs from 'dayjs';
import { IActivity } from 'app/shared/model/activity.model';
import { IMove } from 'app/shared/model/move.model';

export interface IActivityMoves {
  id?: number;
  moveDate?: string | null;
  activity?: IActivity | null;
  move?: IMove | null;
}

export const defaultValue: Readonly<IActivityMoves> = {};
