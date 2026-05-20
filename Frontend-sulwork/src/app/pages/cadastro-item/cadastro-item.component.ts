import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CafeService } from '../../services/cafe.service';
import { ItemCafe } from '../../models/item-cafe';

@Component({
  selector: 'app-cadastro-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro-item.component.html',
  styleUrls: ['./cadastro-item.component.css']
})
export class CadastroItemComponent {
  item: Omit<ItemCafe, 'idItem'> = {
    descricao: '',
    dataCafe: '',
    cpfColaborador: '',
    nomeColaborador: '',
  };

  mensagem: string = '';

  constructor(private service: CafeService) {}

  submit(form: NgForm) {
    this.mensagem = '';

    if (form.invalid) return;

    const partesData = this.item.dataCafe.split('/');
    const dia = parseInt(partesData[0], 10);
    const mes = parseInt(partesData[1], 10) - 1;
    const ano = parseInt(partesData[2], 10);

    const dataEscolhida = new Date(ano, mes, dia);
    
    if (dataEscolhida.getFullYear() !== ano || dataEscolhida.getMonth() !== mes || dataEscolhida.getDate() !== dia) {
      this.mensagem = '❌ A data informada não existe no calendário.';
      return;
    }

    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);

    if (dataEscolhida < hoje) {
      this.mensagem = '❌ A data do café não pode ser no passado.';
      return; 
    }

    const limiteUmAno = new Date();
    limiteUmAno.setHours(0, 0, 0, 0);
    limiteUmAno.setFullYear(limiteUmAno.getFullYear() + 1);

    if (dataEscolhida > limiteUmAno) {
      this.mensagem = '❌ O agendamento só pode ser feito com até 1 ano de antecedência.';
      return;
    }

    this.service.adicionarItem(this.item).subscribe({
      next: () => {
        this.mensagem = '✅ Item adicionado com sucesso!';
        this.item = { descricao: '', dataCafe: '', cpfColaborador: '', nomeColaborador: '' };
        form.resetForm();
      },
      error: (err: unknown) => {
        console.error(err);
        if (isErrorWithMessage(err)) {
          this.mensagem = '❌ ' + err.error;
        } else {
          this.mensagem = '❌ Erro ao adicionar item.';
        }
      }
    });
    
  }
  
  formatarData(event: Event) {
    const target = event.target as HTMLInputElement | null;
    if (!target) return;

    let valor = target.value.replace(/\D/g, '');

    if (valor.length > 2 && valor.length <= 4) {
      valor = valor.substring(0, 2) + '/' + valor.substring(2);
    } 
    else if (valor.length > 4) {
      valor = valor.substring(0, 2) + '/' + valor.substring(2, 4) + '/' + valor.substring(4, 8);
    }

    this.item.dataCafe = valor;
    target.value = valor;
  }
}

function isErrorWithMessage(err: unknown): err is { error: string } {
  return typeof err === 'object' && err !== null && 'error' in err && typeof err.error === 'string';
}
