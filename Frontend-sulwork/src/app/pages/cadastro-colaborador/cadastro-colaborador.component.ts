import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CafeService } from '../../services/cafe.service';
import { Colaborador } from '../../models/colaborador';

@Component({
  selector: 'app-cadastro-colaborador',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro-colaborador.component.html',
  styleUrls: ['./cadastro-colaborador.component.css'],
})
export class CadastroColaboradorComponent {
  colaborador: Colaborador = {
    nome: '',
    cpf: '',
  };

  mensagem: string = '';

  constructor(private service: CafeService) {}

  submit(form: NgForm) {
    this.mensagem = '';

    const nomeValido = /^[A-Za-zÀ-ÿ\s]+$/.test(this.colaborador.nome.trim());
    if (!nomeValido) {
      this.mensagem = '❌ O nome deve conter apenas letras.';
      return;
    }

    if (form.invalid) return;

    this.service.cadastrarColaborador(this.colaborador).subscribe({
      next: () => {
        this.mensagem = '✅ Colaborador cadastrado com sucesso!';
        this.colaborador = { nome: '', cpf: '' };
        form.resetForm();
      },
      error: (err: unknown) => {
        console.error(err);
        if (isErrorWithMessage(err)) {
          this.mensagem = '❌ ' + err.error;
        } else {
          this.mensagem = '❌ Erro ao cadastrar colaborador.';
        }
      },
    });
  }
}

function isErrorWithMessage(err: unknown): err is { error: string } {
  return typeof err === 'object' && err !== null && 'error' in err && typeof err.error === 'string';
}
