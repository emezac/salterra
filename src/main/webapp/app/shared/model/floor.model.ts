import { IDungeonFloors } from 'app/shared/model/dungeon-floors.model';
import { IFloorRooms } from 'app/shared/model/floor-rooms.model';

export interface IFloor {
  id?: number;
  dungeonFloors?: IDungeonFloors[] | null;
  floorRooms?: IFloorRooms[] | null;
}

export const defaultValue: Readonly<IFloor> = {};
