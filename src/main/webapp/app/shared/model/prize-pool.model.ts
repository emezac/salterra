import { IPrize } from 'app/shared/model/prize.model';
import { IChallenge } from 'app/shared/model/challenge.model';

export interface IPrizePool {
  id?: number;
  prizeName?: string | null;
  prizes?: IPrize[] | null;
  challenge?: IChallenge | null;
}

export const defaultValue: Readonly<IPrizePool> = {};
