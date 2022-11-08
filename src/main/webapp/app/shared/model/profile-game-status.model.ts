import dayjs from 'dayjs';
import { IProfile } from 'app/shared/model/profile.model';
import { IDungeon } from 'app/shared/model/dungeon.model';

export interface IProfileGameStatus {
  id?: number;
  moveDate?: string | null;
  lastRoomVisited?: number | null;
  profile?: IProfile | null;
  dungeons?: IDungeon[] | null;
}

export const defaultValue: Readonly<IProfileGameStatus> = {};
