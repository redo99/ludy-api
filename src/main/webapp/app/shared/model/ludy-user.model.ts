export interface ILudyUser {
  id?: number;
  score?: number;
  gems?: number;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<ILudyUser> = {};
