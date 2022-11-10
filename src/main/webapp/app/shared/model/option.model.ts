import { IChoice } from 'app/shared/model/choice.model';

export interface IOption {
  id?: number;
  optionName?: string | null;
  choice?: IChoice | null;
}

export const defaultValue: Readonly<IOption> = {};
