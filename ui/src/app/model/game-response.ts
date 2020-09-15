export interface GameResponse {
  letter: string;
  positions: Array<number>;
  attemptsRemaining: number;
  gameWon: boolean;
  gameLost: boolean;
}
