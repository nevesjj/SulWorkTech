import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CafeService } from './cafe.service';
import { ItemCafe } from '../models/item-cafe';

describe('CafeService', () => {
  let service: CafeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CafeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should cadastrar a colaborador with POST', () => {
    const payload = {
      nome: 'Ana Silva',
      cpf: '12345678901'
    };

    service.cadastrarColaborador(payload).subscribe(response => {
      expect(response).toBe('ok');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/cafe/colaborador');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(payload);
    expect(req.request.responseType).toBe('text');
    req.flush('ok');
  });

  it('should adicionar item with POST', () => {
    const payload: Omit<ItemCafe, 'idItem'> = {
      descricao: 'Pão de queijo',
      dataCafe: '19/05/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    service.adicionarItem(payload).subscribe(response => {
      expect(response).toBe('ok');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/cafe/item');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(payload);
    expect(req.request.responseType).toBe('text');
    req.flush('ok');
  });

  it('should listar itens por data with query params', () => {
    service.listarItensPorData('19/05/2026').subscribe(response => {
      expect(response).toEqual([]);
    });

    const req = httpMock.expectOne(request =>
      request.method === 'GET' &&
      request.url === 'http://localhost:8080/api/cafe/itens' &&
      request.params.get('data') === '19/05/2026'
    );
    req.flush([]);
  });

  it('should atualizar entregue with PUT', () => {
    service.atualizarEntregue(7, true).subscribe(response => {
      expect(response).toBe('ok');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/cafe/item/7/entregue?entregue=true');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toBeNull();
    expect(req.request.responseType).toBe('text');
    req.flush('ok');
  });
});
