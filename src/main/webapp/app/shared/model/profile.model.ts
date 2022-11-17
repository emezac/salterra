import dayjs from 'dayjs';
import { IPrize } from 'app/shared/model/prize.model';
import { IGameStatus } from 'app/shared/model/game-status.model';

export interface IProfile {
  id?: string;
  name?: string;
  auth0UserId?: string;
  socialNetwork?: string | null;
  profileImageContentType?: string | null;
  profileImage?: string | null;
  suspended?: boolean | null;
  suspendedCount?: number | null;
  banned?: boolean | null;
  aclSetup?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  deletedAt?: string | null;
  prizes?: IPrize[] | null;
  gameStatus?: IGameStatus | null;
}

export const defaultValue: Readonly<IProfile> = {
  suspended: false,
  banned: false,
};
