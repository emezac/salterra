import { IActivity } from 'app/shared/model/activity.model';
import { Action } from 'app/shared/model/enumerations/action.model';

export interface IChoices {
  id?: number;
  text?: string | null;
  action?: Action | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IChoices> = {};
