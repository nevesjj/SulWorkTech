import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Colaborador } from '../models/colaborador';
import { ItemCafe } from '../models/item-cafe';

@Injectable({
  providedIn: 'root'
})
export class CafeService {
  private apiUrl = 'http://localhost:8080/api/cafe';

  constructor(private http: HttpClient) {}

  cadastrarColaborador(colaborador: Colaborador): Observable<string> {
    return this.http.post(this.apiUrl + '/colaborador', colaborador, {
      responseType: 'text'
    });
  }

  adicionarItem(item: Omit<ItemCafe, 'idItem'>): Observable<string> {
    return this.http.post(this.apiUrl + '/item', item, {
      responseType: 'text'
    });
  }

  listarItensPorData(data: string): Observable<ItemCafe[]> {
    const params = new HttpParams().set('data', data);
    return this.http.get<ItemCafe[]>(this.apiUrl + '/itens', { params });
  }
  
  atualizarEntregue(id: number, entregue: boolean): Observable<string> {
    return this.http.put<string>(
      `${this.apiUrl}/item/${id}/entregue?entregue=${entregue}`,
      null,
      { responseType: 'text' as 'json' }
    );
  }
}
