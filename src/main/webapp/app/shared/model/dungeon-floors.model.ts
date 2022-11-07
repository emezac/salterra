import { IDungeon } from 'app/shared/model/dungeon.model';
import { IFloor } from 'app/shared/model/floor.model';

export interface IDungeonFloors {
  id?: number;
  dgId?: number | null;
  flId?: number | null;
  dungeon?: IDungeon | null;
  floor?: IFloor | null;
}

export const defaultValue: Readonly<IDungeonFloors> = {};
