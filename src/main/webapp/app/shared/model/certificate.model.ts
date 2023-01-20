import { ICourse } from 'app/shared/model/course.model';

export interface ICertificate {
  id?: number;
  tokenId?: string | null;
  course?: ICourse | null;
}

export const defaultValue: Readonly<ICertificate> = {};
