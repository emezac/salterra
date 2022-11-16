import dayjs from 'dayjs';
import { IProfile } from 'app/shared/model/profile.model';
import { IMove } from 'app/shared/model/move.model';

export interface IGameStatus {
  id?: number;
  moveDate?: string | null;
  lastRoomVisited?: number | null;
  profile?: IProfile | null;
  moves?: IMove[] | null;
}

export const defaultValue: Readonly<IGameStatus> = {};
