import dayjs from 'dayjs';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { EventType } from 'app/shared/model/enumerations/event-type.model';

export interface IEvent {
  id?: number;
  name?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  eventType?: EventType | null;
  applicationUsers?: IApplicationUser[] | null;
}

export const defaultValue: Readonly<IEvent> = {};
