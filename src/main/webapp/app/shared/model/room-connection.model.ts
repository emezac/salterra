import { IRoom } from 'app/shared/model/room.model';

export interface IRoomConnection {
  id?: number;
  fromId?: number | null;
  toId?: number | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IRoomConnection> = {};
