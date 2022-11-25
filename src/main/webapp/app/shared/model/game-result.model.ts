import dayjs from 'dayjs';
import { IMyUser } from 'app/shared/model/my-user.model';

export interface IGameResult {
  id?: number;
  won?: boolean | null;
  lost?: boolean | null;
  createdAt?: string | null;
  myUser?: IMyUser | null;
}

export const defaultValue: Readonly<IGameResult> = {
  won: false,
  lost: false,
};
