import dayjs from 'dayjs';
import { IPrize } from 'app/shared/model/prize.model';

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
}

export const defaultValue: Readonly<IProfile> = {
  suspended: false,
  banned: false,
};
