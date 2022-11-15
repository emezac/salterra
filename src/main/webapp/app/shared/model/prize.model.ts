import { IProfile } from 'app/shared/model/profile.model';
import { ICard } from 'app/shared/model/card.model';
import { IRoom } from 'app/shared/model/room.model';
import { IPrizePool } from 'app/shared/model/prize-pool.model';

export interface IPrize {
  id?: number;
  options?: string | null;
  profile?: IProfile | null;
  card?: ICard | null;
  room?: IRoom | null;
  prizePool?: IPrizePool | null;
}

export const defaultValue: Readonly<IPrize> = {};
