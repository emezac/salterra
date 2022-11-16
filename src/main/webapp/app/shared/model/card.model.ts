import dayjs from 'dayjs';
import { IPrize } from 'app/shared/model/prize.model';

export interface ICard {
  id?: number;
  thumbnaiImageContentType?: string | null;
  thumbnaiImage?: string | null;
  highresImageContentType?: string | null;
  highresImage?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  deletedAt?: string | null;
  prizes?: IPrize[] | null;
}

export const defaultValue: Readonly<ICard> = {};
