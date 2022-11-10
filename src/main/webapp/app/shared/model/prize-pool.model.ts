import { IPrize } from 'app/shared/model/prize.model';

export interface IPrizePool {
  id?: number;
  prizes?: IPrize[] | null;
}

export const defaultValue: Readonly<IPrizePool> = {};
