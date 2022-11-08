import { IDungeon } from 'app/shared/model/dungeon.model';
import { IFloor } from 'app/shared/model/floor.model';

export interface IDungeonFloors {
  id?: number;
  dungeon?: IDungeon | null;
  floor?: IFloor | null;
}

export const defaultValue: Readonly<IDungeonFloors> = {};
