import { ICertificate } from 'app/shared/model/certificate.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface ICourse {
  id?: number;
  name?: string | null;
  tags?: string | null;
  description?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  imageBarCodeContentType?: string | null;
  imageBarCode?: string | null;
  certificateImageTemplateContentType?: string | null;
  certificateImageTemplate?: string | null;
  certificates?: ICertificate[] | null;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<ICourse> = {};
