import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CafeService, Colaborador } from '../../services/cafe.service';

@Component({
  selector: 'app-cadastro-colaborador',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro-colaborador.component.html',
  styleUrls: ['./cadastro-colaborador.component.css']
})
export class CadastroColaboradorComponent {
  colaborador: Colaborador = {
    nome: '',
    cpf: ''
  };

  mensagem: string = '';

  constructor(private service: CafeService) {}

  submit(form: NgForm) {
    this.mensagem = '';

    if (form.invalid) return;

    this.service.cadastrarColaborador(this.colaborador).subscribe({
      next: (res: any) => {
        this.mensagem = '✅ Colaborador cadastrado com sucesso!';
        this.colaborador = { nome: '', cpf: '' };
        form.resetForm();
      },
      error: (err) => {
        console.error(err);
        if (err.error && typeof err.error === 'string') {
          this.mensagem = '❌ ' + err.error;
        } else {
          this.mensagem = '❌ Erro ao cadastrar colaborador.';
        }
      }
    });
  }
}
