import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CafeService } from '../../services/cafe.service';
import { ItemCafe } from '../../models/item-cafe';

@Component({
  selector: 'app-listar-itens',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './listar-itens.component.html',
  styleUrls: ['./listar-itens.component.css']
})
export class ListarItensComponent {
  data: string = '';
  itens: ItemCafe[] = [];
  mensagem: string = '';

  constructor(private service: CafeService) {}

  buscarPorData() {
    this.mensagem = '';
    this.itens = [];

    this.service.listarItensPorData(this.data).subscribe({
      next: (res) => {
        if (res.length === 0) {
          this.mensagem = '❌ Nenhum item encontrado para esta data.';
        } else {
          this.itens = res;
          this.mensagem = `✅ ${res.length} item(s) encontrado(s).`;
        }
      },
      error: (err) => {
        console.error('Erro ao buscar itens:', err);
        this.mensagem = '❌ Erro ao buscar itens.';
      }
    });
  }

  marcarEntregue(item: ItemCafe) {
    item.entregue = true;
  }
  atualizarEntregue(item: ItemCafe) {
    const novoValor = !item.entregue;
  
    this.service.atualizarEntregue(item.idItem, novoValor).subscribe({
      next: () => {
        item.entregue = novoValor;
        this.mensagem = '✅ Status atualizado.';
      },
      error: () => {
        this.mensagem = '❌ Erro ao atualizar status.';
      }
    });
  }

  formatarData(event: Event) {
    const target = event.target as HTMLInputElement | null;
    if (!target) return;

    let valor = target.value.replace(/\D/g, '');

    if (valor.length > 2 && valor.length <= 4) {
      valor = valor.substring(0, 2) + '/' + valor.substring(2);
    } else if (valor.length > 4) {
      valor = valor.substring(0, 2) + '/' + valor.substring(2, 4) + '/' + valor.substring(4, 8);
    }

    this.data = valor;
    target.value = valor;
  }
}
