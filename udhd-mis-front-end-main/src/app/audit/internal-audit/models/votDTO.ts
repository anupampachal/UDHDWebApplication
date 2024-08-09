export class votDTO {
  budgetFY!: string;
  correspondingPrevYearFY!: string;
  currentYearFY!: string;
  id!: number;
  iaId!: number;
  netExpenditure!: {
    budgetFY: number;
    correspondingPrevYearFY: number;
    cumulativeForCurrentPeriod: number;
    currentYearFY: number;
    id?: number;
    previousYearFY: number;
    type: string;
  };
  openingBalance!: {
    budgetFY: number;
    correspondingPrevYearFY: number;
    cumulativeForCurrentPeriod: number;
    currentYearFY: number;
    id?: number;
    previousYearFY: number;
    type: string;
  };
  previousYearFY!: string;
  receipts!: {
    budgetFY: number;
    correspondingPrevYearFY: number;
    cumulativeForCurrentPeriod: number;
    currentYearFY: number;
    id?: number;
    previousYearFY: number;
    type: string;
  }
}
