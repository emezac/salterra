import { IPrize } from 'app/shared/model/prize.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IPrizePool {
  id?: number;
  prizeName?: string | null;
  prizes?: IPrize[] | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IPrizePool> = {};
