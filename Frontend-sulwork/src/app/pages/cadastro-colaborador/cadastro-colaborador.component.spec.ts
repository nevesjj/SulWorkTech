import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgForm } from '@angular/forms';
import { of, throwError } from 'rxjs';

import { CadastroColaboradorComponent } from './cadastro-colaborador.component';
import { CafeService } from '../../services/cafe.service';

describe('CadastroColaboradorComponent', () => {
  let component: CadastroColaboradorComponent;
  let fixture: ComponentFixture<CadastroColaboradorComponent>;
  let serviceSpy: jasmine.SpyObj<CafeService>;

  beforeEach(async () => {
    serviceSpy = jasmine.createSpyObj<CafeService>('CafeService', ['cadastrarColaborador']);

    await TestBed.configureTestingModule({
      imports: [CadastroColaboradorComponent],
      providers: [{ provide: CafeService, useValue: serviceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(CadastroColaboradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function createForm(invalid = false): NgForm {
    return {
      invalid,
      resetForm: jasmine.createSpy('resetForm')
    } as unknown as NgForm;
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should block names with numbers', () => {
    const form = createForm();
    component.colaborador.nome = 'Ana 1';
    component.colaborador.cpf = '12345678901';

    component.submit(form);

    expect(component.mensagem).toBe('❌ O nome deve conter apenas letras.');
    expect(serviceSpy.cadastrarColaborador).not.toHaveBeenCalled();
  });

  it('should not submit when the form is invalid', () => {
    const form = createForm(true);
    component.colaborador.nome = 'Ana Silva';
    component.colaborador.cpf = '12345678901';

    component.submit(form);

    expect(serviceSpy.cadastrarColaborador).not.toHaveBeenCalled();
    expect(component.mensagem).toBe('');
  });

  it('should cadastrar colaborador and reset the form on success', () => {
    const form = createForm();
    const resetFormSpy = form.resetForm as jasmine.Spy;
    serviceSpy.cadastrarColaborador.and.returnValue(of('ok'));
    component.colaborador.nome = 'Ana Silva';
    component.colaborador.cpf = '12345678901';

    component.submit(form);

    expect(serviceSpy.cadastrarColaborador).toHaveBeenCalledWith({
      nome: 'Ana Silva',
      cpf: '12345678901'
    });
    expect(component.mensagem).toBe('✅ Colaborador cadastrado com sucesso!');
    expect(component.colaborador).toEqual({ nome: '', cpf: '' });
    expect(resetFormSpy).toHaveBeenCalled();
  });

  it('should show the backend message when error is a string', () => {
    const form = createForm();
    serviceSpy.cadastrarColaborador.and.returnValue(throwError(() => ({ error: 'CPF já cadastrado.' })));
    component.colaborador.nome = 'Ana Silva';
    component.colaborador.cpf = '12345678901';

    component.submit(form);

    expect(component.mensagem).toBe('❌ CPF já cadastrado.');
  });

  it('should show a generic message when error is not a string', () => {
    const form = createForm();
    serviceSpy.cadastrarColaborador.and.returnValue(throwError(() => ({ error: { detail: 'falha' } })));
    component.colaborador.nome = 'Ana Silva';
    component.colaborador.cpf = '12345678901';

    component.submit(form);

    expect(component.mensagem).toBe('❌ Erro ao cadastrar colaborador.');
  });
});
