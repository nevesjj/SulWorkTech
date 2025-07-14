import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarItensComponent } from './listar-itens.component';

describe('ListarItensComponent', () => {
  let component: ListarItensComponent;
  let fixture: ComponentFixture<ListarItensComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarItensComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListarItensComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
