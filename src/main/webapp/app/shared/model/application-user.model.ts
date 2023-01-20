import { ICourse } from 'app/shared/model/course.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IApplicationUser {
  id?: number;
  fullName?: string | null;
  email?: string | null;
  password?: string | null;
  role?: string | null;
  polygonAddress?: string | null;
  publicUrl?: string | null;
  courses?: ICourse[] | null;
  events?: IEvent[] | null;
}

export const defaultValue: Readonly<IApplicationUser> = {};
