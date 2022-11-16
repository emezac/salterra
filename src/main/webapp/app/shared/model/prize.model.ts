import { IProfile } from 'app/shared/model/profile.model';
import { ICard } from 'app/shared/model/card.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IPrize {
  id?: number;
  description?: string | null;
  profile?: IProfile | null;
  card?: ICard | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IPrize> = {};
