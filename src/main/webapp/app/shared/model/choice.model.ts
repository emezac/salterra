import { IOption } from 'app/shared/model/option.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IChoice {
  id?: number;
  choiceName?: string | null;
  action?: string | null;
  text?: string | null;
  options?: IOption[] | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IChoice> = {};
