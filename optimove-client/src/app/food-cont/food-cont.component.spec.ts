import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodContComponent } from './food-cont.component';

describe('FoodContComponent', () => {
  let component: FoodContComponent;
  let fixture: ComponentFixture<FoodContComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodContComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
