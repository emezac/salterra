import { IRoom } from 'app/shared/model/room.model';
import { TypesOfChallenge } from 'app/shared/model/enumerations/types-of-challenge.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IChallenge {
  id?: number;
  successText?: string;
  failureText?: string;
  challengeText?: string;
  challengeName?: TypesOfChallenge | null;
  difficulty?: Difficulty | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IChallenge> = {};
