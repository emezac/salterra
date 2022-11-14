import { IActivity } from 'app/shared/model/activity.model';
import { IPrize } from 'app/shared/model/prize.model';
import { IRoomConnection } from 'app/shared/model/room-connection.model';

export interface IRoom {
  id?: number;
  introText?: string | null;
  haveGatewayDoor?: boolean | null;
  gatewayDoor?: number | null;
  activities?: IActivity[] | null;
  prizes?: IPrize[] | null;
  roomConnections?: IRoomConnection[] | null;
}

export const defaultValue: Readonly<IRoom> = {
  haveGatewayDoor: false,
};
