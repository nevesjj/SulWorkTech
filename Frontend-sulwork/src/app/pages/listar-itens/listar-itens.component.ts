import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CafeService, ItemCafe } from '../../services/cafe.service';

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
  
    this.service.atualizarEntregue((item as any).idItem, novoValor).subscribe({
      next: () => {
        item.entregue = novoValor;
        this.mensagem = '✅ Status atualizado.';
      },
      error: () => {
        this.mensagem = '❌ Erro ao atualizar status.';
      }
    });
  }
}
