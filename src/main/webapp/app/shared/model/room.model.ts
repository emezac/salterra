import { IActivity } from 'app/shared/model/activity.model';
import { IPrize } from 'app/shared/model/prize.model';
import { IRoomConnection } from 'app/shared/model/room-connection.model';

export interface IRoom {
  id?: number;
  introText?: string | null;
  haveGatewayDoor?: boolean | null;
  gwyDoor1?: number | null;
  gwyDoor2?: number | null;
  gwyDoor3?: number | null;
  gwyDoor4?: number | null;
  gwyDoor5?: number | null;
  gwyDoor6?: number | null;
  activities?: IActivity[] | null;
  prizes?: IPrize[] | null;
  roomConnections?: IRoomConnection[] | null;
}

export const defaultValue: Readonly<IRoom> = {
  haveGatewayDoor: false,
};
