import { IFloor } from 'app/shared/model/floor.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IFloorRooms {
  id?: number;
  floor?: IFloor | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IFloorRooms> = {};
