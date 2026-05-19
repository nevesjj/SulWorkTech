import { ItemCafe } from './item-cafe';

export interface BuscaItem extends ItemCafe {
  idItem: number;
  nomeColaborador: string;
}
