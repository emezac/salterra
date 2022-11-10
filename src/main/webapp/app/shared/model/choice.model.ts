import { IOption } from 'app/shared/model/option.model';
import { IChallenge } from 'app/shared/model/challenge.model';

export interface IChoice {
  id?: number;
  choiceName?: string | null;
  action?: string | null;
  text?: string | null;
  options?: IOption[] | null;
  challenge?: IChallenge | null;
}

export const defaultValue: Readonly<IChoice> = {};
