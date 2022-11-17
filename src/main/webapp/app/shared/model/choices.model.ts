import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { Action } from 'app/shared/model/enumerations/action.model';

export interface IChoices {
  id?: number;
  text?: string | null;
  action?: Action | null;
  activityMoves?: IActivityMoves[] | null;
}

export const defaultValue: Readonly<IChoices> = {};
