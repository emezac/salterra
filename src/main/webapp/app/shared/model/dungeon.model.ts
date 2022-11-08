import dayjs from 'dayjs';
import { IDungeonFloors } from 'app/shared/model/dungeon-floors.model';
import { IProfileGameStatus } from 'app/shared/model/profile-game-status.model';

export interface IDungeon {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  dungeonFloors?: IDungeonFloors[] | null;
  profileGameStatus?: IProfileGameStatus | null;
}

export const defaultValue: Readonly<IDungeon> = {};
