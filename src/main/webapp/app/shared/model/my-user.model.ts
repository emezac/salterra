import dayjs from 'dayjs';
import { IGameResult } from 'app/shared/model/game-result.model';

export interface IMyUser {
  id?: number;
  walletAddress?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  gameResults?: IGameResult[] | null;
}

export const defaultValue: Readonly<IMyUser> = {};
