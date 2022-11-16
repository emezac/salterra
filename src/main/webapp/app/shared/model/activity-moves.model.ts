import dayjs from 'dayjs';
import { IChoices } from 'app/shared/model/choices.model';
import { IMove } from 'app/shared/model/move.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IActivityMoves {
  id?: number;
  moveDate?: string | null;
  choices?: IChoices[] | null;
  move?: IMove | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IActivityMoves> = {};
