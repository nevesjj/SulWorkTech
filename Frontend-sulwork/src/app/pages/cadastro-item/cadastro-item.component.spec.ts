import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgForm } from '@angular/forms';
import { of, throwError } from 'rxjs';

import { CadastroItemComponent } from './cadastro-item.component';
import { CafeService } from '../../services/cafe.service';

describe('CadastroItemComponent', () => {
  let component: CadastroItemComponent;
  let fixture: ComponentFixture<CadastroItemComponent>;
  let serviceSpy: jasmine.SpyObj<CafeService>;

  beforeEach(async () => {
    jasmine.clock().install();
    jasmine.clock().mockDate(new Date('2026-05-19T12:00:00'));
    serviceSpy = jasmine.createSpyObj<CafeService>('CafeService', ['adicionarItem']);

    await TestBed.configureTestingModule({
      imports: [CadastroItemComponent],
      providers: [{ provide: CafeService, useValue: serviceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(CadastroItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jasmine.clock().uninstall();
  });

  function createForm(invalid = false): NgForm {
    return {
      invalid,
      resetForm: jasmine.createSpy('resetForm')
    } as unknown as NgForm;
  }

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

  it('should not submit when the form is invalid', () => {
    const form = createForm(true);

    component.submit(form);

    expect(serviceSpy.adicionarItem).not.toHaveBeenCalled();
    expect(component.mensagem).toBe('');
  });

  it('should reject a date that does not exist in the calendar', () => {
    const form = createForm();
    component.item = {
      descricao: 'Pão de queijo',
      dataCafe: '31/02/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    component.submit(form);

    expect(serviceSpy.adicionarItem).not.toHaveBeenCalled();
    expect(component.mensagem).toBe('❌ A data informada não existe no calendário.');
  });

  it('should reject a date in the past', () => {
    const form = createForm();
    component.item = {
      descricao: 'Pão de queijo',
      dataCafe: '18/05/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    component.submit(form);

    expect(serviceSpy.adicionarItem).not.toHaveBeenCalled();
    expect(component.mensagem).toBe('❌ A data do café não pode ser no passado.');
  });

  it('should reject a date more than one year ahead', () => {
    const form = createForm();
    component.item = {
      descricao: 'Pão de queijo',
      dataCafe: '20/05/2027',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    component.submit(form);

    expect(serviceSpy.adicionarItem).not.toHaveBeenCalled();
    expect(component.mensagem).toBe('❌ O agendamento só pode ser feito com até 1 ano de antecedência.');
  });

  it('should add item and reset the form on success', () => {
    const form = createForm();
    const resetFormSpy = form.resetForm as jasmine.Spy;
    serviceSpy.adicionarItem.and.returnValue(of('ok'));
    component.item = {
      descricao: 'Pão de queijo',
      dataCafe: '20/05/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    component.submit(form);

    expect(serviceSpy.adicionarItem).toHaveBeenCalledWith({
      descricao: 'Pão de queijo',
      dataCafe: '20/05/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    });
    expect(component.mensagem).toBe('✅ Item adicionado com sucesso!');
    expect(component.item).toEqual({
      descricao: '',
      dataCafe: '',
      cpfColaborador: '',
      nomeColaborador: ''
    });
    expect(resetFormSpy).toHaveBeenCalled();
  });

  it('should show the backend message when error is a string', () => {
    const form = createForm();
    serviceSpy.adicionarItem.and.returnValue(throwError(() => ({ error: 'Item já existe.' })));
    component.item = {
      descricao: 'Pão de queijo',
      dataCafe: '20/05/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    component.submit(form);

    expect(component.mensagem).toBe('❌ Item já existe.');
  });

  it('should show a generic message when error is not a string', () => {
    const form = createForm();
    serviceSpy.adicionarItem.and.returnValue(throwError(() => ({ error: { detail: 'falha' } })));
    component.item = {
      descricao: 'Pão de queijo',
      dataCafe: '20/05/2026',
      cpfColaborador: '12345678901',
      nomeColaborador: 'Ana Silva'
    };

    component.submit(form);

    expect(component.mensagem).toBe('❌ Erro ao adicionar item.');
  });

  it('should format the date as the user types', () => {
    const event = createInputEvent('19052026');

    component.formatarData(event);

    expect(component.item.dataCafe).toBe('19/05/2026');
    expect((event.target as HTMLInputElement).value).toBe('19/05/2026');
  });
});
