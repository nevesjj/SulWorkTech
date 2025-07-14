import { Routes } from '@angular/router';
import { CadastroColaboradorComponent } from './pages/cadastro-colaborador/cadastro-colaborador.component';
import { CadastroItemComponent } from './pages/cadastro-item/cadastro-item.component';
import { ListarItensComponent } from './pages/listar-itens/listar-itens.component';

export const routes: Routes = [
  { path: 'colaborador', component: CadastroColaboradorComponent },
  { path: 'item', component: CadastroItemComponent },
  { path: 'listar-itens', component: ListarItensComponent },
  { path: '', redirectTo: 'colaborador', pathMatch: 'full' },
  { path: '**', redirectTo: 'colaborador' }
];



