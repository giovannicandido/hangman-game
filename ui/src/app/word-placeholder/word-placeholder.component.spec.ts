import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WordPlaceholderComponent } from './word-placeholder.component';

describe('WordPlaceholderComponent', () => {
  let component: WordPlaceholderComponent;
  let fixture: ComponentFixture<WordPlaceholderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WordPlaceholderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WordPlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
