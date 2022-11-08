import { IFloorRooms } from 'app/shared/model/floor-rooms.model';
import { IChallenge } from 'app/shared/model/challenge.model';
import { IPrize } from 'app/shared/model/prize.model';

export interface IRoom {
  id?: number;
  introText?: string | null;
  haveGatewayDoor?: boolean | null;
  gatewayDoor?: number | null;
  floorRooms?: IFloorRooms[] | null;
  challenges?: IChallenge[] | null;
  prizes?: IPrize[] | null;
}

export const defaultValue: Readonly<IRoom> = {
  haveGatewayDoor: false,
};
