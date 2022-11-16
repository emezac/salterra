import { IActivity } from 'app/shared/model/activity.model';
import { IPrize } from 'app/shared/model/prize.model';

export interface IRoom {
  id?: number;
  introText?: string;
  activities?: IActivity[] | null;
  prizes?: IPrize[] | null;
}

export const defaultValue: Readonly<IRoom> = {};
