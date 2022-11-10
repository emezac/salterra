import { IActivity } from 'app/shared/model/activity.model';

export interface IChallengeOption {
  id?: number;
  optionName?: string | null;
  option?: string | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IChallengeOption> = {};
