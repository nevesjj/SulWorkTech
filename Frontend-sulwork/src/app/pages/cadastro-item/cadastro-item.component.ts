import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CafeService, ItemCafe } from '../../services/cafe.service';

@Component({
  selector: 'app-cadastro-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro-item.component.html',
  styleUrls: ['./cadastro-item.component.css']
})
export class CadastroItemComponent {
  item: ItemCafe = {
    descricao: '',
    dataCafe: '',
    cpfColaborador: ''
  };

  mensagem: string = '';

  constructor(private service: CafeService) {}

  submit(form: NgForm) {
    this.mensagem = '';

    if (form.invalid) return;

    this.service.adicionarItem(this.item).subscribe({
      next: (res: any) => {
        this.mensagem = '✅ Item adicionado com sucesso!';
        this.item = { descricao: '', dataCafe: '', cpfColaborador: '' };
        form.resetForm();
      },
      error: (err) => {
        console.error(err);
        if (err.error && typeof err.error === 'string') {
          this.mensagem = '❌ ' + err.error;
        } else {
          this.mensagem = '❌ Erro ao adicionar item.';
        }
      }
    });
  }
}
