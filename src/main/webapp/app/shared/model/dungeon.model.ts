import dayjs from 'dayjs';
import { IDungeonFloors } from 'app/shared/model/dungeon-floors.model';

export interface IDungeon {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  dungeonFloors?: IDungeonFloors[] | null;
}

export const defaultValue: Readonly<IDungeon> = {};
