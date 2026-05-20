import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';

import { ListarItensComponent } from './listar-itens.component';
import { CafeService } from '../../services/cafe.service';
import { ItemCafe } from '../../models/item-cafe';

describe('ListarItensComponent', () => {
  let component: ListarItensComponent;
  let fixture: ComponentFixture<ListarItensComponent>;
  let serviceSpy: jasmine.SpyObj<CafeService>;

  beforeEach(async () => {
    serviceSpy = jasmine.createSpyObj<CafeService>('CafeService', ['listarItensPorData', 'atualizarEntregue']);

    await TestBed.configureTestingModule({
      imports: [ListarItensComponent],
      providers: [{ provide: CafeService, useValue: serviceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(ListarItensComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function createInputEvent(value: string): Event {
    const input = document.createElement('input');
    input.value = value;
    const event = new Event('input');
    Object.defineProperty(event, 'target', {
      value: input
    });
    return event;
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show a message when no items are found', () => {
    serviceSpy.listarItensPorData.and.returnValue(of([]));
    component.data = '19/05/2026';
    component.itens = [{ idItem: 1, descricao: 'Teste', dataCafe: '19/05/2026', cpfColaborador: '1', nomeColaborador: 'Ana Silva' }];

    component.buscarPorData();

    expect(serviceSpy.listarItensPorData).toHaveBeenCalledWith('19/05/2026');
    expect(component.itens).toEqual([]);
    expect(component.mensagem).toBe('❌ Nenhum item encontrado para esta data.');
  });

  it('should fill the list when items are found', () => {
    const itens: ItemCafe[] = [
      { idItem: 1, descricao: 'Pão de queijo', dataCafe: '19/05/2026', cpfColaborador: '1', nomeColaborador: 'Ana Silva' },
      { idItem: 2, descricao: 'Bolo', dataCafe: '19/05/2026', cpfColaborador: '2', nomeColaborador: 'Bruno' }
    ];
    serviceSpy.listarItensPorData.and.returnValue(of(itens));
    component.data = '19/05/2026';

    component.buscarPorData();

    expect(component.itens).toEqual(itens);
    expect(component.mensagem).toBe('✅ 2 item(s) encontrado(s).');
  });

  it('should show an error when the search fails', () => {
    serviceSpy.listarItensPorData.and.returnValue(throwError(() => new Error('fail')));
    component.data = '19/05/2026';

    component.buscarPorData();

    expect(component.itens).toEqual([]);
    expect(component.mensagem).toBe('❌ Erro ao buscar itens.');
  });

  it('should mark an item as delivered locally', () => {
    const item: ItemCafe = {
      idItem: 1,
      descricao: 'Bolo',
      dataCafe: '19/05/2026',
      cpfColaborador: '1',
      nomeColaborador: 'Ana Silva',
      entregue: false
    };

    component.marcarEntregue(item);

    expect(item.entregue).toBeTrue();
  });

  it('should update the delivered status on success', () => {
    const item: ItemCafe = {
      idItem: 12,
      descricao: 'Bolo',
      dataCafe: '19/05/2026',
      cpfColaborador: '1',
      nomeColaborador: 'Ana Silva',
      entregue: false
    };
    serviceSpy.atualizarEntregue.and.returnValue(of('ok'));

    component.atualizarEntregue(item);

    expect(serviceSpy.atualizarEntregue).toHaveBeenCalledWith(12, true);
    expect(item.entregue).toBeTrue();
    expect(component.mensagem).toBe('✅ Status atualizado.');
  });

  it('should show an error when status update fails', () => {
    const item: ItemCafe = {
      idItem: 12,
      descricao: 'Bolo',
      dataCafe: '19/05/2026',
      cpfColaborador: '1',
      nomeColaborador: 'Ana Silva',
      entregue: false
    };
    serviceSpy.atualizarEntregue.and.returnValue(throwError(() => new Error('fail')));

    component.atualizarEntregue(item);

    expect(item.entregue).toBeFalse();
    expect(component.mensagem).toBe('❌ Erro ao atualizar status.');
  });

  it('should format the date as the user types', () => {
    const event = createInputEvent('19052026');

    component.formatarData(event);

    expect(component.data).toBe('19/05/2026');
    expect((event.target as HTMLInputElement).value).toBe('19/05/2026');
  });
});
